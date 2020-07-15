package org.arhor.diploma

import org.arhor.diploma.util.addDefaultProfile
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

/**
 * This is a helper Java class that provides an alternative to creating a {@code web.xml}.
 * This will be invoked only when the application is deployed to a Servlet container like Tomcat, JBoss etc.
 */
open class ApplicationWebXml : SpringBootServletInitializer() {

    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
        // set a default to use when no profile is configured.
        addDefaultProfile(builder.application())
        return builder.sources(DiplomaApp::class.java)
    }
}
