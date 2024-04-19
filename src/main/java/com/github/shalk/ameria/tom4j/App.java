package com.github.shalk.ameria.tom4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class App {
  public static void main(String[] args) throws IOException, URISyntaxException {
    String path = System.getenv("HOME") + "/code/github.com/shalk/armeria/examples";
    List<Path> gradleList = FindFinder.getGradleList(path);
    List<GradleFile> gradleFiles = GradleFileManager.getGradleFile(gradleList);
    List<PomFile> pomFiles = PomFileManager.parse(gradleFiles);
    Map<String, String> pomFileMap = PomFileGenManager.gen(pomFiles);
    for (Map.Entry<String, String> entry : pomFileMap.entrySet()) {
      String value = entry.getValue();
      System.out.println("value = " + value);
      break;
    }
  }
}
