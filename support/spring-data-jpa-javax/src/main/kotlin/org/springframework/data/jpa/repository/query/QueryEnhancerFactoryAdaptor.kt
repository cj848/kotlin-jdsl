package org.springframework.data.jpa.repository.query

internal object QueryEnhancerFactoryAdaptor {
    fun forQuery(query: String): QueryEnhancer = QueryEnhancerFactory.forQuery(StringQuery(query, false))
}
