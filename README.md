# Jar file Utility

## Overview

## Usage

- When the project needs package name list from dependent jar file
  - Use `JarPackageFinder#collectPackageNameFromJar()`
- When the project needs package name list with given jar file path
  - Use `JarPackageFinder#collectPackageNameFromJarPath()`

Note that all process throws the exception, `JarFileReferenceException` when the process cannot access to the jar file.

## Development

This application is built with the environment bellow;

- OpenJDK 17
- IntelliJ IDEA 2023.1.2

