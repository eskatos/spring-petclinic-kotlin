rootProject.name = "spring-petclinic-kotlin"

pluginManagement {
    repositories {
        maven {
            url = uri("https://repo.spring.io/milestone")
            content { includeGroupByRegex("org\\.springframework\\..*") }
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://repo.spring.io/milestone")
            content { includeGroupByRegex("org\\.springframework\\..*") }
        }
        mavenCentral()
    }
}
