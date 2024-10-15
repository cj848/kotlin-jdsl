@file:Suppress("SqlSourceToSinkFlow")

package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.query.QueryEnhancerFactoryAdaptor
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

internal object JpqlEntityManagerUtils {
    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered.query, rendered.params, returnType.java)
    }

    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        returnType: KClass<T>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered.query, rendered.params, returnType.java)
    }

    fun createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered.query, rendered.params)
    }

    fun createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered.query, rendered.params)
    }

    fun <T : Any> createEnhancedQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        sort: Sort,
        context: RenderContext,
    ): EnhancedTypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        val queryEnhancer = QueryEnhancerFactoryAdaptor.forQuery(rendered.query)

        return EnhancedTypedQuery(
            createQuery(entityManager, queryEnhancer.applySorting(sort), rendered.params, returnType.java),
        ) {
            // Lazy
            createQuery(entityManager, queryEnhancer.createCountQueryFor(), rendered.params, Long::class.javaObjectType)
        }
    }

    private fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: String,
        queryParams: Map<String, Any?>,
        returnType: Class<T>,
    ): TypedQuery<T> {
        return entityManager.createQuery(query, returnType).apply {
            setParams(this, queryParams)
        }
    }

    private fun createQuery(
        entityManager: EntityManager,
        query: String,
        queryParams: Map<String, Any?>,
    ): Query {
        return entityManager.createQuery(query).apply {
            setParams(this, queryParams)
        }
    }

    private fun setParams(query: Query, params: Map<String, Any?>) {
        val parameterNameSet = query.parameters.map { it.name }.toHashSet()

        params.forEach { (name, value) ->
            if (parameterNameSet.contains(name)) {
                query.setParameter(name, value)
            } else {
                if (log.isDebugEnabled) {
                    log.debug(
                        "No parameter named '$name' in query " +
                            "with named parameters [${parameterNameSet.joinToString()}], " +
                            "parameter binding skipped",
                    )
                }
            }
        }
    }
}

private val log = LoggerFactory.getLogger(JpqlEntityManagerUtils::class.java)
