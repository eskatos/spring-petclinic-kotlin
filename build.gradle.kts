description = "Kotlin version of the Spring Petclinic application"
group = "org.springframework.samples"
// Align with Spring Version
version = "3.4.4"

plugins {
    id("spring-boot-application")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.glassfish.jaxb:jaxb-runtime")
    implementation("javax.cache:cache-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(libs.webjars.bootstrap)
    implementation(libs.webjars.fontawesome)

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly(libs.webjars.webjarsLocatorLite)

    developmentOnly("org.springframework.boot:spring-boot-devtools")
}
