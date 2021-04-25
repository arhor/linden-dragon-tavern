package org.arhor.diploma.util

object Common {
    const val SERIAL_VERSION = 1L

    // Regex for acceptable logins
    const val LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$"

    const val SYSTEM_ACCOUNT = "system"
    const val DEFAULT_LANGUAGE = "ru"
    const val ANONYMOUS_USER = "anonymoususer"
}

object SpringProfile {
    const val DEVELOPMENT = "dev"
    const val PRODUCTION  = "production"
    const val TEST        = "test"
    const val CLOUD       = "cloud"
    const val HEROKU      = "heroku"
    const val SWAGGER     = "swagger"
    const val HAZELCAST   = "hazelcast"
}

object Cache {

    object Hazelcast {
        const val TIME_TO_LIVE_SECONDS = 3600
        const val BACKUP_COUNT = 1

        object ManagementCenter {
            const val ENABLED = false
            const val URL = ""
            const val UPDATE_INTERVAL = 3
        }
    }
}

object JWT {
    const val EXPIRE_SECONDS = 600
}