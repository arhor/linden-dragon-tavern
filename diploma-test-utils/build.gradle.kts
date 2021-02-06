plugins {
    id("java-library")
    id("maven-publish")
}

group = "org.arhor"
version = "0.0.1-SNAPSHOT"
description = "diploma-test-utils"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("ch.qos.logback:logback-core:${Deps.logback}")
    implementation("ch.qos.logback:logback-classic:${Deps.logback}")
    implementation("org.slf4j:slf4j-api:${Deps.slf4j}")
    implementation("org.assertj:assertj-core:${Deps.assertJCore}")
    implementation("org.junit.jupiter:junit-jupiter-api:${Deps.junitJupiter}")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Deps.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Deps.javaGlobal)
}
