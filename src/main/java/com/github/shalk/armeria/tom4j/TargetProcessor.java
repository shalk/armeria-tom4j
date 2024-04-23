/* Licensed under Apache-2.0 2024. */
package com.github.shalk.armeria.tom4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.UnaryOperator;

public class TargetProcessor implements UnaryOperator<Path> {
  @Override
  public Path apply(Path gradleFIle) {
    Path parent = gradleFIle.getParent();
    return Paths.get(parent.toString(), "pom.xml");
  }
}
