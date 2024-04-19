package com.github.shalk.ameria.tom4j;

import java.util.Map;

public class ScopeUtil {

  public Map<String,String> map;

  public ScopeUtil() {
    map.put("testImplementation", "test");
    map.put("runtimeOnly", "runtime");
    map.put("compileOnly", "compile");
    map.put("Implementation", "compile");
  }

  public String getScope(String type) {
    return map.get(type);
  }
}
