package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.*
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.JpqlSelectQuery
import kotlin.reflect.KClass

internal class SelectQueryBuilder<T>(
    private val returnType: KClass<*>,
    private val select: Collection<Expression<*>>,
    private val distinct: Boolean,
    private var from: Collection<Path<*>>,
) {
    private var where: Predicate? = null
    private var groupBy: MutableCollection<Expression<*>>? = null
    private var having: Predicate? = null
    private var orderBy: MutableCollection<Sort>? = null

    fun where(predicate: Predicate): SelectQueryBuilder<T> {
        where = predicate

        return this
    }

    fun groupBy(expressions: Collection<Expression<*>>): SelectQueryBuilder<T> {
        this.groupBy = (this.groupBy ?: mutableListOf()).also { it.addAll(expressions) }

        return this
    }

    fun having(predicate: Predicate): SelectQueryBuilder<T> {
        this.having = predicate

        return this
    }

    fun orderBy(sorts: Collection<Sort>): SelectQueryBuilder<T> {
        this.orderBy = (this.orderBy ?: mutableListOf()).also { it.addAll(sorts) }

        return this
    }

    fun build(): SelectQuery<T> {
        return JpqlSelectQuery(
            returnType = returnType,
            select = select,
            distinct = distinct,
            from = from,
            where = where,
            groupBy = groupBy,
            having = having,
            orderBy = orderBy,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SelectQueryBuilder<*>

        if (returnType != other.returnType) return false
        if (select != other.select) return false
        if (distinct != other.distinct) return false
        if (from != other.from) return false
        if (where != other.where) return false
        if (groupBy != other.groupBy) return false
        if (having != other.having) return false
        return orderBy == other.orderBy
    }

    override fun hashCode(): Int {
        var result = returnType.hashCode()
        result = 31 * result + select.hashCode()
        result = 31 * result + distinct.hashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + (where?.hashCode() ?: 0)
        result = 31 * result + (groupBy?.hashCode() ?: 0)
        result = 31 * result + (having?.hashCode() ?: 0)
        result = 31 * result + (orderBy?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "SelectQueryBuilder(" +
            "returnType=$returnType, " +
            "select=$select, " +
            "distinct=$distinct, " +
            "from=$from, " +
            "where=$where, " +
            "groupBy=$groupBy, " +
            "having=$having, " +
            "orderBy=$orderBy)"
    }
}
