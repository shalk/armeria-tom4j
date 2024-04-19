package com.github.shalk.ameria.tom4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindFinder {



  static List<Path> getGradleList(String path) throws IOException {
    Stream<Path> pathStream = Files.find(Paths.get(path), 10, new BiPredicate<Path, BasicFileAttributes>() {
      @Override
      public boolean test(Path path, BasicFileAttributes basicFileAttributes) {
        return path.endsWith("build.gradle") || path.endsWith("build.gradle.kts");
      }
    });
    return pathStream.collect(Collectors.toList());
  }
}
