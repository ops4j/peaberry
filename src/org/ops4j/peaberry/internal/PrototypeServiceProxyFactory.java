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

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V1_5;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.ops4j.peaberry.Import;
import org.ops4j.peaberry.ServiceUnavailableException;

/**
 * @author stuart.mcculloch@jayway.net (Stuart McCulloch)
 */
final class PrototypeServiceProxyFactory {

  // instances not allowed
  private PrototypeServiceProxyFactory() {}

  private static String[] internalNames(Class<?>... interfaces) {
    String[] names = new String[interfaces.length];
    for (int i = 0; i < interfaces.length; i++) {
      names[i] = Type.getInternalName(interfaces[i]);
    }
    return names;
  }

  public static <T> T serviceProxy(final Class<? extends T> clazz, final Import<T> handle) {

    final String proxyName;
    final String[] apiNames;

    if (clazz.isInterface()) {
      apiNames = internalNames(clazz);
      proxyName = apiNames[0] + "$Peaberry";
    } else {
      apiNames = internalNames(clazz.getInterfaces());
      proxyName = Type.getInternalName(clazz) + "$Peaberry";
    }

    final String fieldType = Type.getInternalName(Import.class);
    final String fieldDesc = 'L' + fieldType + ';';

    final ClassWriter classWriter = new ClassWriter(COMPUTE_FRAMES | COMPUTE_MAXS);
    classWriter.visit(V1_5, ACC_PUBLIC | ACC_FINAL, proxyName, null, "java/lang/Object", apiNames);

    FieldVisitor handleVisitor =
        classWriter.visitField(ACC_PRIVATE | ACC_FINAL, "handle", fieldDesc, null, null);

    handleVisitor.visitEnd();

    MethodVisitor ctorVisitor =
        classWriter.visitMethod(ACC_PUBLIC, "<init>", '(' + fieldDesc + ")V", null, null);

    ctorVisitor.visitCode();
    ctorVisitor.visitVarInsn(ALOAD, 0);
    ctorVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
    ctorVisitor.visitVarInsn(ALOAD, 0);
    ctorVisitor.visitVarInsn(ALOAD, 1);
    ctorVisitor.visitFieldInsn(PUTFIELD, proxyName, "handle", fieldDesc);
    ctorVisitor.visitInsn(RETURN);
    ctorVisitor.visitEnd();

    for (Method m : clazz.getMethods()) {

      MethodVisitor methodVisitor =
          classWriter.visitMethod(ACC_PUBLIC, m.getName(), Type.getMethodDescriptor(m), null,
              internalNames(m.getExceptionTypes()));

      Label start = new Label();
      Label end = new Label();
      Label handler = new Label();
      Label finallyHandler = new Label();
      Label fini = new Label();

      methodVisitor.visitCode();
      methodVisitor.visitVarInsn(ALOAD, 0);
      methodVisitor.visitFieldInsn(GETFIELD, proxyName, "handle", fieldDesc);
      methodVisitor.visitVarInsn(ASTORE, 1);
      methodVisitor.visitLabel(start);
      methodVisitor.visitVarInsn(ALOAD, 1);
      methodVisitor.visitMethodInsn(INVOKEINTERFACE, fieldType, "get", "()Ljava/lang/Object;");
      methodVisitor.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(m.getDeclaringClass()), m
          .getName(), Type.getMethodDescriptor(m));
      if (m.getReturnType() == void.class) {
        methodVisitor.visitInsn(POP);
      } else {
        methodVisitor.visitVarInsn(ASTORE, 2);
      }
      methodVisitor.visitLabel(end);
      methodVisitor.visitVarInsn(ALOAD, 1);
      methodVisitor.visitMethodInsn(INVOKEINTERFACE, fieldType, "unget", "()V");
      if (m.getReturnType() == void.class) {
        methodVisitor.visitJumpInsn(GOTO, fini);
      } else {
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitInsn(ARETURN);
      }
      methodVisitor.visitLabel(handler);
      methodVisitor.visitVarInsn(ASTORE, 2);
      methodVisitor.visitTypeInsn(NEW, Type.getInternalName(ServiceUnavailableException.class));
      methodVisitor.visitInsn(DUP);
      methodVisitor.visitVarInsn(ALOAD, 2);
      methodVisitor
          .visitMethodInsn(INVOKESPECIAL, Type.getInternalName(ServiceUnavailableException.class),
              "<init>", "(Ljava/lang/Throwable;)V");
      methodVisitor.visitInsn(ATHROW);
      methodVisitor.visitLabel(finallyHandler);
      methodVisitor.visitVarInsn(ASTORE, 3);
      methodVisitor.visitVarInsn(ALOAD, 1);
      methodVisitor.visitMethodInsn(INVOKEINTERFACE, fieldType, "unget", "()V");
      methodVisitor.visitVarInsn(ALOAD, 3);
      methodVisitor.visitInsn(ATHROW);
      methodVisitor.visitLabel(fini);
      if (m.getReturnType() == void.class) {
        methodVisitor.visitInsn(RETURN);
      }

      methodVisitor.visitTryCatchBlock(start, end, handler, Type.getInternalName(Exception.class));
      methodVisitor.visitTryCatchBlock(start, end, finallyHandler, null);
      methodVisitor.visitTryCatchBlock(handler, finallyHandler, finallyHandler, null);

      methodVisitor.visitEnd();
    }

    classWriter.visitEnd();

    try {
      OutputStream f = new FileOutputStream("eek");
      f.write(classWriter.toByteArray());
      f.close();
    } catch (Exception e) {}

    return null;
  }
}
