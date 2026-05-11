package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.CastStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCast

@PublishedApi
internal data class JpqlCastStep(
    private val expression: Expression<String>,
) : CastStep {
    override fun asInteger(): Expressionable<Int> = JpqlCast(expression, Int::class)

    override fun asLong(): Expressionable<Long> = JpqlCast(expression, Long::class)

    override fun asFloat(): Expressionable<Float> = JpqlCast(expression, Float::class)

    override fun asDouble(): Expressionable<Double> = JpqlCast(expression, Double::class)
}
