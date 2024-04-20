package com.github.shalk.ameria.tom4j;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TargetProcessorTest {

  @Test
  void apply() {
    String a = "/aaaa/examples/grpc-kotlin/build.gradle.kts";
    Path path = Paths.get(a);
    TargetProcessor processor = new TargetProcessor();
    assertEquals("/aaaa/examples/grpc-kotlin/pom.xml", processor.apply(path).toString());
  }
}