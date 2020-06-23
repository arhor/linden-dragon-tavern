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
  const val TEST = "test"
  const val PRODUCTION = "prod"
  const val CLOUD = "cloud"
  const val HEROKU = "heroku"
  const val AWS_ECS = "aws-ecs"
  const val AZURE = "azure"
  const val SWAGGER = "swagger"
  const val NO_LIQUIBASE = "no-liquibase"
  const val K8S = "k8s"
}