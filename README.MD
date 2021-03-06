# Simple log4j2 vulnerability scanner

[![Current Release](https://badgen.net/github/release/Afrouper/Log4j-Scanner?icon=github)](https://github.com/Afrouper/Log4j-Scanner/releases/latest)
[![Java CI with Maven](https://github.com/Afrouper/Log4j-Scanner/actions/workflows/maven.yml/badge.svg)](https://github.com/Afrouper/Log4j-Scanner/actions/workflows/maven.yml)
[![Build release](https://github.com/Afrouper/Log4j-Scanner/actions/workflows/buildAndRelease.yml/badge.svg)](https://github.com/Afrouper/Log4j-Scanner/actions/workflows/buildAndRelease.yml)
[![Release](https://badgen.net/github/releases/Afrouper/Log4j-Scanner)](https://github.com/Afrouper/Log4j-Scanner/releases)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/Afrouper/Log4j-Scanner.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/Afrouper/Log4j-Scanner/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/Afrouper/Log4j-Scanner.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/Afrouper/Log4j-Scanner/alerts/)
![License](https://badgen.net/github/license/Afrouper/Log4j-Scanner)

Inspired by [logpresso Scanner](https://github.com/logpresso/CVE-2021-44228-Scanner) where I was able to contribute some
parts :-)

And I have to do some automatic builds and releases with GitHub Actions...

## Build

Simple checkout and build via `mvn package`. GraalVM native-images can be created with `mvn package -Pnative`. An
installed [GraalVM](https://www.graalvm.org) with native-image must be installed.

### Autoversioning

This project uses the [Maven Git Versioning Extension](https://github.com/qoomon/maven-git-versioning-extension) to
automaticly set the version numbers. For a flawless experience you need to disable this extension during project import
in IntelliJ. Disable it by adding `-Dversioning.disable=true` to Maven Importer VM options.

## Execute

In the release section you can find the JAR for all JRE 8+ environments. Also you can download a native binary for
linux, macOS or windows.

To execute the scanner simple call `java -jar Log4j-Scanner-<version>.jar scanPath` where 'scanPath' is to directory you
want to scan. All ZIPs, JARs, WARs and EARs in the directory are scanned (also Fat-JARs are supported). Also all
directories are scanned recursive. Native binaries can be executed without installed Java - please pick the correct for
you system.

**Attention:** After downloading the macOS binary you may need to remove the quarantine attribute. Do this
via `xattr -d com.apple.quarantine Log4j-Scanner-mac-amd64`.

You get a return code `0` when everything is fine, `1` if there are vulnereable log4j2 versions.

### Sample Output

```
[*] 🔥 org.apache.logging.log4j:log4j-core:2.12.3 in Log4j-Scanner/src/test/resources/log4j2/log4j-core.2.12.3.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.15.0 in Log4j-Scanner/src/test/resources/log4j2/outerlog4j.jar -> innerlog4j.jar -> log4j-core.2.15.0.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.12.1 in Log4j-Scanner/src/test/resources/log4j2/log4j-core-2.12.1.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.12.3 in Log4j-Scanner/src/test/resources/log4j2/loggingBuddies.war -> log4j-core.2.12.3.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.14.0 in Log4j-Scanner/src/test/resources/log4j2/loggingBuddies.war -> log4j-core.2.14.0.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.16.0 in Log4j-Scanner/src/test/resources/log4j2/log4j-core.2.16.0.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.15.0 in Log4j-Scanner/src/test/resources/log4j2/loggingBuddies.war -> log4j-core.2.15.0.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.12.1 in Log4j-Scanner/src/test/resources/log4j2/loggingBuddies.war -> log4j-core-2.12.1.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.15.0 in Log4j-Scanner/src/test/resources/log4j2/log4j-core.2.15.0.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.16.0 in Log4j-Scanner/src/test/resources/log4j2/loggingBuddies.war -> log4j-core.2.16.0.jar
[*] 🔥 org.apache.logging.log4j:log4j-core:2.14.0 in Log4j-Scanner/src/test/resources/log4j2/log4j-core.2.14.0.jar
```