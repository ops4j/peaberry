/**
 * Copyright (C) 2008 Stuart McCulloch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.peaberry.internal;

import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INSTANCEOF;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V1_5;
import static org.objectweb.asm.Type.VOID;
import static org.objectweb.asm.Type.getArgumentTypes;
import static org.objectweb.asm.Type.getDescriptor;
import static org.objectweb.asm.Type.getInternalName;
import static org.objectweb.asm.Type.getMethodDescriptor;
import static org.objectweb.asm.Type.getReturnType;

import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.ops4j.peaberry.Import;

/**
 * Around-advice glue code, specifically optimised for peaberry.
 * 
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class ImportGlue {

  private static final String EXCEPTION_NAME = getInternalName(Exception.class);
  private static final String IMPORT_NAME = getInternalName(Import.class);
  private static final String OBJECT_DESC = getDescriptor(Object.class);

  public static String getProxyName(final String clazzName) {
    return clazzName + "$Proxy";
  }

  public static String getClazzName(final String proxyName) {
    return proxyName.replaceAll("\\$Proxy$", "");
  }

  public static byte[] generateProxy(Class<?> clazz) {

    final String proxyName = getProxyName(getInternalName(clazz));

    final String superName;
    final String[] interfaceNames;

    if (clazz.isInterface()) {
      superName = getInternalName(Object.class);
      interfaceNames = getInternalNames(clazz);
    } else {
      superName = getInternalName(clazz);
      interfaceNames = getInternalNames(clazz.getInterfaces());
    }

    final ClassWriter cw = new ClassWriter(COMPUTE_MAXS);

    cw.visit(V1_5, ACC_PUBLIC | ACC_FINAL, proxyName, null, superName, interfaceNames);
    cw.visitField(ACC_PRIVATE | ACC_FINAL, "handle", OBJECT_DESC, null, null).visitEnd();

    init(cw, superName, proxyName);
    for (final Method m : clazz.getMethods()) {
      wrap(cw, proxyName, m);
    }

    cw.visitEnd();

    return cw.toByteArray();
  }

  private static void init(final ClassWriter cw, final String superName, final String proxyName) {

    final String descriptor = '(' + OBJECT_DESC + ")V";

    final MethodVisitor v = cw.visitMethod(ACC_PUBLIC, "<init>", descriptor, null, null);

    v.visitCode();

    v.visitVarInsn(ALOAD, 0);
    v.visitInsn(DUP);
    v.visitMethodInsn(INVOKESPECIAL, superName, "<init>", "()V");
    v.visitVarInsn(ALOAD, 1);
    v.visitFieldInsn(PUTFIELD, proxyName, "handle", OBJECT_DESC);
    v.visitInsn(RETURN);

    v.visitMaxs(0, 0);
    v.visitEnd();
  }

  private static void wrap(final ClassWriter cw, final String proxyName, final Method method) {

    final String name = method.getName();

    final String descriptor = getMethodDescriptor(method);
    final String[] exceptions = getInternalNames(method.getExceptionTypes());

    final MethodVisitor v = cw.visitMethod(ACC_PUBLIC, name, descriptor, null, exceptions);

    final Label start = new Label();
    final Label end = new Label();

    final Label isHandle = new Label();
    final Label loop = new Label();

    final Label ungetR = new Label();
    final Label finalR = new Label();
    final Label catchX = new Label();
    final Label ungetX = new Label();
    final Label finalX = new Label();

    v.visitCode();

    v.visitTryCatchBlock(start, ungetR, catchX, null);
    v.visitTryCatchBlock(ungetR, finalR, finalR, EXCEPTION_NAME);
    v.visitTryCatchBlock(ungetX, finalX, finalX, EXCEPTION_NAME);

    v.visitLabel(start);

    v.visitVarInsn(ALOAD, 0);
    v.visitFieldInsn(GETFIELD, proxyName, "handle", OBJECT_DESC);
    v.visitJumpInsn(GOTO, isHandle);

    v.visitLabel(loop);
    v.visitTypeInsn(CHECKCAST, IMPORT_NAME);
    v.visitInsn(DUP);
    v.visitVarInsn(ASTORE, 0);
    v.visitMethodInsn(INVOKEINTERFACE, IMPORT_NAME, "get", "()" + OBJECT_DESC);

    v.visitLabel(isHandle);
    v.visitInsn(DUP);
    v.visitTypeInsn(INSTANCEOF, IMPORT_NAME);
    v.visitJumpInsn(IFNE, loop);

    int i = 1;
    for (final Type t : getArgumentTypes(method)) {
      v.visitVarInsn(t.getOpcode(ILOAD), i);
      i = i + t.getSize();
    }

    final Class<?> clazz = method.getDeclaringClass();

    final int kind = clazz.isInterface() ? INVOKEINTERFACE : INVOKEVIRTUAL;
    v.visitMethodInsn(kind, getInternalName(clazz), name, getMethodDescriptor(method));
    final Type returnType = getReturnType(method);

    if (returnType.getSort() != VOID) {
      v.visitVarInsn(returnType.getOpcode(ISTORE), 1);
    }

    v.visitLabel(ungetR);

    v.visitVarInsn(ALOAD, 0);
    v.visitInsn(DUP);
    v.visitTypeInsn(CHECKCAST, IMPORT_NAME);
    v.visitMethodInsn(INVOKEINTERFACE, IMPORT_NAME, "unget", "()V");

    v.visitLabel(finalR);

    if (returnType.getSort() != VOID) {
      v.visitVarInsn(returnType.getOpcode(ILOAD), 1);
    }

    v.visitInsn(returnType.getOpcode(IRETURN));

    v.visitLabel(catchX);
    v.visitVarInsn(ASTORE, 1);

    v.visitLabel(ungetX);

    v.visitVarInsn(ALOAD, 0);
    v.visitInsn(DUP);
    v.visitTypeInsn(CHECKCAST, IMPORT_NAME);
    v.visitMethodInsn(INVOKEINTERFACE, IMPORT_NAME, "unget", "()V");

    v.visitLabel(finalX);

    v.visitVarInsn(ALOAD, 1);
    v.visitInsn(ATHROW);

    v.visitLabel(end);
    v.visitMaxs(0, 0);
    v.visitEnd();
  }

  private static String[] getInternalNames(Class<?>... clazzes) {
    String[] names = new String[clazzes.length];
    for (int i = 0; i < names.length; i++) {
      names[i] = getInternalName(clazzes[i]);
    }
    return names;
  }
}
