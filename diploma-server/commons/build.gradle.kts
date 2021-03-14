plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "org.arhor"
version = "0.0.1-SNAPSHOT"
description = "commons"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.code.findbugs:jsr305:${Versions.findbugsJsr305}")

    testImplementation(project(":diploma-test-utils"))
    testImplementation("org.assertj:assertj-core:${Versions.assertJCore}")
    testImplementation("org.mockito:mockito-core:${Versions.mockitoCore}")
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

