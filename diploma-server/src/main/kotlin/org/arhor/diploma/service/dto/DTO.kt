package org.arhor.diploma.service.dto

import org.arhor.diploma.core.Identifiable
import java.io.Serializable

interface DTO<K : Serializable> : Identifiable<K>