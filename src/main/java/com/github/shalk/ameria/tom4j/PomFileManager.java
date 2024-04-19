package com.github.shalk.ameria.tom4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PomFileManager {

  private static ScopeUtil scopeUtil = new ScopeUtil();
  private static DepStore depStore = new DepStoreImpl();

  public PomFileManager() throws IOException, URISyntaxException {
  }

  public static List<PomFile> parse(List<GradleFile> gradleFiles) {
    return gradleFiles.stream().map(k -> trans(k)).collect(Collectors.toList());
  }

  private static PomFile trans(GradleFile k) {
    PomFile pomFile = new PomFile();
    String filename = k.getFilename();
    pomFile.setFilename(filename);
    pomFile.setG("com.linecorp.armeria");
    pomFile.setA(getA(filename));
    pomFile.setV("1.0.0-SNAPSHOT");
    List<Dep> depMap = new ArrayList<>();
    Map<String, String> dep = k.getDep();
    for (Map.Entry<String, String> entry : dep.entrySet()) {
      String lib = entry.getKey();
      String type = entry.getValue();
      scopeUtil.getScope(type);
      depStore.getDepByName(lib);
    }
    pomFile.setDep(depMap);
  }

  private static String getA(String filename) {
    return null;
  }
}
