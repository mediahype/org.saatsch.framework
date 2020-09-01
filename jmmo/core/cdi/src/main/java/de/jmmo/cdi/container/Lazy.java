package de.jmmo.cdi.container;

import java.util.Objects;
import java.util.function.Supplier;

public class Lazy<T> {
  
  private final Supplier<T> supplier;
  private volatile T value;

  public Lazy(Supplier<T> supplier) {
    Objects.requireNonNull(supplier);
    this.supplier = supplier;
  }

  public T get() {
    if (value == null) {
      synchronized (this) {
        if (value == null) {
          value = supplier.get();
        }
      }
    }

    return value;
  }
  
  public static <T> Lazy<T> of(Supplier<T> supplier) {
    return new Lazy<>(supplier);
}
  
}
