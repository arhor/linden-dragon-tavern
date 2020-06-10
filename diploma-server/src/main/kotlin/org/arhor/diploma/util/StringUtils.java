package org.arhor.diploma.util;

import java.util.StringJoiner;

@SuppressWarnings("unused")
public final class StringUtils {

  private StringUtils() {
    throw new UnsupportedOperationException("Must not be instantiated");
  }

  public static class JsonBuilder {

    private final StringJoiner body;

    private boolean pretty;

    private JsonBuilder() {
      this.body = new StringJoiner(", ", "{ ", " }");
    }

    public static JsonBuilder json() {
      return new JsonBuilder();
    }

    public String build() {
      return body.toString();
    }

    public JsonBuilder pretty(boolean pretty) {
      this.pretty = pretty;
      return this;
    }

    public JsonBuilder prop(String name, boolean value) {
      body.add(quote(name) + ": " + value);
      return this;
    }

    public JsonBuilder prop(String name, int value) {
      body.add(quote(name) + ": " + value);
      return this;
    }

    public JsonBuilder prop(String name, long value) {
      body.add(quote(name) + ": " + value);
      return this;
    }

    public JsonBuilder prop(String name, float value) {
      body.add(quote(name) + ": " + value);
      return this;
    }

    public JsonBuilder prop(String name, double value) {
      body.add(quote(name) + ": " + value);
      return this;
    }

    public JsonBuilder prop(String name, JsonBuilder value) {
      body.add(quote(name) + ": " + value.body);
      return this;
    }

    public JsonBuilder prop(String name, String value) {
      body.add(quote(name) + ": " + quote(value));
      return this;
    }

    private static String quote(String s) {
      return '"' + s + '"';
    }
  }

}
