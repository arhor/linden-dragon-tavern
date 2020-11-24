import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.task.NodeTask
import java.nio.file.Paths

plugins {
    id("com.github.node-gradle.node")
    id("idea")
}

group = "org.arhor"
version = "0.0.1-SNAPSHOT"
description = "diploma-client"

node {
    version.set(Versions.nodeJS)
    download.set(true)
}

idea {
    module {
        excludeDirs.addAll(
            listOf(
                file("node_modules"),
                file("raw_data"),
                file("dist"),
                file("dist_electron")
            )
        )
    }
}

dependencies {
    project(":diploma-shared")
}

tasks {
    val injectSharedLib =
        register<Copy>("injectSharedLib") {
            dependsOn(":diploma-shared:build")

            from(Paths.get(project(":diploma-shared").buildDir.toString(), "compiled-js"))
            into(Paths.get(project(":diploma-client").projectDir.toString(), "src", "lib"))
        }

    val clientTest =
        register<NpmTask>("clientTest") {
            dependsOn(npmInstall, injectSharedLib)
            group = "verification"
            description = "Runs JS tests in client project"
            workingDir.fileValue(projectDir)
            args.set(listOf("run", "test:unit"))
        }

    val buildClient =
        register<NpmTask>("buildClient") {
            dependsOn(clientTest)
            group = "build"
            description = "Builds production version of the app client"
            workingDir.fileValue(projectDir)
            args.set(listOf("run", "build"))
        }

    val clientStart =
        register<NpmTask>("clientStart") {
            dependsOn(npmInstall, injectSharedLib)
            description = "Starts client development server"
            workingDir.fileValue(projectDir)
            args.set(listOf("run", "serve"))
        }

    val scrap =
        register<NodeTask>("scrap") {
            dependsOn(npmInstall)
            script.fileValue(file("scripts/dungeon_su_scrapper.js"))
        }
}
