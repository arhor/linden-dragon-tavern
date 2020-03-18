package org.arhor.commons.structure;//package by.arhor.core.structure;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@SuppressWarnings("unchecked")
//public final class NetherWorld {
//
//  private final Map<Class<?>, Soul<?>> soulStorage = new HashMap<>();
//  private final Reaper reaper = Reaper.summon();
//
//  public final <T> Soul<T> soulLookup(T item) throws IllegalAccessException {
//    final var itemClass = (Class<T>) item.getClass();
//
//    var soul = (Soul<T>) soulStorage.get(itemClass);
//
//    if (soul == null) {
//      soul = reaper.soulSeparate(item);
//      soulStorage.put(itemClass, soul);
//    }
//
//    return soul;
//  }
//
//}
