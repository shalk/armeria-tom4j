package com.github.shalk.ameria.tom4j;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class DepStoreImplTest {
  @Test
  public void test() throws IOException, URISyntaxException {
    DepStoreImpl depStore = new DepStoreImpl();
    Dep dep = depStore.getDep("libs.assertj");
    String name = DepUtil.depToString(dep);
    System.out.println("name = " + name);
  }

}