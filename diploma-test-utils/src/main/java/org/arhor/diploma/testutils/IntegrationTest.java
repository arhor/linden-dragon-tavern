package org.arhor.diploma.testutils;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Tag("integration")
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
}
