package com.linecorp.kotlinjdsl.querymodel.jpql.predicate

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlAnd
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlBetween
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlCustomPredicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqual
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqualAll
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqualAny
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlExists
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlFunctionPredicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlGreaterThan
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlGreaterThanAll
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlGreaterThanAny
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlGreaterThanOrEqualTo
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlGreaterThanOrEqualToAll
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlGreaterThanOrEqualToAny
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIn
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlInSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsEmpty
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNotEmpty
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNotNull
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNull
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThan
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThanAll
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThanAny
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThanOrEqualTo
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThanOrEqualToAll
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThanOrEqualToAny
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLike
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNot
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotBetween
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotEqual
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotEqualAll
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotEqualAny
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotExists
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotIn
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotInSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotLike
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlOr
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlPredicateParentheses

/**
 * Factory class that creates [Predicate].
 */
@SinceJdsl("3.0.0")
object Predicates {
    /**
     * Creates a predicate the inverse of the predicate.
     */
    @SinceJdsl("3.0.0")
    fun not(predicate: Predicate): Predicate = JpqlNot(predicate)

    /**
     * Creates a predicate that combines predicates with `AND`.
     */
    @SinceJdsl("3.0.0")
    fun and(predicates: Iterable<Predicate>): Predicate = JpqlAnd(predicates)

    /**
     * Creates a predicate that combines predicates with `OR`.
     */
    @SinceJdsl("3.0.0")
    fun or(predicates: Iterable<Predicate>): Predicate = JpqlOr(predicates)

    /**
     * Creates a predicate that tests whether the value is null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> isNull(value: Expression<T>): Predicate = JpqlIsNull(value)

    /**
     * Creates a predicate that tests whether the value is not null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> isNotNull(value: Expression<T>): Predicate = JpqlIsNotNull(value.toExpression())

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> equal(
        value: Expression<T>,
        compareValue: Expression<T>,
    ): Predicate =
        if (compareValue is JpqlNull) {
            isNull(value)
        } else {
            JpqlEqual(value, compareValue)
        }

    /**
     * Creates a predicate that tests whether the value is equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> equalAll(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlEqualAll(value, subquery)

    /**
     * Creates a predicate that tests whether the value is equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> equalAny(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlEqualAny(value, subquery)

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> notEqual(
        value: Expression<T>,
        compareValue: Expression<T>,
    ): Predicate =
        if (compareValue is JpqlNull) {
            isNotNull(value)
        } else {
            JpqlNotEqual(value, compareValue)
        }

    /**
     * Creates a predicate that tests whether the value is not equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> notEqualAll(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlNotEqualAll(value, subquery)

    /**
     * Creates a predicate that tests whether the value is not equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> notEqualAny(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlNotEqualAny(value, subquery)

    /**
     * Creates a predicate that tests whether the value is less than the compareValue.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThan(
        value: Expression<T>,
        compareValue: Expression<T>,
    ): Predicate = JpqlLessThan(value, compareValue)

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanAll(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlLessThanAll(value, subquery)

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanAny(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlLessThanAny(value, subquery)

    /**
     * Creates a predicate that tests whether the value is less than or equal to the compareValue.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanOrEqualTo(
        value: Expression<T>,
        compareValue: Expression<T>,
    ): Predicate = JpqlLessThanOrEqualTo(value, compareValue)

    /**
     * Creates a predicate that tests whether the value is less than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanOrEqualToAll(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlLessThanOrEqualToAll(value, subquery)

    /**
     * Creates a predicate that tests whether the value is less than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> lessThanOrEqualToAny(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlLessThanOrEqualToAny(value, subquery)

    /**
     * Creates a predicate that tests whether the value is greater than the compareValue.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThan(
        value: Expression<T>,
        compareValue: Expression<T>,
    ): Predicate = JpqlGreaterThan(value, compareValue)

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanAll(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlGreaterThanAll(value, subquery)

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanAny(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlGreaterThanAny(value, subquery)

    /**
     * Creates a predicate that tests whether the value is greater than or equal to the compareValue.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanOrEqualTo(
        value: Expression<T>,
        compareValue: Expression<T>,
    ): Predicate = JpqlGreaterThanOrEqualTo(value, compareValue)

    /**
     * Creates a predicate that tests whether the value is greater than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanOrEqualToAll(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlGreaterThanOrEqualToAll(value, subquery)

    /**
     * Creates a predicate that tests whether the value is greater than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> greaterThanOrEqualToAny(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlGreaterThanOrEqualToAny(value, subquery)

    /**
     * Creates a predicate that tests whether the value is between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> between(
        value: Expression<T>,
        min: Expression<T>,
        max: Expression<T>,
    ): Predicate = JpqlBetween(value, min, max)

    /**
     * Creates a predicate that tests whether the value is not between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> notBetween(
        value: Expression<T>,
        min: Expression<T>,
        max: Expression<T>,
    ): Predicate = JpqlNotBetween(value, min, max)

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> `in`(
        value: Expression<T>,
        compareValues: Iterable<Expression<T>>,
    ): Predicate = JpqlIn(value, compareValues)

    /**
     * Creates a predicate that tests whether the value is in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> `in`(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlInSubquery(value, subquery)

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> notIn(
        value: Expression<T>,
        compareValues: Iterable<Expression<T>>,
    ): Predicate = JpqlNotIn(value, compareValues)

    /**
     * Creates a predicate that tests whether the value is not in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> notIn(
        value: Expression<T>,
        subquery: Subquery<T>,
    ): Predicate = JpqlNotInSubquery(value, subquery)

    /**
     * Creates a predicate that tests whether the value matches the pattern.
     */
    @SinceJdsl("3.0.0")
    fun like(
        value: Expression<String>,
        pattern: Expression<String>,
        escape: Expression<Char>? = null,
    ): Predicate = JpqlLike(value, pattern, escape)

