package com.github.shalk.ameria.tom4j;

import lombok.SneakyThrows;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DepStoreImpl implements DepStore {
  TomlParseResult result;
  Map<String, String> versionMap;
  Map<String, Dep> lib;

  // key is groupId, value is versionRefKey
  Map<String, String> bomMap;

  @SneakyThrows

  public DepStoreImpl() {
    this.result = Toml.parse(Paths.get(ClassLoader.getSystemResource("dependencies.toml").toURI()));
    this.versionMap = new HashMap<>();
    this.lib = new HashMap<>();
    this.bomMap = new HashMap<>();
    init();
  }

  public void init() {
    // init versionMap
    TomlTable versions = result.getTable("versions");
    Set<Map.Entry<String, Object>> entries = versions.dottedEntrySet();
    for (Map.Entry<String, Object> entry : entries) {
      versionMap.put(entry.getKey(), (String) entry.getValue());
    }
    TomlTable bomTable = result.getTable("boms");
    Set<String> bomKeys = bomTable.keySet();
    for (String bomKey : bomKeys) {
      TomlTable table = bomTable.getTable(bomKey);
      String module = table.getString("module");
      String versionRef = table.getString("version.ref");
      String groupId = module.split(":")[0];
      bomMap.put(groupId, versionRef);
    }


    // init depMap
    TomlTable libTable = result.getTable("libraries");
    Set<String> resultKeys = libTable.keySet();
    for (String resultKey : resultKeys) {
      Dep dep = new Dep();
      String replaceKey = resultKey.replaceAll("_", ".").replaceAll("-", ".");
      lib.put("libs." + replaceKey, dep);

      TomlTable table = libTable.getTable(resultKey);
      String module = table.getString("module");
      dep.setName(resultKey);
      if (module != null) {
        String[] split = module.split(":");
        dep.setGroup(split[0]);
        dep.setArtifact(split[1]);
      }
      // handle version
      if (table.isString("version")) {
        dep.setVersion(table.getString("version"));
      } else {
        String versionRef = table.getString("version.ref");
        if (versionRef != null) {
          String x = versionMap.get(versionRef);
          dep.setVersion(x);
        } else if (inBom(dep)) {
          for (String groupId : bomMap.keySet()) {
            if (dep.getGroup().equals(groupId) || dep.getGroup().startsWith(groupId + ".")) {
              String bomVersionRef = bomMap.get(groupId);
              dep.setVersion(versionMap.get(bomVersionRef));
              break;
            }
          }
        } else {
          throw new RuntimeException("can not find version for " + resultKey);
        }
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

  private boolean inBom(Dep dep) {
    boolean found = false;
    for (String groupId : bomMap.keySet()) {
      if (dep.getGroup().equals(groupId) || dep.getGroup().startsWith(groupId + ".")) {
        found = true;
        break;
      }
    }
    return found;
  }

  @Override
  public Dep getDep(String name) {
    Dep dep1 = getDep1(name);
    if (dep1 == null) {
      throw new RuntimeException("can not handle dep " + name);
    }
    return dep1;
  }

  private Dep getDep1(String name) {
    if (name.startsWith("libs")) {
      return getDepByLibName(name);
    } else if (name.startsWith("project")) {
      return getDepByProjectName(name);
    } else if (name.startsWith("kotlin")) {
      return getDepByKotlinName(name);
    } else {
      throw new RuntimeException("unkown dep type name");
    }
  }

  public Dep getDepByLibName(String name) {
    return lib.get(name);
  }

  public Dep getDepByProjectName(String project) {
    Dep dep = new Dep();
    String projectName = "armeria" +
        project.replaceAll("project\\(", "")
            .replaceAll("\\)", "")
            .replaceAll(":", "-")
            .replaceAll("['\"]", "");
    dep.setArtifact(projectName);
    dep.setGroup("com.linecorp.armeria");
    dep.setVersion(ArmeriaVersion.version);
    return dep;
  }

  public Dep getDepByKotlinName(String project) {
    String libName = "libs.kotlin." + project.replaceAll("kotlin\\(\"", "")
        .replaceAll("\"\\)", "")
        .replaceAll("-", ".");
    return lib.get(libName);
  }
}
