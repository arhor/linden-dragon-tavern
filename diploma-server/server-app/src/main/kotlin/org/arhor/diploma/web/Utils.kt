package org.arhor.diploma.web

import org.arhor.diploma.commons.*
import org.arhor.diploma.service.Reader
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.security.core.Authentication
import java.io.Serializable

internal inline fun <D, K, reified CONTROLLER> Reader<D, K>.createPagedModel(
    page: Int?,
    size: Int?,
    auth: Authentication?,
    crossinline getPage: CONTROLLER.(Int?, Int?, Authentication?) -> PagedModel<*>,
    mapper: (D) -> RepresentationModel<*> = { item: Any -> RepresentationModel.of(item) },
): PagedModel<*>
        where D : Identifiable<K>,
              K : Serializable {

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
