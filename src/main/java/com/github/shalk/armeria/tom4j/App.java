/* Licensed under Apache-2.0 2024. */
package com.github.shalk.armeria.tom4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class App {
  public static void main(String[] args) throws IOException, URISyntaxException {
    if (args.length < 1) {
      System.out.println("Usage: armeriaPath");
      System.out.println("mvn exec:java -Dexec.args=\"/home/user/armeria\"");
      System.exit(1);
    }
    String armeriaCodeDir = args[0];
    System.out.println("armeriaCodeDir = " + armeriaCodeDir);
    Path depTomlPath = Paths.get(armeriaCodeDir, "dependencies.toml");

    String path = armeriaCodeDir + "/examples";
    // find all file
    FindFinder findFinder = new FindFinder();
    List<Path> gradleList = findFinder.getGradleList(path);

    // load dependencies.toml
    DepStore depstore = new DepStoreImpl(depTomlPath);

    for (Path gradleFilePath : gradleList) {
      // read file to as Pojo GradleFile
      GradleFileReader gradleFileReader = new GradleFileReader();
      // convert  GradleFile to PomFile
      ConvertGradleFileToPomFile convertGradleFileToPomFile =
          new ConvertGradleFileToPomFile(depstore);
      // generate pom.xml from pomFile
      PomFileContentGenerator pomFileContentGenerator = new PomFileContentGenerator();
      String pomFileContent =
          gradleFileReader
              .andThen(convertGradleFileToPomFile)
              .andThen(pomFileContentGenerator)
              .apply(gradleFilePath);

      // write to  pom.xml
      Path pomFilePath = new TargetProcessor().apply(gradleFilePath);
      Files.write(pomFilePath, pomFileContent.getBytes(StandardCharsets.UTF_8));
    }
  }
}
