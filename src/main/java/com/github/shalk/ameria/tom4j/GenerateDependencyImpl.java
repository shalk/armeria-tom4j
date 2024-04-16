package com.github.shalk.ameria.tom4j;

public class GenerateDependencyImpl implements GenerateDependency {

  GenerateDependency lib = new GenerateDependencyLibImpl();

  DepStore depStore;

  @Override
  public String generate(String type, String name) {
    if (name.startsWith("lib.")) {
      String libName = name.substring(5);
      return depStore.getName(libName);
    }
    return null;
  }
}
