package org.arhor.diploma.web

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.service.CrudService
import org.arhor.diploma.util.DEFAULT_PAGE
import org.arhor.diploma.util.DEFAULT_SIZE
import org.arhor.diploma.util.maxBound
import org.arhor.diploma.util.minBound
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.security.core.Authentication
import java.io.Serializable

internal inline fun <E, K, reified CONTROLLER> CrudService<E, K>.createPagedModel(
    page: Int?,
    size: Int?,
    auth: Authentication?,
    crossinline getPage: CONTROLLER.(Int?, Int?, Authentication?) -> PagedModel<*>,
    mapper: (E) -> RepresentationModel<*> = { item: Any -> RepresentationModel.of(item) },
): PagedModel<*>
        where E : Identifiable<K>, K : Serializable {

    val safePage = page.minBound(DEFAULT_PAGE)
    val safeSize = size.maxBound(DEFAULT_SIZE)

    val items = getList(page = safePage, size = safeSize).map(mapper)
    val totalSize = getTotalSize()

    val pagedModel = PagedModel.of(
        items,
        PagedModel.PageMetadata(
            safeSize.toLong(),
            safePage.toLong(),
            totalSize
        )
    )

    pagedModel.addIf(safePage > 0) {
        linkTo<CONTROLLER> {
            getPage(
                safePage - 1,
                safeSize,
                auth
            )
        }.withRel("prev")
    }

    pagedModel.addIf((safePage + 1) < totalSize) {
        linkTo<CONTROLLER> {
            getPage(
                safePage + 1,
                safeSize,
                auth
            )
        }.withRel("next")
    }

    return pagedModel
}
