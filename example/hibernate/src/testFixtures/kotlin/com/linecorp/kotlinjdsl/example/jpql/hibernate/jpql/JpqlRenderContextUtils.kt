package com.linecorp.kotlinjdsl.example.jpql.hibernate.jpql

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext

object JpqlRenderContextUtils {
    fun getJpqlRenderContext(): JpqlRenderContext = jpqlRenderContext
}

private val jpqlRenderContext = JpqlRenderContext()
