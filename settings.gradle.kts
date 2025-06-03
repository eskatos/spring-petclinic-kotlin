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

plugins {
    id("com.gradle.develocity").version("4.0.2")
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

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/help/legal-terms-of-use"
        termsOfUseAgree = "yes"
        publishing.onlyIf { true }
    }
}
