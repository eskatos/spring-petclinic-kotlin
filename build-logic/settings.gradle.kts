dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://repo.spring.io/milestone")
            content { includeGroupByRegex("org\\.springframework\\..*") }
        }
        gradlePluginPortal()
    }
    versionCatalogs {
        register("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
