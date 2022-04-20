package com.github.arhor.linden.dragon.tavern.config.resolver

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.net.URI
import javax.servlet.http.HttpServletRequest

class RequestURIMethodArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return URI::class.java == parameter.parameterType;
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any {
        return webRequest.getNativeRequest(NATIVE_REQUEST_TYPE)?.let { URI.create(it.requestURI) }
            ?: throw IllegalStateException(
                "Unsupported request type, required: ${NATIVE_REQUEST_TYPE.simpleName}, provided: null"
            )
    }

    companion object {
        private val NATIVE_REQUEST_TYPE = HttpServletRequest::class.java
    }
}
