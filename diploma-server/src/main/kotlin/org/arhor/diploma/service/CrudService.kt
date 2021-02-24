package org.arhor.diploma.service

import org.arhor.diploma.commons.Identifiable
import java.io.Serializable

interface CrudService<D, K> : Creator<D, K>, Reader<D, K>, Updater<D, K>, Deleter<D, K>
        where D : Identifiable<K>,
              K : Serializable