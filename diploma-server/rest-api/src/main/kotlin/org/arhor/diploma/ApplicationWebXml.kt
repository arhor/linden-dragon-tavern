package org.arhor.diploma

import org.arhor.diploma.util.SpringProfile
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

/**
 * This is a helper Java class that provides an alternative to creating a {@code web.xml}.
 * This will be invoked only when the application is deployed to a Servlet container like Tomcat, JBoss etc.
 */
open class ApplicationWebXml : SpringBootServletInitializer() {

    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
        // load a Spring profile to be used as default
        // when there is no `spring.profiles.active` set in the environment or as command line argument.
        // If the value is not available in `application.yml` then `dev` profile will be used as default.
        // The default profile to use when no other profiles are defined
        // This cannot be set in the application.yml file.
        // See https://github.com/spring-projects/spring-boot/issues/1219
        builder.application().setDefaultProperties(
            mapOf("spring.profiles.default" to SpringProfile.DEVELOPMENT)
        )

        return builder.sources(DiplomaApp::class.java)
    }
}
