package org.arhor.commons.util;

import java.util.StringJoiner;

public final class JsonUtils {

  private JsonUtils() { throw new UnsupportedOperationException("Must not be instantiated"); }

  public static String json(Node<?> ...nodes) {
    var body = new StringJoiner(", ", "{ ", " }");
    for (var node : nodes) {
      body.add(String.valueOf(node));
    }
    return body.toString();
  }

  public static class Node<T> {

    private final String name;
    private final T value;

    private Node(String name, T value) {
      this.name = name;
      this.value = value;
    }

    public static <T> Node<T> $(String name, T value) {
      return new Node<>(name, value);
    }

    @Override
    public String toString() {
      return value instanceof Number || value instanceof Boolean
          ? '"' + name + '"' + ": " + value
          : '"' + name + '"' + ": " + '"' + value + '"';
    }
  }
}
