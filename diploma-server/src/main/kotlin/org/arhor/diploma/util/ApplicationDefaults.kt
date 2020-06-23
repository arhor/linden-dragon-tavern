package org.arhor.diploma.util

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
