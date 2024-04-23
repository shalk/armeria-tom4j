/* Licensed under Apache-2.0 2024. */
package com.github.shalk.armeria.tom4j;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class TargetProcessorTest {

  @Test
  void apply() {
    String a = "/aaaa/examples/grpc-kotlin/build.gradle.kts";
    Path path = Paths.get(a);
    TargetProcessor processor = new TargetProcessor();
    assertEquals("/aaaa/examples/grpc-kotlin/pom.xml", processor.apply(path).toString());
  }
}
