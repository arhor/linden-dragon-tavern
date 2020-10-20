package org.arhor.diploma.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import java.util.stream.Collector

internal fun toArrayNode(mapper: ObjectMapper): Collector<String, ArrayNode, ArrayNode> {
    return Collector.of(
        fun(): ArrayNode {
            return mapper.createArrayNode()
        },
        fun(node: ArrayNode, str: String) {
            node.add(str)
        },
        fun(left: ArrayNode, right: ArrayNode): ArrayNode {
            return left.addAll(right)
        }
    )
}
