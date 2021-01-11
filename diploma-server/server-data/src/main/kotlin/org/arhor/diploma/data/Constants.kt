package org.arhor.diploma.data

// Because of Hibernate entities with generated ID should have fixed hashCode value
const val STATIC_HASH_CODE = 31

object Cache {

    object Names {
        const val ACCOUNT             = "dp_account_cache"
        const val ACCOUNT_BY_ID       = "dp_account_cache_by_id"
        const val ACCOUNT_BY_USERNAME = "dp_account_cache_by_username"
    }
}