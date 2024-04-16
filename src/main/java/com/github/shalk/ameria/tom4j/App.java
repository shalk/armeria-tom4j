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

    TomlTable libTable = result.getTable("libraries");

    Set<String> resultKeys = libTable.keySet();
    for (String resultKey : resultKeys) {
      TomlTable table = libTable.getTable(resultKey);
      String module = table.getString("module");
      Dep dep = new Dep();
      dep.setName(resultKey);
      if (module != null) {
        String[] split = module.split(":");
        dep.setGroup(split[0]);
        dep.setArtifact(split[1]);
      }
      String versionRef = table.getString("version.ref");
      if (versionRef != null) {
        String x = versionMap.get(versionRef);
        dep.setVersion(x);
      }
      Object o = table.get("exclusions");
      if (o != null) {
          if (o instanceof String) {
            dep.getExcludes().add((String) o);
          } else if (o instanceof TomlArray) {
            TomlArray o1 = (TomlArray) o;
            for (Object object : o1.toList()) {
              dep.getExcludes().add((String) object);
            }
          }
      }

    }

  }
}
