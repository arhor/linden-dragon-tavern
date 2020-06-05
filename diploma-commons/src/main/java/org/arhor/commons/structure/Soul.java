//package org.arhor.commons.structure;
//
//import java.lang.invoke.VarHandle;
//
//public final class Soul<T> {
//  private final String name;
////  private final Aspect<T, ?>[] aspects;
//
////  public Soul(String name, Aspect<T, ?>[] aspects) {
////    this.name = name;
////    this.aspects = aspects;
////  }
//
//  public String getName() {
//    return name;
//  }
//
////  public Aspect<T, ?>[] getAspects() {
////    return aspects;
////  }
//
////  public static abstract class SoulCore<B> implements Soul<B> {
////    private final String name;
////
////    protected SoulCore(String name) {
////      this.name = name;
////    }
////
////    @Override
////    public String getName() {
////      return name;
////    }
////  }
//
////  @SuppressWarnings("unchecked")
////  public static final class Aspect<B, V> extends SoulCore<B> {
////    private final VarHandle accessor;
////
////    public Aspect(String name, VarHandle accessor) {
////      super(name);
////      this.accessor = accessor;
////    }
////
////    public V getValue(B body) {
////      return (V) accessor.get(body);
////    }
////
////    public void setValue(B body, V value) {
////      accessor.set(body, value);
////    }
////  }
//
////  public static final class Fracture<B> extends SoulCore<B> {
////    private final Soul<B>[] souls;
////
////    public Fracture(String name, Soul<B>[] souls) {
////      super(name);
////      this.souls = souls;
////    }
////  }
//}
