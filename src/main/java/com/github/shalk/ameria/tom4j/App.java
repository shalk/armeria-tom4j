package com.github.shalk.ameria.tom4j;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
  public static void main(String[] args) {
    Path source = Paths.get("/path/to/file.toml");
    TomlParseResult result = Toml.parse(source);
    result.errors().forEach(error -> System.err.println(error.toString()));

    String value = result.getString("a. dotted . key");
  }
}
