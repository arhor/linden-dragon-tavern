package org.arhor.diploma.util

object Common {
  const val SERIAL_VERSION = 1L

  // Because of Hibernate entities with generated ID should have fixed hashCode value
  const val STATIC_HASH_CODE = 31

  // Regex for acceptable logins
  const val LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$"

  const val SYSTEM_ACCOUNT = "system"
  const val DEFAULT_LANGUAGE = "ru"
  const val ANONYMOUS_USER = "anonymoususer"
}

object SpringProfile {
  const val DEVELOPMENT = "dev"
  const val TEST = "test"
  const val PRODUCTION = "prod"
  const val CLOUD = "cloud"
  const val HEROKU = "heroku"
  const val AWS_ECS = "aws-ecs"
  const val AZURE = "azure"
  const val SWAGGER = "swagger"
  const val NO_LIQUIBASE = "no-liquibase"
  const val K8S = "k8s"
  const val HAZELCAST = "hazelcast"
}

object Cache {

  object Names {
    const val ACCOUNT = "dp_cache_account"
    const val ACCOUNT_BY_ID = "dp_account_cache_by_id"
    const val ACCOUNT_BY_USERNAME = "dp_account_cache_by_username"
  }

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