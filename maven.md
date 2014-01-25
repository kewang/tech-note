# Maven

## 什麼是Maven

At first glance Maven can appear to be many things, but in a nutshell Maven is an attempt to apply patterns to a project's build infrastructure in order to promote comprehension and productivity by providing a clear path in the use of best practices. Maven is essentially a project management and comprehension tool and as such provides a way to help with managing:

* Builds
* Documentation
* Reporting
* Dependencies
* SCMs
* Releases
* Distribution

You have walked through the process for setting up, building, testing, packaging, and installing a typical Maven project.

## file structures

```
my-app
|-- pom.xml
`-- src
    |-- main
    |   `-- java
    |       `-- com
    |           `-- mycompany
    |               `-- app
    |                   `-- App.java
    `-- test
        `-- java
            `-- com
                `-- mycompany
                    `-- app
                        `-- AppTest.java
```

## 什麼是pom.xml

## mvn command

* `mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=com.mycompany.app -DartifactId=my-app`: create architecture
* `mvn compile`: compile your application sources
* `mvn test`: compile test sources and run unit tests
* `mvn test-compile`: only compile test sources
* `mvn package`: create a JAR file
* `mvn install`: install artifact to local repository
* `mvn site`: generate a website for the project
* `mvn clean`: clean `target` folder
* `mvn idea:idea`: generate an IntelliJ IDEA descriptor for the project
* `mvn eclipse:eclipse`: generate an Eclipse descriptor for the project

## use plugin

### configure the Java compiler example
```
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>2.5.1</version>
      <configuration>
        <source>1.6</source>
        <target>1.6</target>
      </configuration>
    </plugin>
  </plugins>
</build>
```

## 如何publish自己的library到maven repository上
## sonatype是什麼？

http://www.apache.org/dev/publishing-maven-artifacts.html
https://docs.sonatype.org/display/Repository/How+To+Generate+PGP+Signatures+With+Maven
http://datumedge.blogspot.tw/2012/05/publishing-from-github-to-maven-central.html