package org.arhor.commons.structure;//package by.arhor.core.structure;
//
//import java.lang.invoke.VarHandle;
//
//public class ObjectUpdaterImpl<T> implements ObjectUpdater<T> {
//
//  private final VarHandle[] varHandles;
//
//  ObjectUpdaterImpl(VarHandle[] varHandles) {
//    this.varHandles = varHandles;
//  }
//
//  @Override
//  public void update(T target, T source) {
//    for (VarHandle varHandle : varHandles) {
////      final var oldValue = varHandle.get(target);
//      final var newValue = varHandle.get(source);
//
////      if (!Objects.equals(oldValue, newValue)) {
//        varHandle.set(target, newValue);
////      }
//    }
//  }
//}
