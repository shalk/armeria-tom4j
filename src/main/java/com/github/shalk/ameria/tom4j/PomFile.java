package com.github.shalk.ameria.tom4j;

import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class PomFile {

  private String filename;
  private String g;
  private String a;
  private String v;

  private List<Dep> dep;

  private String execMain;

}
