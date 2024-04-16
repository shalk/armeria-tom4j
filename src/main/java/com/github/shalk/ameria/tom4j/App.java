package com.github.shalk.ameria.tom4j;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
  public static void main(String[] args) throws IOException, URISyntaxException {
    String resourceName = "dependencies.toml";
    Path source = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
    TomlParseResult result = Toml.parse(source);
    result.errors().forEach(error -> System.err.println(error.toString()));

    String value = result.getString("versions. asm");
    System.out.println("value = " + value);
  }
}
