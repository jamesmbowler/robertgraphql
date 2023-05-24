package com.example.paymentsv2.datafetchers

import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.internal.DgsWebMvcRequestData
import com.netflix.graphql.dgs.internal.method.ArgumentResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.core.MethodParameter
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver
import org.springframework.stereotype.Component
import org.springframework.web.context.request.NativeWebRequest

@Component
class DgsAuthenticationPrincipalArgumentResolver : ArgumentResolver {
    private val delegate = AuthenticationPrincipalArgumentResolver()

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return delegate.supportsParameter(parameter)
    }

    override fun resolveArgument(parameter: MethodParameter, dfe: DataFetchingEnvironment): Any? {
        val request = (DgsDataFetchingEnvironment(dfe).getDgsContext().requestData as DgsWebMvcRequestData).webRequest as NativeWebRequest
        return delegate.resolveArgument(parameter, null, request, null)
    }
}