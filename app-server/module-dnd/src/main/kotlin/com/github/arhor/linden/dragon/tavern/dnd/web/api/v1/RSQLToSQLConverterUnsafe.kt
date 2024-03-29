package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1

import cz.jirutka.rsql.parser.ast.AndNode
import cz.jirutka.rsql.parser.ast.ComparisonNode
import cz.jirutka.rsql.parser.ast.ComparisonOperator
import cz.jirutka.rsql.parser.ast.NoArgRSQLVisitorAdapter
import cz.jirutka.rsql.parser.ast.Node
import cz.jirutka.rsql.parser.ast.OrNode
import cz.jirutka.rsql.parser.ast.RSQLOperators.EQUAL
import cz.jirutka.rsql.parser.ast.RSQLOperators.GREATER_THAN
import cz.jirutka.rsql.parser.ast.RSQLOperators.GREATER_THAN_OR_EQUAL
import cz.jirutka.rsql.parser.ast.RSQLOperators.IN
import cz.jirutka.rsql.parser.ast.RSQLOperators.LESS_THAN
import cz.jirutka.rsql.parser.ast.RSQLOperators.LESS_THAN_OR_EQUAL
import cz.jirutka.rsql.parser.ast.RSQLOperators.NOT_EQUAL
import cz.jirutka.rsql.parser.ast.RSQLOperators.NOT_IN
import mu.KLogging
import org.springframework.stereotype.Component

typealias ArgsConverter = java.util.function.Function<Iterable<String>, String>
typealias OperatorMapping = Pair<String, ArgsConverter>

@Component
class RSQLToSQLConverterUnsafe : NoArgRSQLVisitorAdapter<String>() {

    override fun visit(node: AndNode) = processNodes(node.children, SQL_SEPARATOR_AND)

    override fun visit(node: OrNode) = processNodes(node.children, SQL_SEPARATOR_OR)

    override fun visit(node: ComparisonNode): String {
        val (sqlOperator, argsConverter) = RSQL_OPERATOR_MAPPINGS[node.operator]
            ?: throw IllegalArgumentException("Unknown operator: ${node.operator}")

        val field = node.selector
        val value = handleArguments(argsConverter.apply(node.arguments))

        return "$field $sqlOperator $value"
    }

    private fun processNodes(nodes: List<Node>, separator: String): String {
        return nodes.joinToString(separator, prefix = "(", postfix = ")") { it.accept(this) }
    }

    private fun handleArguments(input: String): String {
        val matchResult = TYPE_PATTERN.find(input)

        if (matchResult != null) {
            val (type, value) = matchResult.destructured

            return runCatching { TypeConverter.valueOf(type.uppercase()) }.fold(
                onSuccess = {
                    it.convert(value)
                },
                onFailure = {
                    logger.warn {
                        "Cannot determine corresponding converter for the type: $type, using Default as fallback"
                    }
                    TypeConverter.DEFAULT.convert(value)
                }
            )
        }

        return input
    }

    private enum class TypeConverter {
        STR {
            override fun convert(value: String?) = value?.let { "'$it'" } ?: "NULL"
        },
        NUM {
            override fun convert(value: String?) = value ?: "NULL"
        },
        DEFAULT {
            override fun convert(value: String?) = value ?: "NULL"
        },
        ;

        abstract fun convert(value: String?): String
    }

    companion object : KLogging() {
        // @formatter:off
        private const val SQL_SEPARATOR_AND = " AND "
        private const val SQL_SEPARATOR_OR  = " OR "

        private const val SQL_OPERATOR_EQ = "="
        private const val SQL_OPERATOR_NE = "!="
        private const val SQL_OPERATOR_GT = ">"
        private const val SQL_OPERATOR_GE = ">="
        private const val SQL_OPERATOR_LT = "<"
        private const val SQL_OPERATOR_LE = "<="
        private const val SQL_OPERATOR_IN = "IN"
        private const val SQL_OPERATOR_NIN = "NOT IN"

        private val TYPE_PATTERN = Regex("(^[a-zA-Z]+)\\[([a-zA-Z0-9.,]+)]$")

        private val FIRST_ARG = ArgsConverter { it.first() }
        private val ALL_ARGS_IN_BRACES = ArgsConverter { it.joinToString(",", "(", ")") }

        private val RSQL_OPERATOR_MAPPINGS = buildMap<ComparisonOperator, OperatorMapping> {
            put(EQUAL,                 SQL_OPERATOR_EQ to FIRST_ARG)
            put(NOT_EQUAL,             SQL_OPERATOR_NE to FIRST_ARG)
            put(GREATER_THAN,          SQL_OPERATOR_GT to FIRST_ARG)
            put(GREATER_THAN_OR_EQUAL, SQL_OPERATOR_GE to FIRST_ARG)
            put(LESS_THAN,             SQL_OPERATOR_LT to FIRST_ARG)
            put(LESS_THAN_OR_EQUAL,    SQL_OPERATOR_LE to FIRST_ARG)
            put(IN,                    SQL_OPERATOR_IN to ALL_ARGS_IN_BRACES)
            put(NOT_IN,                SQL_OPERATOR_NIN to ALL_ARGS_IN_BRACES)
        }
        // @formatter:on
    }
}
