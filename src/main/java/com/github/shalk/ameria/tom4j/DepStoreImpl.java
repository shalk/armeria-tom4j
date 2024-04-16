package com.github.shalk.ameria.tom4j;

import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DepStoreImpl implements DepStore {
  TomlParseResult result;
  Map<String, String> versionMap;
  Map<String, Dep> lib;


  public DepStoreImpl() throws IOException, URISyntaxException {
    this.versionMap = new HashMap<>();

    String resourceName = "dependencies.toml";
    Path source = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
    this.result = Toml.parse(source);
    result.errors().forEach(error -> System.err.println(error.toString()));

    TomlTable versions = result.getTable("versions");
    Set<Map.Entry<String, Object>> entries = versions.dottedEntrySet();
    for (Map.Entry<String, Object> entry : entries) {
      versionMap.put(entry.getKey(), (String) entry.getValue());
    }

  }
  @Override
  public String getName(String name) {
    return null;
  }
}
