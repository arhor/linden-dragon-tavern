package org.arhor.diploma.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.stream.Collector;

public final class CustomCollectors {

  private CustomCollectors() {}

  public static Collector<String, ArrayNode, ArrayNode> toArrayNode(final ObjectMapper mapper) {
    return Collector.of(
        mapper::createArrayNode,
        ArrayNode::add,
        ArrayNode::addAll
    );
  }
}
