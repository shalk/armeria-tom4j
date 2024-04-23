/* Licensed under Apache-2.0 2024. */
package com.github.shalk.armeria.tom4j;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExtUtil {

  public static List<String> getExt(String name) {
    try {
      URL url = ExtUtil.class.getClassLoader().getResource("ext-" + name + ".xml");
      Path path = Paths.get(url.toURI());
      return Files.readAllLines(path, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }
}
