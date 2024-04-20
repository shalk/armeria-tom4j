package com.github.shalk.ameria.tom4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
  public static void main(String[] args) throws IOException, URISyntaxException {
    String path = System.getenv("HOME") + "/code/github.com/shalk/armeria/examples";
    // find all file
    List<Path> gradleList = FindFinder.getGradleList(path);
    TargetProcessor processor = new TargetProcessor();
    List<Path> targetList = gradleList.stream().map(processor).collect(Collectors.toList());

    // read file to memory
    List<GradleFile> gradleFiles = GradleFileManager.getGradleFile(gradleList);
    // covert gradle file pojo to pom file pojo
    List<PomFile> pomFiles = PomFileManager.parse(gradleFiles);
    // convert pom file pojo to pom.xml
    List<String> pomFileMap = PomFileGenManager.gen(pomFiles);

    for (int i = 0; i < gradleList.size(); i++) {
      Files.write(targetList.get(i), pomFileMap.get(i).getBytes(StandardCharsets.UTF_8));
    }

  }
}
