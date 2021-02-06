package org.arhor.diploma.data.persistence.domain.extension

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ExtraDataHolder {

    private val lock = ReentrantLock()

    private val data: MutableMap<String, Any?> = HashMap()

    lateinit var dataProvider: () -> Map<String, Any?>

    lateinit var dataConsumer: (Map<String, Any?>) -> Unit

    val names: Set<String>
        get() = withDataLoaded {
            data.keys.toSet()
        }

    var state: State = State.NEW
        private set

    operator fun get(name: String): Any? = withDataLoaded {
        lock.withLock { data[name] }
    }

    operator fun set(name: String, value: Any?) = withDataLoaded {
        mutation {
            data[name] = value
        }
    }

    fun remove(name: String) = withDataLoaded {
        mutation {
            data.remove(name)
        }
    }

    fun clear() = withDataLoaded {
        mutation {
            data.clear()
        }
    }

    private inline fun mutation(state: State = State.DIRTY, action: () -> Unit) {
        lock.withLock {
            action()
            this.state = state
        }
    }

    private fun <T> withDataLoaded(action: () -> T): T {
        if (!::dataProvider.isInitialized) {
            throw IllegalStateException("initializer function must be set first")
        }

        return when (state) {
            State.NEW -> lock.withLock {
                // pre-load required
                val dbData = dataProvider.invoke()

                mutation(State.PERSISTENT) {
                    data.clear()
                    data.putAll(dbData)
                }

                action()
            }
            State.PERSISTENT, State.DIRTY -> {
                // pre-load performed
                action()
            }
        }
    }

    enum class State {
        /**
         * [NEW] state indicates that synchronization with the database was not performed yet.
         */
        NEW,

        /**
         * [PERSISTENT] state indicates that synchronization with the database was performed, however,
         * no mutation occurred after that.
         */
        PERSISTENT,

        /**
         * [DIRTY] state indicates that synchronization with the database was performed and
         * some mutation occurred after that.
         */
        DIRTY
        ;
    }
}