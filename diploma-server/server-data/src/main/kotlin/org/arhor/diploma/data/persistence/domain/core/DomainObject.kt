package org.arhor.diploma.data.persistence.domain.core

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.extension.ObjectExtensionLoader
import org.arhor.diploma.commons.Extensible
import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
@EntityListeners(ObjectExtensionLoader::class)
abstract class DomainObject<T : Serializable> : Identifiable<T>, Extensible, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: T? = null

    // ******* Domain object extension infrastructure *******

    abstract val tableName: String

    @Transient
    private val _extState: MutableMap<String, Any?> = HashMap()

    final override val names: Set<String>
        get() = _extState.keys.toSet()

    final override operator fun get(name: String): Any? {
        return _extState[name]
    }

    final override operator fun set(name: String, value: Any?) {
        _extState[name] = value
    }

    final override fun remove(name: String) {
        _extState.remove(name)
    }

    final override fun clear() {
        _extState.clear()
    }

    // ******* `equals`/`hashCode` overrides *******

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DomainObject<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
