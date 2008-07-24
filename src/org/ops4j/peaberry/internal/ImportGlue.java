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

import static java.lang.reflect.Modifier.ABSTRACT;
import static java.lang.reflect.Modifier.FINAL;
import static java.lang.reflect.Modifier.NATIVE;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;
import static java.lang.reflect.Modifier.SYNCHRONIZED;
import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.ILOAD;
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
 * @author mcculls@gmail.com (Stuart McCulloch)
 */
final class ImportGlue {

  // instances not allowed
  private ImportGlue() {}

  private static final String EXCEPTION_NAME = getInternalName(Exception.class);
  private static final String IMPORT_NAME = getInternalName(Import.class);

  private static final String IMPORT_DESC = getDescriptor(Import.class);
  private static final String OBJECT_DESC = getDescriptor(Object.class);

  public static String getProxyName(final String clazzName) {
    final String safeName;
    if (clazzName.startsWith("java.")) {
      safeName = clazzName.replace(".", "$$");
    } else if (clazzName.startsWith("java/")) {
      safeName = clazzName.replace("/", "$$");
    } else {
      safeName = clazzName;
    }
    return safeName + "$$Proxy";
  }

  public static String getClazzName(final String proxyName) {
    final String clazzName = proxyName.replaceFirst("\\$\\$Proxy$", "");
    if (clazzName.startsWith("java$$")) {
      return clazzName.replace("$$", ".");
    }
    return clazzName;
  }

  public static byte[] generateProxy(final Class<?> clazz) {

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

    final ClassWriter cw = new ClassWriter(COMPUTE_FRAMES);

    cw.visit(V1_5, PUBLIC | FINAL, proxyName, null, superName, interfaceNames);
    cw.visitField(PRIVATE | FINAL, "handle", IMPORT_DESC, null, null).visitEnd();

    init(cw, superName, proxyName);
    for (final Method m : clazz.getMethods()) {
      if ((m.getModifiers() & (STATIC | FINAL | NATIVE)) == 0) {
        wrap(cw, proxyName, m);
      }
    }

    cw.visitEnd();

    return cw.toByteArray();
  }

  private static void init(final ClassWriter cw, final String superName, final String proxyName) {

    final MethodVisitor v = cw.visitMethod(PUBLIC, "<init>", '(' + IMPORT_DESC + ")V", null, null);

    v.visitCode();

    v.visitVarInsn(ALOAD, 0);
    v.visitInsn(DUP);
    v.visitMethodInsn(INVOKESPECIAL, superName, "<init>", "()V");
    v.visitVarInsn(ALOAD, 1);
    v.visitFieldInsn(PUTFIELD, proxyName, "handle", IMPORT_DESC);
    v.visitInsn(RETURN);

    v.visitMaxs(0, 0);
    v.visitEnd();
  }

  private static void wrap(final ClassWriter cw, final String proxyName, final Method method) {

    final String methodName = method.getName();

    final String descriptor = getMethodDescriptor(method);
    final String[] exceptions = getInternalNames(method.getExceptionTypes());
    final int modifiers = method.getModifiers() & ~(ABSTRACT | SYNCHRONIZED);

    final MethodVisitor v = cw.visitMethod(modifiers, methodName, descriptor, null, exceptions);

    final Label start = new Label();
    final Label end = new Label();

    final Label ungetR = new Label();
    final Label finalR = new Label();
    final Label catchX = new Label();
    final Label ungetX = new Label();
    final Label finalX = new Label();

    v.visitCode();

    v.visitTryCatchBlock(start, ungetR, catchX, null);
    v.visitTryCatchBlock(ungetR, finalR, finalR, EXCEPTION_NAME);
    v.visitTryCatchBlock(ungetX, finalX, finalX, EXCEPTION_NAME);

    v.visitVarInsn(ALOAD, 0);
    v.visitFieldInsn(GETFIELD, proxyName, "handle", IMPORT_DESC);
    v.visitInsn(DUP);
    v.visitVarInsn(ASTORE, 0);

    v.visitLabel(start);

    v.visitMethodInsn(INVOKEINTERFACE, IMPORT_NAME, "get", "()" + OBJECT_DESC);

    int i = 1;
    for (final Type t : getArgumentTypes(method)) {
      v.visitVarInsn(t.getOpcode(ILOAD), i);
      i = i + t.getSize();
    }

    final Class<?> clazz = method.getDeclaringClass();
    final String subjectName = getInternalName(clazz);

    if (clazz.isInterface()) {
      v.visitMethodInsn(INVOKEINTERFACE, subjectName, methodName, descriptor);
    } else {
      v.visitTypeInsn(CHECKCAST, subjectName);
      v.visitMethodInsn(INVOKEVIRTUAL, subjectName, methodName, descriptor);
    }

    final Type returnType = getReturnType(method);

    if (returnType.getSort() != VOID) {
      v.visitVarInsn(returnType.getOpcode(ISTORE), 1);
    }

    v.visitLabel(ungetR);

    v.visitVarInsn(ALOAD, 0);
    v.visitMethodInsn(INVOKEINTERFACE, IMPORT_NAME, "unget", "()V");
    v.visitInsn(ACONST_NULL);

    v.visitLabel(finalR);

    if (returnType.getSort() != VOID) {
      v.visitVarInsn(returnType.getOpcode(ILOAD), 1);
    }

    v.visitInsn(returnType.getOpcode(IRETURN));

    v.visitLabel(catchX);
    v.visitVarInsn(ASTORE, 1);

    v.visitLabel(ungetX);

    v.visitVarInsn(ALOAD, 0);
    v.visitMethodInsn(INVOKEINTERFACE, IMPORT_NAME, "unget", "()V");
    v.visitInsn(ACONST_NULL);

    v.visitLabel(finalX);

    v.visitVarInsn(ALOAD, 1);
    v.visitInsn(ATHROW);

    v.visitLabel(end);
    v.visitMaxs(0, 0);
    v.visitEnd();
  }

  private static String[] getInternalNames(final Class<?>... clazzes) {
    final String[] names = new String[clazzes.length];
    for (int i = 0; i < names.length; i++) {
      names[i] = getInternalName(clazzes[i]);
    }
    return names;
  }
}
