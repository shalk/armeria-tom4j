package com.github.shalk.ameria.tom4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PomFileManager {

  private static ScopeUtil scopeUtil = new ScopeUtil();
  private static DepStore depStore = new DepStoreImpl();

  public PomFileManager() {
  }

  public static List<PomFile> parse(List<GradleFile> gradleFiles) {
    List<PomFile> ret = new ArrayList<>();
    for (GradleFile gradleFile : gradleFiles) {
      ret.add(trans(gradleFile));
    }
    return ret;
  }

  private static PomFile trans(GradleFile k) {
    PomFile pomFile = new PomFile();
    String filename = k.getFilename();
    pomFile.setFilename(filename);
    pomFile.setG("com.linecorp.armeria");
    pomFile.setA(getA(filename));
    pomFile.setV("1.0.0-SNAPSHOT");
    pomFile.setDep(getDeps(k));
    return pomFile;
  }

  private static List<Dep> getDeps(GradleFile k) {
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

  static String getA(String filename) {
    int index = filename.indexOf("examples");
    String sub = filename.substring(index + "examples".length());
    int end = sub.lastIndexOf("/");
    String mid = sub.substring(0, end);
    String replace = mid.replace("/", "-");
    return "example" + replace;
  }
}
