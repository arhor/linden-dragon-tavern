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
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.assertj:assertj-core:${Versions.assertJCore}")
    implementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}
