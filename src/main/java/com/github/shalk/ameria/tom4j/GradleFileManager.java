package com.github.shalk.ameria.tom4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleFileManager {
  static String rex = "^\\s*(implementation|runtimeOnly|Implementation|compileOnly)\\s+(\\S+)";
  static Pattern p = Pattern.compile(rex);
  static List<GradleFile> getGradleFile(List<Path> paths) throws IOException {
    List<GradleFile> gradleFiles = new ArrayList<>();
    for (Path path : paths) {
      GradleFile file = new GradleFile();
      file.setFilename(path.toString());
      List<String> lines = Files.readAllLines(path);
      for (String line : lines) {
        Matcher matcher = p.matcher(line);
        if (matcher.matches()) {
          String group1 = matcher.group(1);
          String group2 = matcher.group(2);
          file.getDep().put(group2, group1);
        }
      }
    }
    return gradleFiles;


  }
}
