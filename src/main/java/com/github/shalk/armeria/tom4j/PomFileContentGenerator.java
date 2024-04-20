package com.github.shalk.armeria.tom4j;

import java.util.List;
import java.util.function.Function;

public class PomFileContentGenerator implements Function<PomFile, String> {


  public String apply(PomFile pomFile) {
    StringBuilder builder = new StringBuilder();
    builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
        "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
        "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
        "    <modelVersion>4.0.0</modelVersion>\n" +
        "\n" +
        "    <groupId>" + pomFile.getG() + "</groupId>\n" +
        "    <artifactId>" + pomFile.getA() + "</artifactId>\n" +
        "    <version>" + pomFile.getV() + "</version>\n" +
        "\n" +
        "    <properties>\n" +
        "        <maven.compiler.source>17</maven.compiler.source>\n" +
        "        <maven.compiler.target>17</maven.compiler.target>\n" +
        "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
        "    </properties>\n");
    builder.append("    <dependencies>\n");
    List<Dep> deps = pomFile.getDep();
    for (Dep dep : deps) {
      builder.append(DepUtil.depToString(dep));
    }
    builder.append("    </dependencies>\n");
    builder.append("</project>");
    return builder.toString();
  }
}
