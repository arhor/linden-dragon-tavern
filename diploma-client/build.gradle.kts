import com.github.gradle.node.npm.task.NpmTask

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
                file("$projectDir/node_modules"),
                file("$projectDir/raw_data"),
                file("$projectDir/dist"),
                file("$projectDir/dist_electron"),
            )
        )
    }
}

tasks {
    val test = register<NpmTask>("test") {
        dependsOn(npmInstall)
        group = "verification"
        description = "Runs JS tests in client project"
        workingDir.fileValue(projectDir)
        args.set(listOf("run", "test:unit"))
    }

    register<NpmTask>("build") {
        dependsOn(npmInstall, test)
        group = "build"
        description = "Builds production version of the app client"
        workingDir.fileValue(projectDir)
        args.set(listOf("run", "build"))
    }
}
