package com.github.shalk.armeria.tom4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvertGradleFileToPomFileTest {

  @Test
  void getA() {
    String a = "/Users/shalk/code/github.com/shalk/armeria/examples/context-propagation/kotlin/build.gradle.kts";
    String a1 = ConvertGradleFileToPomFile.getA(a);
    assertEquals("example-context-propagation-kotlin",a1);
  }
}