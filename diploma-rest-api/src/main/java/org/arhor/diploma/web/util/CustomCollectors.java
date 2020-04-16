package org.arhor.diploma.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.stream.Collector;

public class CustomCollectors {

  public static Collector<String, ArrayNode, ArrayNode> toArrayNode(ObjectMapper objectMapper) {
    return Collector.of(
        objectMapper::createArrayNode,
        (arrayNode, value) -> arrayNode.add(value),
        (arrayNode, other) -> arrayNode.addAll(other)
    );
  }
}
