dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://repo.spring.io/milestone")
            content { includeGroupByRegex("org\\.springframework\\..*") }
        }
        gradlePluginPortal()
    }
}
