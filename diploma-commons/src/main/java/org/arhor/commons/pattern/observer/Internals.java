package org.arhor.commons.pattern.observer;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
final class Internals {

  private Internals() { throw new UnsupportedOperationException("Must not be instantiated"); }

  static <V> ObservableVal<V> observableVal(V value) {
    return new ObservableValImpl<>(value);
  }

  static <R> ObservableRef<R> observableRef(R reference) {
    return new ObservableRefImpl<>(reference);
  }

  static ObservableByte observableVal(byte value) {
    return new ObservableByteImpl(value);
  }

  static ObservableShort observableVal(short value) {
    return new ObservableShortImpl(value);
  }

  static ObservableInt observableVal(int value) {
    return new ObservableIntImpl(value);
  }

  static ObservableLong observableVal(long value) {
    return new ObservableLongImpl(value);
  }

  static ObservableFloat observableVal(float value) {
    return new ObservableFloatImpl(value);
  }

  static ObservableDouble observableVal(double value) {
    return new ObservableDoubleImpl(value);
  }

  static ObservableChar observableVal(char value) {
    return new ObservableCharImpl(value);
  }

  static ObservableBoolean observableVal(boolean value) {
    return new ObservableBooleanImpl(value);
  }

  /* ---------- Observable basic abstractions ---------- */

  private abstract static class ObservableOps<T> implements Observable<T> {

    protected final Set<Observer<T>> observers = new LinkedHashSet<>();

    @Override
    public final void subscribe(Observer<T> observer) {
      observers.add(observer);
    }

    @Override
    public final void unsubscribe(Observer<T> observer) {
      observers.remove(observer);
    }
  }

  private abstract static class AbstractObservable<T> extends ObservableOps<T> {

    protected T observable;

    AbstractObservable(T observable) {
      this.observable = observable;
    }

    protected final void noticeObservers() {
      observers.forEach(observer -> observer.notice(observable));
    }
  }

  /* ---------- Observable general implementations ---------- */

  private static final class ObservableValImpl<V> extends AbstractObservable<V> implements ObservableVal<V> {
    ObservableValImpl(V value) { super(value); }

    @Override
    public final void set(V value) {
      observable = value;
      noticeObservers();
    }

    @Override
    public final V get() {
      return observable;
    }
  }

  private static final class ObservableRefImpl<R> extends AbstractObservable<R> implements ObservableRef<R> {
    ObservableRefImpl(R reference) { super(reference); }

    @Override
    public final <V> void mutate(BiConsumer<R, V> setter, V value) {
      setter.accept(observable, value);
      noticeObservers();
    }

    @Override
    public final <V> V access(Function<R, V> getter) {
      return getter.apply(observable);
    }

    @Override
    public final <V> Consumer<V> buildSetter(BiConsumer<R, V> setter) {
      return (value) -> {
        setter.accept(observable, value);
        noticeObservers();
      };
    }

    @Override
    public final <V> Supplier<V> buildGetter(Function<R, V> getter) {
      return () -> getter.apply(observable);
    }
  }

  /* ---------- Observable primitive specializations ---------- */

  private static final class ObservableByteImpl extends ObservableOps<Byte> implements ObservableByte {

    private byte value;

    ObservableByteImpl(byte value) {
      this.value = value;
    }

    @Override
    public final void setValue(byte value) {
      observers.forEach(observer -> observer.notice(this.value = value));
    }

    @Override
    public final byte getValue() {
      return value;
    }

    @Override
    public final void set(Byte value) {
      if (value != null) {
        setValue(value);
      }
    }

    @Override
    public final Byte get() {
      return value;
    }
  }

  private static final class ObservableShortImpl extends ObservableOps<Short> implements ObservableShort {

    private short value;

    ObservableShortImpl(short value) {
      this.value = value;
    }

    @Override
    public final void setValue(short value) {
      observers.forEach(observer -> observer.notice(this.value = value));
    }

    @Override
    public final short getValue() {
      return value;
    }

    @Override
    public final void set(Short value) {
      if (value != null) {
        setValue(value);
      }
    }

    @Override
    public final Short get() {
      return value;
    }
  }

  private static final class ObservableIntImpl extends ObservableOps<Integer> implements ObservableInt {

    private int value;

    ObservableIntImpl(int value) {
      this.value = value;
    }

    @Override
    public final void setValue(int value) {
      observers.forEach(observer -> observer.notice(this.value = value));
    }

    @Override
    public final int getValue() {
      return value;
    }

    @Override
    public final void set(Integer value) {
      if (value != null) {
        setValue(value);
      }
    }

    @Override
    public final Integer get() {
      return value;
    }
  }

  private static final class ObservableLongImpl extends ObservableOps<Long> implements ObservableLong {

    private long value;

    ObservableLongImpl(long value) {
      this.value = value;
    }

    @Override
    public final void setValue(long value) {
      observers.forEach(observer -> observer.notice(this.value = value));
    }

    @Override
    public final long getValue() {
      return value;
    }

    @Override
    public final void set(Long value) {
      if (value != null) {
        setValue(value);
      }
    }

    @Override
    public final Long get() {
      return value;
    }
  }

  private static final class ObservableFloatImpl extends ObservableOps<Float> implements ObservableFloat {

    private float value;

    ObservableFloatImpl(float value) {
      this.value = value;
    }

    @Override
    public final void setValue(float value) {
      observers.forEach(observer -> observer.notice(this.value = value));
    }

    @Override
    public final float getValue() {
      return value;
    }

    @Override
    public final void set(Float value) {
      if (value != null) {
        setValue(value);
      }
    }

    @Override
    public final Float get() {
      return value;
    }
  }

  private static final class ObservableDoubleImpl extends ObservableOps<Double> implements ObservableDouble {

    private double value;

    ObservableDoubleImpl(double value) {
      this.value = value;
    }

    @Override
    public final void setValue(double value) {
      observers.forEach(observer -> observer.notice(this.value = value));
    }

    @Override
    public final double getValue() {
      return value;
    }

    @Override
    public final void set(Double value) {
      if (value != null) {
        setValue(value);
      }
    }

    @Override
    public final Double get() {
      return value;
    }
  }

  private static final class ObservableCharImpl extends ObservableOps<Character> implements ObservableChar {

    private char value;

    ObservableCharImpl(char value) {
      this.value = value;
    }

    @Override
    public final void setValue(char value) {
      observers.forEach(observer -> observer.notice(this.value = value));
    }

    @Override
    public final char getValue() {
      return value;
    }

    @Override
    public final void set(Character value) {
      if (value != null) {
        setValue(value);
      }
    }

    @Override
    public final Character get() {
      return value;
    }
  }

  private static final class ObservableBooleanImpl extends ObservableOps<Boolean> implements ObservableBoolean {

    private boolean value;

    ObservableBooleanImpl(boolean value) {
      this.value = value;
    }

    @Override
    public void setValue(boolean value) {
      observers.forEach(observer -> observer.notice(this.value = value));
    }

    @Override
    public boolean getValue() {
      return value;
    }

    @Override
    public void set(Boolean value) {
      if (value != null) {
        setValue(value);
      }
    }

    @Override
    public Boolean get() {
      return value;
    }
  }
}
