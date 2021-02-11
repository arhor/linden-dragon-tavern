package org.arhor.diploma.data

// Hibernate entities with generated ID should have fixed hashCode value
// https://github.com/jhipster/generator-jhipster/issues/10097
// https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
internal inline fun <reified T> T.classBasedStaticHashCode() = T::class.hashCode()