    /**
     * Creates a predicate that tests whether the value does not match the pattern.
     */
    @SinceJdsl("3.0.0")
    fun notLike(
        value: Expression<String>,
        pattern: Expression<String>,
        escape: Expression<Char>? = null,
    ): Predicate = JpqlNotLike(value, pattern, escape)

    /**
     * Creates a predicate that tests whether the path has no elements.
     */
    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> isEmpty(path: Path<S>): Predicate = JpqlIsEmpty(path)

    /**
     * Creates a predicate that tests whether the path has an element.
     */
    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> isNotEmpty(path: Path<S>): Predicate = JpqlIsNotEmpty(path)

    /**
     * Creates a predicate that tests whether the subquery has a row.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> exists(subquery: Subquery<T>): Predicate = JpqlExists(subquery)

    /**
     * Creates a predicate that tests whether the subquery has no row.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> notExists(subquery: Subquery<T>): Predicate = JpqlNotExists(subquery)

    /**
     * Creates a predicate that represents predefined database functions and user-defined database functions.
     */
    @SinceJdsl("3.0.0")
    fun function(
        name: String,
        args: Iterable<Expression<*>>,
    ): Predicate = JpqlFunctionPredicate(name, args)

    /**
     * Creates a predicate that represents the user-defined predicate.
     *
     * The template for the user-defined predicate can have placeholders.
     * Placeholders in template are replaced with the expression in args, matching with index.
     *
     * ```
     * Placeholder: { ArgumentIndex }
     * ```
     *
     * Examples:
     * ```
     * Predicates.customPredicate("{0} MEMBER OF {1}", Expressions.value(author), Paths.path(Book::authors))
     * ```
     */
    @SinceJdsl("3.3.0")
    fun customPredicate(
        template: String,
        args: Iterable<Expression<*>>,
    ): Predicate = JpqlCustomPredicate(template, args)

    /**
     * Creates a predicate that is enclosed in parentheses.
     */
    @SinceJdsl("3.0.0")
    fun parentheses(predicate: Predicate): Predicate = JpqlPredicateParentheses(predicate)
}
