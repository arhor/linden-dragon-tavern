import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.npm.task.NpxTask
import com.github.gradle.node.task.NodeTask
import java.nio.file.Paths

plugins {
    id("com.github.node-gradle.node")
    id("idea")
}

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
    val injectSharedLib = register("injectSharedLib") {
        dependsOn(":diploma-shared:build")

        doLast {
            copy {
                val sharedBldDir = project(":diploma-shared").buildDir.toString()
                val clientPrjDir = project(":diploma-client").projectDir.toString()

                from(Paths.get(sharedBldDir, "compiled-js"))
                into(Paths.get(clientPrjDir, "src", "lib"))
            }
        }
    }

    val test = register<NpmTask>("test") {
        dependsOn(npmInstall)
        group = "verification"
        description = "Runs JS tests in client project"
        workingDir.fileValue(projectDir)
        args.set(listOf("run", "test:unit"))
    }

    val dll = register<NpmTask>("dll") {
        dependsOn(npmInstall)
        group = "build"
        description = "Builds vendors bundle"
        workingDir.fileValue(projectDir)
        args.set(listOf("run", "dll"))
    }

    val build = register<NpmTask>("build") {
        dependsOn(npmInstall, injectSharedLib, test)
        group = "build"
        description = "Builds production version of the app client"
        workingDir.fileValue(projectDir)
        args.set(listOf("run", "build"))
    }

    register("buildFull") {
        group = "build"
        description = "Builds dll and sources"
        dependsOn(dll)
        finalizedBy(build)
    }

    register<NpmTask>("clientStart") {
        dependsOn(npmInstall, injectSharedLib)
        description = "Starts client development server"
        workingDir.fileValue(projectDir)
        args.set(listOf("run", "serve"))
    }

    register<NpmTask>("npmAudit") {
        workingDir.fileValue(projectDir)
        args.set(listOf("audit"))
    }

    register<NpmTask>("npmAuditFix") {
        workingDir.fileValue(projectDir)
        args.set(listOf("audit", "fix"))
    }

    register<NpxTask>("updateBrowsersList") {
        workingDir.fileValue(projectDir)
        command.set("browserslist@latest")
        args.set(listOf("--update-db"))
    }

    register<NodeTask>("scrap") {
        dependsOn(npmInstall)
        script.fileValue(file("scripts/dungeon_su_scrapper.js"))
    }
}
