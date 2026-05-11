package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseThenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseWhenStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@PublishedApi
internal data class CaseThenFirstStepDsl(
    private val predicate: Predicate,
) : CaseThenFirstStep {
    override fun <T> then(value: T): CaseWhenStep<T & Any> = CaseDsl(predicate, Expressions.value(value))

    override fun <T : Any> then(value: Expressionable<T>): CaseWhenStep<T> = CaseDsl(predicate, value.toExpression())
}
