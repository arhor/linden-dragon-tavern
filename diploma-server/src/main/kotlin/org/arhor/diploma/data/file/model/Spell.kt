package org.arhor.diploma.data.file.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.util.equalTo
import org.arhor.diploma.util.findAll
import java.util.function.Predicate

data class Spell(
    val name: String,
    val level: Byte,
    val school: String
) : Identifiable<String> {

    class Finder(
        name: String? = null,
        level: Byte? = null,
        ritual: Boolean? = null,
        components: Set<Component>? = null,
        concentration: Boolean? = null,
        school: String? = null,
    ): Predicate<Details> {

        private val query: Predicate<Details>
        var isEmpty: Boolean = true

        init {
            var tempQuery: Predicate<Details> = findAll()

            name?.let {
                tempQuery = tempQuery.and(Details::name.equalTo(it))
                isEmpty = false
            }
            level?.let {
                tempQuery = tempQuery.and(Details::level.equalTo(it))
                isEmpty = false
            }
            ritual?.let {
                tempQuery = tempQuery.and(Details::ritual.equalTo(it))
                isEmpty = false
            }
            components?.let {
                tempQuery = tempQuery.and(Details::components.equalTo(it))
                isEmpty = false
            }
            concentration?.let {
                tempQuery = tempQuery.and(Details::concentration.equalTo(it))
                isEmpty = false
            }
            school?.let {
                tempQuery = tempQuery.and(Details::school.equalTo(it))
                isEmpty = false
            }

            query = tempQuery
        }

        override fun test(item: Details): Boolean {
            return query.test(item)
        }
    }

    enum class Component {
        /** Material */
        M,

        /** Verbal */
        V,

        /** Somatic */
        S;
    }

    @JsonInclude(NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Details(
        val name: String,
        val desc: List<String>,
        val higherLevel: List<String>?,
        val range: String,
        val level: Byte,
        val ritual: Boolean,
        val components: Set<Component>,
        val material: String?,
        val duration: String,
        val concentration: Boolean,
        val castingTime: String,
        val school: String,
        val classes: List<String>,
        val subclasses: List<String>,
    ) : Identifiable<String> {

        @JsonIgnore
        override fun getId(): String? = name
    }

    @JsonIgnore
    override fun getId(): String? = name
}