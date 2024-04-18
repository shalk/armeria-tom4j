package com.github.shalk.ameria.tom4j;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class DepStoreImplTest {
  @Test
  public void test() throws IOException, URISyntaxException {
    DepStoreImpl depStore = new DepStoreImpl();
    String name = depStore.getName("libs.assertj");
    System.out.println("name = " + name);
  }

}