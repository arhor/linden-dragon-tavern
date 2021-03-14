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
    implementation("ch.qos.logback:logback-core:${Versions.logback}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")
    implementation("org.slf4j:slf4j-api:${Versions.slf4j}")
    implementation("org.assertj:assertj-core:${Versions.assertJCore}")
    implementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}
