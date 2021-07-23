import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Plugins version configuration. Placed here instead of `settings.gradle` by the following reasons:
 * - `settings.gradle` can't see `Versions` declared in `buildScr` project
 * - plugins declared here allows not only to configure the version, but also adds it to the classpath
 */
plugins {
    id("org.flywaydb.flyway")                 version Versions.flywayGradlePlugin  apply false
    id("org.springframework.boot")            version Versions.springBoot          apply false
    id("io.spring.dependency-management")     version Versions.springDepManagement apply false
    id("com.github.node-gradle.node")         version Versions.nodeJSGradlePlugin  apply false
    id("org.jetbrains.kotlin.kapt")           version Versions.kotlinLang          apply false
    id("org.jetbrains.kotlin.multiplatform")  version Versions.kotlinLang          apply false
    id("org.jetbrains.kotlin.jvm")            version Versions.kotlinLang          apply false
    id("org.jetbrains.kotlin.plugin.noarg")   version Versions.kotlinLang          apply false
    id("org.jetbrains.kotlin.plugin.allopen") version Versions.kotlinLang          apply false
    id("org.jetbrains.kotlin.plugin.spring")  version Versions.kotlinLang          apply false
    id("org.jetbrains.kotlin.plugin.jpa")     version Versions.kotlinLang          apply false
}

allprojects {
    group = "org.arhor"

    repositories {
        mavenLocal()
        mavenCentral()
        maven { setUrl("https://repo.spring.io/milestone") }
    }

    tasks {
        withType<JavaCompile> {
            options.compilerArgs.addAll(
                listOf(
                    "-Xlint:unchecked",
                    "-Xlint:deprecation"
                )
            )
        }

        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf(
                    "-Xjsr305=strict",
                    "-Xjvm-default=all"
                )
                jvmTarget = Versions.javaGlobal
                javaParameters = true
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = Versions.gradle
    }

    register("stage") {
        dependsOn(":diploma-client:buildFull")
        finalizedBy(":diploma-server:module-main:build")
    }
}
