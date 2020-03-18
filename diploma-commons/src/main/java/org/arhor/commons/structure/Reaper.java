package org.arhor.commons.structure;//package by.arhor.core.structure;
//
//import by.arhor.core.Lazy;
//
//import java.lang.invoke.MethodHandles;
//
//@SuppressWarnings("unchecked")
//public final class Reaper {
//
//  private static final Lazy<Reaper> REAPER = Lazy.eval(Reaper::new);
//
//  private final MethodHandles.Lookup lookup = MethodHandles.lookup();
//
//  private Reaper() {}
//
//  public static Reaper summon() {
//    return REAPER.get();
//  }
//
//  public final <T> Soul<T> soulSeparate(T item) throws IllegalAccessException {
//    Class<T> itemClass = (Class<T>) item.getClass();
//
//    final var declaredFields = itemClass.getDeclaredFields();
//
//    final var aspects = (Soul.Aspect<T, ?>[]) new Soul.Aspect[declaredFields.length];
//
//    for (int i = 0; i < declaredFields.length; i++) {
//      final var declaredField = declaredFields[i];
//
//      final var varHandle =
//          MethodHandles
//              .privateLookupIn(itemClass, lookup)
//              .unreflectVarHandle(declaredField);
//
//      aspects[i] = new Soul.Aspect<>(declaredField.getName(), varHandle);
//    }
//
//    return new Soul<>(itemClass.getSimpleName(), aspects);
//  }
//}
