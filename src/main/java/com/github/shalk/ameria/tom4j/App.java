package com.github.shalk.ameria.tom4j;

import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class App {
  public static void main(String[] args) throws IOException, URISyntaxException {
    String resourceName = "dependencies.toml";
    Path source = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
    TomlParseResult result = Toml.parse(source);
    result.errors().forEach(error -> System.err.println(error.toString()));

    TomlTable versions = result.getTable("versions");
    Set<Map.Entry<String, Object>> entries = versions.dottedEntrySet();
    Map<String, String> versionMap = new HashMap<>();
    for (Map.Entry<String, Object> entry : entries) {
      versionMap.put(entry.getKey(), (String) entry.getValue());
    }
    String value = result.getString("versions. asm");
    System.out.println("value = " + value);

    Set<String> resultKeys = result.dottedKeySet();
    for (String resultKey : resultKeys) {
      if (resultKey.startsWith("libaries")) {
        TomlTable table = result.getTable(resultKey);
//        if(table.getString(""))
      }
    }

  }
}
