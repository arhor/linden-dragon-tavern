//package org.arhor.diploma.web
//
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.toList
//import org.arhor.diploma.commons.*
//import org.arhor.diploma.service.Reader
//import org.springframework.hateoas.PagedModel
//import org.springframework.hateoas.RepresentationModel
//import org.springframework.hateoas.server.mvc.linkTo
//import org.springframework.security.core.Authentication
//import java.io.Serializable
//
//internal suspend inline fun <D, K, reified CONTROLLER> Reader<D, K>.createPagedModel(
//    page: Int?,
//    size: Int?,
//    auth: Authentication?,
//    crossinline getPage: suspend CONTROLLER.(Int?, Int?, Authentication?) -> PagedModel<*>,
//    crossinline mapper: (D) -> RepresentationModel<*> = { item: Any -> RepresentationModel.of(item) },
//): PagedModel<*>
//        where D : Identifiable<K>,
//              K : Serializable {
//
//    val safePage = page.minBound(DEFAULT_PAGE)
//    val safeSize = size.maxBound(DEFAULT_SIZE)
//
//    val items = getList(page = safePage, size = safeSize).map { value -> mapper.invoke(value) }
//    val totalSize = getTotalSize()
//
//    val pagedModel = PagedModel.of(
//        items.toList(),
//        PagedModel.PageMetadata(
//            safeSize.toLong(),
//            safePage.toLong(),
//            totalSize
//        )
//    )
//
//    pagedModel.addIf(safePage > 0) {
//        linkTo<CONTROLLER> {
//            getPage(
//                safePage - 1,
//                safeSize,
//                auth
//            )
//        }.withRel("prev")
//    }
//
//    pagedModel.addIf((safePage + 1) < totalSize) {
//        linkTo<CONTROLLER> {
//            getPage(
//                safePage + 1,
//                safeSize,
//                auth
//            )
//        }.withRel("next")
//    }
//
//    return pagedModel
//}
