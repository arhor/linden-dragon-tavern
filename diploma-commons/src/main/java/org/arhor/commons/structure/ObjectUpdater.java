package org.arhor.commons.structure;//package by.arhor.core.structure;
//
//import java.lang.invoke.MethodHandles;
//import java.lang.invoke.VarHandle;
//
//public interface ObjectUpdater<T> {
//
//  static <R> ObjectUpdater<R> of(Class<? extends R> targetClass) throws IllegalAccessException {
//    final var lookup = MethodHandles.privateLookupIn(targetClass, MethodHandles.lookup());
//    final var declaredFields = targetClass.getDeclaredFields();
//    final var varHandles = new VarHandle[declaredFields.length];
//
//    for (int i = 0; i < declaredFields.length; i++) {
//      varHandles[i] = lookup.unreflectVarHandle(declaredFields[i]);
//    }
//
//    return new ObjectUpdaterImpl<>(varHandles);
//  }
//
//  void update(T target, T source);
//
//}
