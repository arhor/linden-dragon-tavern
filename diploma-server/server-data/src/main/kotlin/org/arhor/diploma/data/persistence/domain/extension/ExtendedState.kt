package org.arhor.diploma.data.persistence.domain.extension

class ExtendedState : Extensible {

    lateinit var loader: () -> MutableMap<String, Any?>

    private val _extState: MutableMap<String, Any?> by lazy(loader)

    override val names: Set<String>
        get() = _extState.keys.toSet()

    override operator fun get(name: String): Any? {
        return _extState[name]
    }

    override operator fun set(name: String, value: Any?) {
        _extState[name] = value
    }

    override fun remove(name: String) {
        _extState.remove(name)
    }

    override fun clear() {
        _extState.clear()
    }
}