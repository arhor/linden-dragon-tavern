plugins {
    id("java-library")
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
    implementation("org.assertj:assertj-core:${Versions.assertJCore}")
    implementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}