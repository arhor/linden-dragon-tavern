package org.arhor.diploma.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import java.util.function.BiConsumer
import java.util.function.BinaryOperator
import java.util.function.Supplier
import java.util.stream.Collector

object CustomCollectors {

  @JvmStatic
  fun toArrayNode(mapper: ObjectMapper): Collector<String, ArrayNode, ArrayNode> {
    return Collector.of(
        Supplier {
          mapper.createArrayNode()
        },
        BiConsumer { node: ArrayNode, str: String ->
          node.add(str)
        },
        BinaryOperator { left: ArrayNode, right: ArrayNode ->
          left.addAll(right)
        }
    )
  }
}
