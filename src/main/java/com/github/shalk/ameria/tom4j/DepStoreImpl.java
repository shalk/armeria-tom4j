package com.github.shalk.ameria.tom4j;

import lombok.SneakyThrows;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DepStoreImpl implements DepStore {
  TomlParseResult result;
  Map<String, String> versionMap;
  Map<String, Dep> lib;

  @SneakyThrows

  public DepStoreImpl()  {
    String resourceName = "dependencies.toml";
    Path source = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
    result = Toml.parse(source);
//    result.errors().forEach(error -> System.err.println(error.toString()));

    TomlTable versions = result.getTable("versions");
    Set<Map.Entry<String, Object>> entries = versions.dottedEntrySet();
    this.versionMap = new HashMap<>();
    for (Map.Entry<String, Object> entry : entries) {
      versionMap.put(entry.getKey(), (String) entry.getValue());
    }

    lib = new HashMap<>();
    TomlTable libTable = result.getTable("libraries");

    Set<String> resultKeys = libTable.keySet();
    for (String resultKey : resultKeys) {
      Dep dep = new Dep();
      String replaceKey = resultKey.replaceAll("_", ".");
      replaceKey = resultKey.replaceAll("-",".");
      lib.put("libs."+replaceKey, dep);

      TomlTable table = libTable.getTable(resultKey);
      String module = table.getString("module");
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

  @Override
  public String getName(String name) {
    Dep dep = lib.get(name);
    return depToString(dep);
  }

  @Override
  public Dep getDepByName(String name) {
    return lib.get(name);
  }
  private static String depToString(Dep dep) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("<dependency>").append("\n");
    buffer.append("<groupId>").append(dep.getGroup()).append("</groupId>").append("\n");
    buffer.append("<artifactId>").append(dep.getArtifact()).append("</artifactId>").append("\n");
    buffer.append("<version>").append(dep.getVersion()).append("</version>").append("\n");
    if (dep.getScope() != null) {
      buffer.append("<scope>").append(dep.getScope()).append("</scope>").append("\n");
    }
    if (!dep.getExcludes().isEmpty()) {
      buffer.append("<exclusions>\n");
      for (String exclude : dep.getExcludes()) {
        String[] split = exclude.split(":");
        buffer.append(" <exclusion>\n");
        buffer.append("<groupId>").append(split[0]).append("</groupId>").append("\n");
        buffer.append("<artifactId>").append(split[1]).append("</artifactId>").append("\n");
        buffer.append(" </exclusion>\n");
      }
      buffer.append("</exclusions>\n");
    }
    buffer.append("</dependency>").append("\n");
    return buffer.toString();
  }
}
