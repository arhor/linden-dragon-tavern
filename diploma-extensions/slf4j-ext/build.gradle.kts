plugins {
    id("org.jetbrains.kotlin.jvm")
}

version = "0.0.1-SNAPSHOT"
description = "diploma-extensions:slf4j-ext"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.slf4j:slf4j-api:${Versions.slf4j}")

    testImplementation("org.assertj:assertj-core:${Versions.assertJCore}")
    testImplementation("org.mockito:mockito-core:${Versions.mockitoCore}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoCore}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}")
    testRuntimeOnly("ch.qos.logback:logback-core:${Versions.logback}")
    testRuntimeOnly("ch.qos.logback:logback-classic:${Versions.logback}")
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
