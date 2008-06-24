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
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.DLOAD;
import static org.objectweb.asm.Opcodes.DRETURN;
import static org.objectweb.asm.Opcodes.DSTORE;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.FLOAD;
import static org.objectweb.asm.Opcodes.FRETURN;
import static org.objectweb.asm.Opcodes.FSTORE;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.LLOAD;
import static org.objectweb.asm.Opcodes.LRETURN;
import static org.objectweb.asm.Opcodes.LSTORE;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V1_5;

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
final class ImportProxyClassLoader
    extends ClassLoader {

  public ImportProxyClassLoader(ClassLoader loader) {
    super(loader);
  }

  @Override
  protected Class<?> findClass(String name)
      throws ClassNotFoundException {

    if (name.endsWith("$Peaberry")) {
      String baseName = name.substring(0, name.length() - "$Peaberry".length());
      Class<?> baseClazz = loadClass(baseName);
      byte[] byteCode = generateProxy(baseClazz);
      return defineClass(name, byteCode, 0, byteCode.length);
    }

    return super.findClass(name);
  }

  private static String[] internalNames(Class<?>... interfaces) {
    String[] names = new String[interfaces.length];
    for (int i = 0; i < interfaces.length; i++) {
      names[i] = Type.getInternalName(interfaces[i]);
    }
    return names;
  }

  private static byte[] generateProxy(final Class<?> clazz) {

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

    final ClassWriter classWriter = new ClassWriter(COMPUTE_MAXS);
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

    ctorVisitor.visitMaxs(0, 0);
    ctorVisitor.visitEnd();

    for (Method m : clazz.getMethods()) {

      MethodVisitor methodVisitor =
          classWriter.visitMethod(ACC_PUBLIC, m.getName(), Type.getMethodDescriptor(m), null,
              internalNames(m.getExceptionTypes()));

      Label start = new Label();
      Label end = new Label();
      Label handler = new Label();
      Label finallyHandler = new Label();

      methodVisitor.visitCode();

      methodVisitor.visitTryCatchBlock(start, end, handler, Type.getInternalName(Exception.class));
      methodVisitor.visitTryCatchBlock(start, end, finallyHandler, null);
      methodVisitor.visitTryCatchBlock(handler, finallyHandler, finallyHandler, null);

      Class<?> ret = m.getReturnType();

      methodVisitor.visitVarInsn(ALOAD, 0);
      methodVisitor.visitFieldInsn(GETFIELD, proxyName, "handle", fieldDesc);
      methodVisitor.visitVarInsn(ASTORE, 1);
      methodVisitor.visitLabel(start);
      methodVisitor.visitVarInsn(ALOAD, 1);
      methodVisitor.visitMethodInsn(INVOKEINTERFACE, fieldType, "get", "()Ljava/lang/Object;");
      methodVisitor.visitMethodInsn(INVOKEINTERFACE, Type.getInternalName(m.getDeclaringClass()), m
          .getName(), Type.getMethodDescriptor(m));
      if (m.getReturnType() != void.class) {
        if (ret == int.class) {
          methodVisitor.visitVarInsn(ISTORE, 2);
        } else if (ret == long.class) {
          methodVisitor.visitVarInsn(LSTORE, 2);
        } else if (ret == float.class) {
          methodVisitor.visitVarInsn(FSTORE, 2);
        } else if (ret == double.class) {
          methodVisitor.visitVarInsn(DSTORE, 2);
        } else {
          methodVisitor.visitVarInsn(ASTORE, 2);
        }
      }
      methodVisitor.visitLabel(end);
      methodVisitor.visitVarInsn(ALOAD, 1);
      methodVisitor.visitMethodInsn(INVOKEINTERFACE, fieldType, "unget", "()V");
      if (m.getReturnType() == void.class) {
        methodVisitor.visitInsn(RETURN);
      } else {
        if (ret == int.class) {
          methodVisitor.visitVarInsn(ILOAD, 2);
        } else if (ret == long.class) {
          methodVisitor.visitVarInsn(LLOAD, 2);
        } else if (ret == float.class) {
          methodVisitor.visitVarInsn(FLOAD, 2);
        } else if (ret == double.class) {
          methodVisitor.visitVarInsn(DLOAD, 2);
        } else {
          methodVisitor.visitVarInsn(ALOAD, 2);
        }
        if (ret == int.class) {
          methodVisitor.visitInsn(IRETURN);
        } else if (ret == long.class) {
          methodVisitor.visitInsn(LRETURN);
        } else if (ret == float.class) {
          methodVisitor.visitInsn(FRETURN);
        } else if (ret == double.class) {
          methodVisitor.visitInsn(DRETURN);
        } else {
          methodVisitor.visitInsn(ARETURN);
        }
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

      methodVisitor.visitMaxs(0, 0);
      methodVisitor.visitEnd();
    }

    classWriter.visitEnd();

    return classWriter.toByteArray();
  }
}
