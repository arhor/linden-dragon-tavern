package org.arhor.diploma.data.persistence.audit

import java.lang.annotation.Inherited

@Inherited
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Audit(
    /**
     * org.arhor.diploma.data.audit.Audit-log table name.
     */
    val table: String = DEFAULT,
) {

    companion object {
        const val DEFAULT = "##default"
    }
}
