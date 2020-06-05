//package org.arhor.commons.structure;//package by.arhor.core.structure;
//
//import java.lang.annotation.Annotation;
//import java.lang.invoke.MethodHandles;
//import java.lang.invoke.VarHandle;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//import by.arhor.core.Lazy;
//
//public class MagicAccessor {
//
//  private static final Lazy<MagicAccessor> INSTANCE = Lazy.eval(MagicAccessor::new);
//
//  private final MethodHandles.Lookup lookup = MethodHandles.lookup();
//  private final Map<Class<?>, List<FieldMetaHolder<?>>> metas = new HashMap<>();
//
//  private MagicAccessor() {}
//
//  public static MagicAccessor getInstance() {
//    return INSTANCE.get();
//  }
//
//  public boolean check(Object o) throws IllegalAccessException {
//    Objects.requireNonNull(o);
//    final Class<?> oClass = o.getClass();
//
//    var fieldMetaHolders = metas.get(oClass);
//
//    if (fieldMetaHolders == null) {
//      register(oClass);
//      fieldMetaHolders = metas.get(oClass);
//    }
//
//    for (var holder : fieldMetaHolders) {
//      final Class<?> varType = holder.accessor.varType();
//      final var value = varType.cast(holder.accessor.get(o));
//    }
//    return true;
//  }
//
//  public void register(Class<?> target) throws IllegalAccessException {
//    final var fieldMetaHolders = new ArrayList<FieldMetaHolder<?>>();
//
//    for (Field declaredField : target.getDeclaredFields()) {
//      VarHandle varHandle =
//          MethodHandles
//              .privateLookupIn(target, lookup)
//              .unreflectVarHandle(declaredField);
//
//      fieldMetaHolders.add(
//          new FieldMetaHolder<>(
//              declaredField.getName(),
//              target.getAnnotations(),
//              varHandle
//          )
//      );
//    }
//
//    metas.put(target, fieldMetaHolders);
//  }
//
//  private static class FieldMetaHolder<T> {
//    private final String name;
//    private final Annotation[] constraints;
//    private final VarHandle accessor;
//
//    private FieldMetaHolder(String name, Annotation[] constraints, VarHandle accessor) {
//      this.name = name;
//      this.constraints = constraints;
//      this.accessor = accessor;
//    }
//
//    @SuppressWarnings("unchecked")
//    T get(Object that) {
//      return (T) accessor.get(that);
//    }
//  }
//
//}
