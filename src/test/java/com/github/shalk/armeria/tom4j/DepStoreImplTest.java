package com.github.shalk.armeria.tom4j;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

class DepStoreImplTest {
  @Test
  public void test() throws IOException, URISyntaxException {
    Path path = Paths.get(ClassLoader.getSystemResource("dependencies.toml").toURI());
    DepStoreImpl depStore = new DepStoreImpl(path);
    Dep dep = depStore.getDep("libs.assertj");
    String name = DepUtil.depToString(dep);
    System.out.println("name = " + name);
  }

}