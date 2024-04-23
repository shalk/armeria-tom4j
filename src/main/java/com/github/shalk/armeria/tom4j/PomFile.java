/* Licensed under Apache-2.0 2024. */
package com.github.shalk.armeria.tom4j;

import java.util.List;
import lombok.Data;

@Data
public class PomFile {

  private String filename;
  private String g;
  private String a;
  private String v;

  private List<Dep> dep;

  private List<String> plugin;
  private List<String> extension;

  private String execMain;
}
