# Backend Static Analysis TODO (Spring Boot + Maven)

Current: No project files present. Create a Maven Spring Boot scaffold.

1) Create Maven project structure
- pom.xml (Spring Boot parent)
- src/main/java, src/main/resources
- src/test/java

2) Add plugins to pom.xml
- Checkstyle:
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.3.1</version>
    <configuration>
      <configLocation>checkstyle.xml</configLocation>
      <encoding>UTF-8</encoding>
      <consoleOutput>true</consoleOutput>
      <failsOnError>true</failsOnError>
    </configuration>
    <executions>
      <execution>
        <phase>verify</phase>
        <goals><goal>check</goal></goals>
      </execution>
    </executions>
  </plugin>

- PMD:
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>3.25.0</version>
    <configuration>
      <printFailingErrors>true</printFailingErrors>
      <rulesets>
        <ruleset>pmd-ruleset.xml</ruleset>
      </rulesets>
      <failOnViolation>true</failOnViolation>
    </configuration>
    <executions>
      <execution>
        <phase>verify</phase>
        <goals><goal>check</goal></goals>
      </execution>
    </executions>
  </plugin>

- SpotBugs:
  <plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <version>4.8.6.4</version>
    <configuration>
      <effort>max</effort>
      <threshold>Low</threshold>
      <failOnError>true</failOnError>
    </configuration>
    <executions>
      <execution>
        <phase>verify</phase>
        <goals><goal>check</goal></goals>
      </execution>
    </executions>
  </plugin>

- OWASP Dependency-Check:
  <plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>10.0.4</version>
    <configuration>
      <failBuildOnCVSS>7</failBuildOnCVSS>
      <format>ALL</format>
    </configuration>
    <executions>
      <execution>
        <goals><goal>check</goal></goals>
      </execution>
    </executions>
  </plugin>

3) Provide rule configs in repo root
- checkstyle.xml (extend Google or Sun checks; adjust)
- pmd-ruleset.xml (avoidDuplicateLiterals, cyclomaticComplexity, lawOfDemeter where applicable)
- spotbugs-exclude.xml (optional)

4) Commands (non-interactive)
- mvn -q -DskipTests checkstyle:check
- mvn -q -DskipTests pmd:check
- mvn -q -DskipTests com.github.spotbugs:spotbugs-maven-plugin:check
- mvn -q -DskipTests org.owasp:dependency-check-maven:check

5) CI suggestions
- Fail on Checkstyle/PMD/SpotBugs violations
- Upload dependency-check and unit test reports as artifacts
