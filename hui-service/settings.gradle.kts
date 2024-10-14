pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
  }
}
plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    maven("https://jitpack.io")
    google()
    mavenCentral()
    maven("https://mvnrepository.com/")
  }
}
rootProject.name = "hui-service"
