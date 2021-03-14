plugins {
    id("org.flywaydb.flyway")                 version Versions.flywayGradlePlugin  apply false
    id("org.springframework.boot")            version Versions.springBoot          apply false
    id("io.spring.dependency-management")     version Versions.springDepManagement apply false
    id("com.github.node-gradle.node")         version Versions.nodeJSGradlePlugin  apply false
    id("org.jetbrains.kotlin.kapt")           version Versions.kotlinGlobal        apply false
    id("org.jetbrains.kotlin.multiplatform")  version Versions.kotlinGlobal        apply false
    id("org.jetbrains.kotlin.jvm")            version Versions.kotlinGlobal        apply false
    id("org.jetbrains.kotlin.plugin.noarg")   version Versions.kotlinGlobal        apply false
    id("org.jetbrains.kotlin.plugin.allopen") version Versions.kotlinGlobal        apply false
    id("org.jetbrains.kotlin.plugin.spring")  version Versions.kotlinGlobal        apply false
    id("org.jetbrains.kotlin.plugin.jpa")     version Versions.kotlinGlobal        apply false
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven {
            setUrl("https://repo.spring.io/milestone")
        }
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

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
                jvmTarget = Versions.javaGlobal
                javaParameters = true
                useIR = true
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = Versions.gradle
    }

    register("buildCompositeApp") {
        dependsOn(":diploma-client:buildClient")

        group = "build"

        doFirst {
            print("copying client build files into the server resource directory")
            copy {
                val clientRootDir = project(":diploma-client").projectDir.toString()
                val serverBuildDir = project(":diploma-server:rest-api").buildDir.toString()

                from(java.nio.file.Paths.get(clientRootDir, "dist"))
                into(java.nio.file.Paths.get(serverBuildDir, "resources", "main", "static"))
            }
        }

        finalizedBy(":diploma-server:rest-api:build")
    }
}
