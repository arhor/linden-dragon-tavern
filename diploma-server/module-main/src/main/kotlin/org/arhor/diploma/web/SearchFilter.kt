package org.arhor.diploma.web

/**
 * name=not(eq(Max))
 */
enum class SearchFilter(val description: String) {
    NOT("not"),
    EQ("equal"),
    SW("starts with"),
    EW("ends with"),
    ;
}

sealed abstract class Condition(val description: String)

class Eq : Condition("equal")
class Ne : Condition("not equal")