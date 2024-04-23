/* Licensed under Apache-2.0 2024. */
package com.github.shalk.armeria.tom4j;

public class DepUtil {
  public static String depToString(Dep dep) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("<dependency>").append("\n");
    buffer.append("<groupId>").append(dep.getGroup()).append("</groupId>").append("\n");
    buffer.append("<artifactId>").append(dep.getArtifact()).append("</artifactId>").append("\n");
    buffer.append("<version>").append(dep.getVersion()).append("</version>").append("\n");
    if (dep.getScope() != null) {
      buffer.append("<scope>").append(dep.getScope()).append("</scope>").append("\n");
    }
    if (!dep.getExcludes().isEmpty()) {
      buffer.append("<exclusions>\n");
      for (String exclude : dep.getExcludes()) {
        String[] split = exclude.split(":");
        buffer.append(" <exclusion>\n");
        buffer.append("<groupId>").append(split[0]).append("</groupId>").append("\n");
        buffer.append("<artifactId>").append(split[1]).append("</artifactId>").append("\n");
        buffer.append(" </exclusion>\n");
      }
      buffer.append("</exclusions>\n");
    }
    buffer.append("</dependency>").append("\n");
    return buffer.toString();
  }

  public static Dep genDaggerCompiler(String version) {
    Dep dep = new Dep();
    dep.setVersion(version);
    dep.setGroup("com.google.dagger");
    dep.setArtifact("dagger-compiler");
    dep.setScope("provided");
    return dep;
  }
}
