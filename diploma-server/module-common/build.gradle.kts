plugins {
    id("org.jetbrains.kotlin.jvm")
}

version = "0.0.1-SNAPSHOT"
description = "diploma-server:module-common"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.slf4j:slf4j-api:${Versions.slf4j}")
    implementation("io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}")
    implementation("com.google.code.findbugs:jsr305:${Versions.findbugsJsr305}")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("org.assertj:assertj-core:${Versions.assertJCore}")
    testImplementation("org.mockito:mockito-core:${Versions.mockitoCore}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoCore}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}")
}

java {
    sourceCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
    targetCompatibility = JavaVersion.toVersion(Versions.javaGlobal)
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

