package com.github.arhor.linden.dragon.tavern;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTag;
import com.tngtech.archunit.junit.ArchTest;

@ArchTag("arch")
@AnalyzeClasses(
    packages = {
        "dev.arhor.simple.todo..",
    },
    importOptions = {
        DoNotIncludeTests.class,
    }
)
class ApplicationArchitectureTest {

    @ArchTest
    void correct_layered_architecture_should_be_observed(final JavaClasses applicationClasses) {
        layeredArchitecture()
            .layer("Web").definedBy("dev.arhor.simple.todo.web..")
            .layer("Service").definedBy("dev.arhor.simple.todo.service..")
            .layer("Persistence").definedBy("dev.arhor.simple.todo.data..")
            .whereLayer("Web").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Web")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
            .check(applicationClasses);
    }
}
