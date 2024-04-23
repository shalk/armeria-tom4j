/* Licensed under Apache-2.0 2024. */
package com.github.shalk.armeria.tom4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ConvertGradleFileToPomFile implements Function<GradleFile, PomFile> {

  private static final ScopeUtil scopeUtil = new ScopeUtil();
  private final DepStore depStore;

  public ConvertGradleFileToPomFile(DepStore depStore) {
    this.depStore = depStore;
  }

  static String getA(String filename) {
    int index = filename.indexOf("examples");
    String sub = filename.substring(index + "examples".length());
    int end = sub.lastIndexOf("/");
    String mid = sub.substring(0, end);
    String replace = mid.replace("/", "-");
    return "example" + replace;
  }

  public PomFile apply(GradleFile k) {
    PomFile pomFile = new PomFile();
    String filename = k.getFilename();
    pomFile.setFilename(filename);
    pomFile.setG("com.linecorp.armeria");
    pomFile.setA(getA(filename));
    pomFile.setV("1.0.0-SNAPSHOT");
    List<Dep> deps = getDeps(k);

    // deps corner case
    if (pomFile.getA().contains("dagger")) {
      deps.add(DepUtil.genDaggerCompiler(depStore.getVersion("dagger")));
    }
    pomFile.setDep(deps);

    if (pomFile.getA().contains("grpc")) {
      pomFile.getPlugin().add("grpc");
      pomFile.getExtension().add("os");
      String grpcJavaVersion = depStore.getVersion("grpc-java");
      pomFile.getProperties().put("dep.grpc-java.version", grpcJavaVersion);

      String protobufVersion = depStore.getVersion("protobuf");
      pomFile.getProperties().put("dep.protobuf.version", protobufVersion);
    }
    if (pomFile.getA().contains("thrift")) {
      pomFile.getPlugin().add("thrift");
    }
    return pomFile;
  }

  List<Dep> getDeps(GradleFile k) {
    List<Dep> depList = new ArrayList<>();
    Map<String, String> depMap = k.getDep();
    for (Map.Entry<String, String> entry : depMap.entrySet()) {
      String lib = entry.getKey();
      String type = entry.getValue();
      Dep dep = depStore.getDep(lib);
      dep.setScope(scopeUtil.getScope(type));
      depList.add(dep);
    }

    return depList;
  }
}
