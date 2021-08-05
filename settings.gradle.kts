pluginManagement {
    repositories {
        maven { setUrl("https://repo.spring.io/release") }
        gradlePluginPortal()
    }
}

rootProject.name = "diploma"

include(
    ":diploma-client",
    ":diploma-server:module-common",
    ":diploma-server:module-dnd",
    ":diploma-server:module-main",
    ":diploma-shared",
    ":diploma-test-utils"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
