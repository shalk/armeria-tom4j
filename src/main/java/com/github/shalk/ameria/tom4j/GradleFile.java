package com.github.shalk.ameria.tom4j;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GradleFile {

  private String filename;
  private Map<String, String> dep = new HashMap<>();

  private String exec;

}
