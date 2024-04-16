package com.github.shalk.ameria.tom4j;

import lombok.Data;

import java.util.List;


@Data
public class Dep {
  private String name;
  private String group;
  private String artifact;
  private String version;
  private String scope;
  private List<String> excludes;

}
