package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.dsl.jpql.delete.impl.DeleteQueryDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseThenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CastStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CastStepToString
import com.linecorp.kotlinjdsl.dsl.jpql.expression.TrimFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseThenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.CaseValueWhenFirstStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.JpqlCastStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.JpqlCastStepToString
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.TrimBothFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.TrimFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.TrimLeadingFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.expression.impl.TrimTrailingFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.AssociationJoinOnStep
import com.linecorp.kotlinjdsl.dsl.jpql.join.JoinOnStep
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.AssociationFetchJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.AssociationJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.FetchJoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.join.impl.JoinDsl
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryOrderByStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SelectQueryFromStepDsl
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SetOperator
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SetOperatorQueryDsl
import com.linecorp.kotlinjdsl.dsl.jpql.sort.SortNullsStep
import com.linecorp.kotlinjdsl.dsl.jpql.sort.impl.SortDsl
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.impl.UpdateQuerySetStepFirstDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Pathable
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.internal.Exact
import kotlin.internal.LowPriorityInOverloadResolution
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

/**
 * Builds new JPQL query using newly created JpqlDsl.
 */
@SinceJdsl("3.0.0")
inline fun <Q : JpqlQuery<Q>> jpql(init: Jpql.() -> JpqlQueryable<Q>): Q = Jpql().init().toQuery()

/**
 * Builds new JPQL query using provided JpqlDsl.
 */
@SinceJdsl("3.0.0")
inline fun <DSL : JpqlDsl, Q : JpqlQuery<Q>> jpql(
    dsl: JpqlDsl.Constructor<DSL>,
    init: DSL.() -> JpqlQueryable<Q>,
): Q = dsl.newInstance().init().toQuery()

/**
 * Builds new JPQL query using provided JpqlDsl instance.
 */
@SinceJdsl("3.4.0")
inline fun <DSL : JpqlDsl, Q : JpqlQuery<Q>> jpql(
    jpql: DSL,
    init: DSL.() -> JpqlQueryable<Q>,
): Q = jpql.init().toQuery()

/**
 * Default implementation of DSL for building a JPQL query.
 */
@SinceJdsl("3.0.0")
open class Jpql : JpqlDsl {
    companion object Constructor : JpqlDsl.Constructor<Jpql> {
        override fun newInstance(): Jpql = Jpql()
    }

    /**
     * Creates a parameter expression with a generated name and the value.
     */
    @SinceJdsl("3.0.0")
    fun <T> value(value: @Exact T): Expression<T & Any> = Expressions.value(value)

    /**
     * Creates a parameter expression with a generated name and null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullValue(): Expression<T> = Expressions.nullValue()

    /**
     * Creates a literal expression with the int.
     */
    @SinceJdsl("3.0.0")
    fun intLiteral(int: Int): Expression<Int> = Expressions.intLiteral(int)

    /**
     * Creates a literal expression with the long.
     */
    @SinceJdsl("3.0.0")
    fun longLiteral(long: Long): Expression<Long> = Expressions.longLiteral(long)

    /**
     * Creates a literal expression with the float.
     */
    @SinceJdsl("3.0.0")
    fun floatLiteral(float: Float): Expression<Float> = Expressions.floatLiteral(float)

    /**
     * Creates a literal expression with the double.
     */
    @SinceJdsl("3.0.0")
    fun doubleLiteral(double: Double): Expression<Double> = Expressions.doubleLiteral(double)

    /**
     * Creates a literal expression with the boolean.
     */
    @SinceJdsl("3.0.0")
    fun booleanLiteral(boolean: Boolean): Expression<Boolean> = Expressions.booleanLiteral(boolean)

    /**
     * Creates a literal expression with the char.
     * If the char is '(single quote), it is rendered as ''(two single quotes).
     */
    @SinceJdsl("3.0.0")
    fun charLiteral(char: Char): Expression<Char> = Expressions.charLiteral(char)

    /**
     * Creates a literal expression with the string.
     * If the string contains '(single quote), it is rendered as ''(two single quotes).
     * For example: literal''s.
     */
    @SinceJdsl("3.0.0")
    fun stringLiteral(string: String): Expression<String> = Expressions.stringLiteral(string)

    /**
     * Creates a literal expression with the enum.
     */
    @SinceJdsl("3.0.0")
    fun <T : Enum<T>> enumLiteral(enum: T): Expression<T> = Expressions.enumLiteral(enum)

    /**
     * Creates a literal expression with null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullLiteral(): Expression<T> = Expressions.nullLiteral()

    /**
     * Creates a parameter expression with the name.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> param(name: String): Expression<T> = Expressions.param(name)

    /**
     * Creates a parameter expression with the name and value.
     * The value can be overridden in rendering.
     */
    @SinceJdsl("3.0.0")
    fun <T> param(
        name: String,
        value: @Exact T,
    ): Expression<T & Any> = Expressions.param(name, value)

    /**
     * Creates an entity expression with the type and alias.
     * The entity is identified and referenced by its alias.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> entity(
        type: KClass<T>,
        alias: String = type.simpleName!!,
    ): Entity<T> = Entities.entity(type, alias)

    /**
     * Creates a path expression with the property.
     * The path starts from the entity which is the owner of the property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> path(property: KProperty1<T, @Exact V>): Path<V & Any> = Paths.path(property)

    /**
     * Creates a path expression with the property.
     * The path starts from the entity which is the owner of the property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> path(getter: KFunction1<T, @Exact V>): Path<V & Any> = Paths.path(getter)

    /**
     * Creates a path expression with the entity and property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> Entityable<T>.path(property: KProperty1<in T, @Exact V>): Path<V & Any> =
        Paths.path(this.toEntity(), property)

    /**
     * Creates a path expression with the entity and property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> Entityable<T>.path(getter: KFunction1<T, @Exact V>): Path<V & Any> =
        Paths.path(this.toEntity(), getter)

    /**
     * Creates a path expression with the path and property.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> Pathable<T>.path(property: KProperty1<in T, @Exact V>): Path<V & Any> =
        Paths.path(this.toPath(), property)

    /**
     * Creates a path expression with the path and property.
     */
    @SinceJdsl("3.1.0")
    fun <T : Any, V> Pathable<T>.path(getter: KFunction1<T, @Exact V>): Path<V & Any> =
        Paths.path(this.toPath(), getter)

    /**
     * Creates a path expression with the entity and property.
     */
    @SinceJdsl("3.0.0")
    operator fun <T : Any, V> Entityable<T>.invoke(property: KProperty1<in T, @Exact V>): Path<V & Any> =
        Paths.path(this.toEntity(), property)

    /**
     * Creates a path expression with the entity and property.
     */
    @SinceJdsl("3.1.0")
    operator fun <T : Any, V> Entityable<T>.invoke(getter: KFunction1<T, @Exact V>): Path<V & Any> =
        Paths.path(this.toEntity(), getter)

    /**
     * Creates a path expression with the path and property.
     */
    @SinceJdsl("3.0.0")
    operator fun <T : Any, V> Pathable<T>.invoke(property: KProperty1<in T, @Exact V>): Path<V & Any> =
        Paths.path(this.toPath(), property)

    /**
     * Creates a path expression with the path and property.
     */
    @SinceJdsl("3.1.0")
    operator fun <T : Any, V> Pathable<T>.invoke(getter: KFunction1<T, @Exact V>): Path<V & Any> =
        Paths.path(this.toPath(), getter)

    /**
     * Creates an aliased expression with the alias expression.
     * The aliased expression can be referenced by the alias expression.
     *
     * @see expression for creating an alias expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`as`(alias: Expression<T>): Expression<T> =
        Expressions.alias(this.toExpression(), alias)

    /**
     * Creates an aliased expression with the alias expression.
     * The aliased expression can be referenced by the alias expression.
     *
     * @see expression for creating an alias expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.alias(alias: Expression<T>): Expression<T> =
        Expressions.alias(this.toExpression(), alias)

    /**
     * Creates an expression to reference.
     * The expression can be used for aliasing and referencing.
     *
     * @see as for aliasing an expression.
     * @see alias for aliasing an expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> expression(
        type: KClass<T>,
        alias: String,
    ): Expression<T> = Expressions.expression(type, alias)

    /**
     * Creates an expression to reference.
     * The expression can be used for aliasing and referencing.
     *
     * @see as for aliasing an expression.
     * @see alias for aliasing an expression.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> expression(alias: String): Expression<T> = Expressions.expression(T::class, alias)

    /**
     * Creates an entity with downcasting.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> Entityable<T>.treat(type: KClass<S>): Entity<S> = Entities.treat(this.toEntity(), type)

    /**
     * Creates a path with downcasting.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> Pathable<T>.treat(type: KClass<S>): Path<S> = Paths.treat(this.toPath(), type)

    /**
     * Creates an expression that represents the plus of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) + (value2)```.
     */
    @JvmName("plusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> plus(
        value1: Expressionable<@Exact T>,
        value2: S,
    ): Expression<T> =
        Expressions.plus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )

    /**
     * Creates an expression that represents the plus of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) + (value2)```.
     */
    @JvmName("plusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> plus(
        value1: Expressionable<@Exact T>,
        value2: Expressionable<S>,
    ): Expression<T> =
        Expressions.plus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )

    /**
     * Creates an expression that represents the plus of values.
     *
     * This is the same as ```value1 + value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.plus(value: S): Expression<T> =
        Expressions.plus(this.toExpression(), Expressions.value(value))

    /**
     * Creates an expression that represents the plus of values.
     *
     * This is the same as ```value1 + value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.plus(value: Expressionable<S>): Expression<T> =
        Expressions.plus(this.toExpression(), value.toExpression())

    /**
     * Creates an expression that represents the minus of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) - (value2)```.
     */
    @JvmName("minusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> minus(
        value1: Expressionable<@Exact T>,
        value2: S,
    ): Expression<T> =
        Expressions.minus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )

    /**
     * Creates an expression that represents the minus of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) - (value2)```.
     */
    @JvmName("minusWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> minus(
        value1: Expressionable<@Exact T>,
        value2: Expressionable<S>,
    ): Expression<T> =
        Expressions.minus(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )

    /**
     * Creates an expression that represents the minus of values.
     *
     * This is the same as ```value1 - value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.minus(value: S): Expression<T> =
        Expressions.minus(this.toExpression(), Expressions.value(value))

    /**
     * Creates an expression that represents the minus of values.
     *
     * This is the same as ```value1 - value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.minus(value: Expressionable<S>): Expression<T> =
        Expressions.minus(this.toExpression(), value.toExpression())

    /**
     * Creates an expression that represents the times of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) * (value2)```.
     */
    @JvmName("timesWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> times(
        value1: Expressionable<@Exact T>,
        value2: S,
    ): Expression<T> =
        Expressions.times(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )

    /**
     * Creates an expression that represents the times of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) * (value2)```.
     */
    @JvmName("timesWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> times(
        value1: Expressionable<@Exact T>,
        value2: Expressionable<S>,
    ): Expression<T> =
        Expressions.times(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )

    /**
     * Creates an expression that represents the times of values.
     *
     * This is the same as ```value1 * value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.times(value: S): Expression<T> =
        Expressions.times(this.toExpression(), Expressions.value(value))

    /**
     * Creates an expression that represents the times of values.
     *
     * This is the same as ```value1 * value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.times(value: Expressionable<S>): Expression<T> =
        Expressions.times(this.toExpression(), value.toExpression())

    /**
     * Creates an expression that represents the divide of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) / (value2)```.
     */
    @JvmName("divWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> div(
        value1: Expressionable<@Exact T>,
        value2: S,
    ): Expression<T> =
        Expressions.div(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(Expressions.value(value2)),
        )

    /**
     * Creates an expression that represents the divide of values.
     * The values are each enclosed in parentheses.
     *
     * This is the same as ```(value1) / (value2)```.
     */
    @JvmName("divWithParentheses")
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> div(
        value1: Expressionable<@Exact T>,
        value2: Expressionable<S>,
    ): Expression<T> =
        Expressions.div(
            Expressions.parentheses(value1.toExpression()),
            Expressions.parentheses(value2.toExpression()),
        )

    /**
     * Creates an expression that represents the divide of values.
     *
     * This is the same as ```value1 / value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T?> Expressionable<@Exact T>.div(value: S): Expression<T> =
        Expressions.div(this.toExpression(), Expressions.value(value))

    /**
     * Creates an expression that represents the divide of values.
     *
     * This is the same as ```value1 / value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> Expressionable<@Exact T>.div(value: Expressionable<S>): Expression<T> =
        Expressions.div(this.toExpression(), value.toExpression())

    /**
     * Creates an expression that represents the absolute value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> abs(value: KProperty1<T, @Exact V>): Expression<V> = Expressions.abs(Paths.path(value))

    /**
     * Creates an expression that represents the absolute value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> abs(value: Expressionable<T>): Expression<T> = Expressions.abs(value.toExpression())

    /**
     * Creates an expression that is enclosed in ceiling.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> ceiling(value: KProperty1<T, @Exact V>): Expression<V> =
        Expressions.ceiling(Paths.path(value))

    /**
     * Creates an expression that is enclosed in ceiling.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> ceiling(value: Expressionable<T>): Expression<T> = Expressions.ceiling(value.toExpression())

    /**
     * Creates an expression that represents the exponential value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> exp(value: KProperty1<T, @Exact V>): Expression<Double> =
        Expressions.exp(Paths.path(value))

    /**
     * Creates an expression that represents the exponential value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> exp(value: Expressionable<T>): Expression<Double> = Expressions.exp(value.toExpression())

    /**
     * Creates an expression that is enclosed in floor.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> floor(value: KProperty1<T, @Exact V>): Expression<V> =
        Expressions.floor(Paths.path(value))

    /**
     * Creates an expression that is enclosed in floor.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> floor(value: Expressionable<T>): Expression<T> = Expressions.floor(value.toExpression())

    /**
     * Creates an expression that represents the index of the entity in an ordered list.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any> index(entity: KClass<T>): Expression<Int> = Expressions.index(Entities.entity(entity))

    /**
     * Creates an expression that represents the index of the entity in an ordered list.
     */
    @SinceJdsl("3.4.0")
    fun index(entity: Entityable<*>): Expression<Int> = Expressions.index(entity.toEntity())

    /**
     * Creates an expression that represents the id of the entity.
     */
    @SinceJdsl("3.6.0")
    fun <ID : Any> id(entity: KClass<*>): Expression<ID> = Expressions.id(Entities.entity(entity))

    /**
     * Creates an expression that represents the id of the entity.
     */
    @SinceJdsl("3.6.0")
    fun <ID : Any> id(entity: Expressionable<*>): Expression<ID> = Expressions.id(entity)

    /**
     * Creates an expression that represents the version of the entity.
     */
    @SinceJdsl("3.6.0")
    fun <VERSION : Any> version(entity: KClass<*>): Expression<VERSION> = Expressions.version(Entities.entity(entity))

    /**
     * Creates an expression that represents the version of the entity.
     */
    @SinceJdsl("3.6.0")
    fun <VERSION : Any> version(expression: Expressionable<*>): Expression<VERSION> = Expressions.version(expression)

    /**
     * Creates an expression that represents the natural logarithm of value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> ln(value: KProperty1<T, @Exact V>): Expression<Double> = Expressions.ln(Paths.path(value))

    /**
     * Creates an expression that represents the natural logarithm of value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> ln(value: Expressionable<T>): Expression<Double> = Expressions.ln(value.toExpression())

    /**
     * Creates an expression that represents the mod of values.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any> mod(
        value1: KProperty1<T, Int>,
        value2: Int,
    ): Expression<Int> = Expressions.mod(Paths.path(value1), Expressions.value(value2))

    /**
     * Creates an expression that represents the mod of values.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any> mod(
        value1: KProperty1<T, Int>,
        value2: Expression<Int>,
    ): Expression<Int> = Expressions.mod(Paths.path(value1), value2.toExpression())

    /**
     * Creates an expression that represents the mod of values.
     */
    @SinceJdsl("3.4.0")
    fun mod(
        value1: Expressionable<Int>,
        value2: Int,
    ): Expression<Int> = Expressions.mod(value1.toExpression(), Expressions.value(value2))

    /**
     * Creates an expression that represents the mod of values.
     */
    @SinceJdsl("3.4.0")
    fun mod(
        value1: Expressionable<Int>,
        value2: Expressionable<Int>,
    ): Expression<Int> = Expressions.mod(value1.toExpression(), value2.toExpression())

    /**
     * Create an expression that represents the power of [base] and [exponent].
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> power(
        base: KProperty1<T, @Exact V>,
        exponent: Number,
    ): Expression<Double> = Expressions.power(Paths.path(base), Expressions.value(exponent))

    /**
     * Create an expression that represents the power of [base] and [exponent].
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> power(
        base: KProperty1<T, @Exact V>,
        exponent: Expressionable<V>,
    ): Expression<Double> = Expressions.power(Paths.path(base), exponent.toExpression())

    /**
     * Create an expression that represents the power of [base] and [exponent].
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> power(
        base: Expressionable<T>,
        exponent: T,
    ): Expression<Double> = Expressions.power(base.toExpression(), Expressions.value(exponent))

    /**
     * Create an expression that represents the power of [base] and [exponent].
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> power(
        base: Expressionable<T>,
        exponent: Expressionable<T>,
    ): Expression<Double> = Expressions.power(base.toExpression(), exponent.toExpression())

    /**
     * Creates an expression that represents the sign of value.
     *
     * - If value is positive, it returns 1.
     * - If value is negative, it returns -1.
     * - If value is zero, it returns 0.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> sign(value: KProperty1<T, @Exact V>): Expression<Int> =
        Expressions.sign(Paths.path(value))

    /**
     * Creates an expression that represents the sign of value.
     *
     * - If value is positive, it returns 1.
     * - If value is negative, it returns -1.
     * - If value is zero, it returns 0.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> sign(value: Expressionable<T>): Expression<Int> = Expressions.sign(value.toExpression())

    /**
     * Creates an expression that represents the square root of value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> sqrt(value: KProperty1<T, @Exact V>): Expression<Double> =
        Expressions.sqrt(Paths.path(value))

    /**
     * Creates an expression that represents the square root of value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> sqrt(value: Expressionable<T>): Expression<Double> = Expressions.sqrt(value.toExpression())

    /**
     * Creates an expression that represents the rounding of the value to a specified scale.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> round(
        value: KProperty1<T, @Exact V>,
        scale: Int,
    ): Expression<V> = Expressions.round(Paths.path(value), Expressions.value(scale))

    /**
     * Creates an expression that represents the rounding of the value to a specified scale.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V : Number> round(
        value: KProperty1<T, @Exact V>,
        scale: Expression<Int>,
    ): Expression<V> = Expressions.round(Paths.path(value), scale.toExpression())

    /**
     * Creates an expression that represents the rounding of the value to a specified scale.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> round(
        value: Expressionable<T>,
        scale: Int,
    ): Expression<T> = Expressions.round(value.toExpression(), Expressions.value(scale))

    /**
     * Creates an expression that represents the rounding of the value to a specified scale.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> round(
        value: Expressionable<T>,
        scale: Expressionable<Int>,
    ): Expression<T> = Expressions.round(value.toExpression(), scale.toExpression())

    /**
     * Creates an expression that the number of elements of the collection.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, V, S : Collection<V>> size(path: KProperty1<T, @Exact S>): Expression<Int> =
        Expressions.size(Paths.path(path))

    /**
     * Creates an expression that the number of elements of the collection.
     */
    @SinceJdsl("3.4.0")
    fun <T, S : Collection<T>> size(path: Pathable<S>): Expression<Int> = Expressions.size(path.toPath())

    /**
     * Creates an expression that represents the current date.
     *
     * This is the same as ```CURRENT_DATE```.
     */
    @SinceJdsl("3.4.0")
    fun currentDate(): Expression<Date> = Expressions.currentDate()

    /**
     * Creates an expression that represents the current time.
     *
     * This is the same as ```CURRENT_TIME```.
     */
    @SinceJdsl("3.4.0")
    fun currentTime(): Expression<Time> = Expressions.currentTime()

    /**
     * Creates an expression that represents the current timestamp.
     *
     * This is the same as ```CURRENT_TIMESTAMP```.
     */
    @SinceJdsl("3.4.0")
    fun currentTimestamp(): Expression<Timestamp> = Expressions.currentTimestamp()

    /**
     * Creates an expression that represents the local date.
     *
     * This is the same as ```LOCAL DATE```.
     */
    @SinceJdsl("3.4.0")
    fun localDate(): Expression<LocalDate> = Expressions.localDate()

    /**
     * Creates an expression that represents the local time.
     *
     * This is the same as ```LOCAL TIME```.
     */
    @SinceJdsl("3.4.0")
    fun localTime(): Expression<LocalTime> = Expressions.localTime()

    /**
     * Creates an expression that represents the local datetime.
     *
     * This is the same as ```LOCAL DATETIME```.
     */
    @SinceJdsl("3.4.0")
    fun localDateTime(): Expression<LocalDateTime> = Expressions.localDateTime()

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> count(expr: KProperty1<T, @Exact V>): Expression<Long> =
        Expressions.count(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> count(expr: Expressionable<T>): Expression<Long> =
        Expressions.count(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V> countDistinct(expr: KProperty1<T, @Exact V>): Expression<Long> =
        Expressions.count(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> countDistinct(expr: Expressionable<T>): Expression<Long> =
        Expressions.count(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> max(expr: KProperty1<T, @Exact V>): Expression<V & Any> =
        Expressions.max(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> max(expr: Expressionable<@Exact T>): Expression<T> =
        Expressions.max(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> maxDistinct(expr: KProperty1<T, @Exact V>): Expression<V & Any> =
        Expressions.max(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> maxDistinct(expr: Expressionable<@Exact T>): Expression<T> =
        Expressions.max(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> min(expr: KProperty1<T, @Exact V>): Expression<V & Any> =
        Expressions.min(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> min(expr: Expressionable<@Exact T>): Expression<T> =
        Expressions.min(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Comparable<*>?> minDistinct(expr: KProperty1<T, @Exact V>): Expression<V & Any> =
        Expressions.min(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> minDistinct(expr: Expressionable<@Exact T>): Expression<T> =
        Expressions.min(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Number?> avg(expr: KProperty1<T, @Exact V>): Expression<Double> =
        Expressions.avg(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number> avg(expr: Expressionable<@Exact T>): Expression<Double> =
        Expressions.avg(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Number?> avgDistinct(expr: KProperty1<T, @Exact V>): Expression<Double> =
        Expressions.avg(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number> avgDistinct(expr: Expressionable<@Exact T>): Expression<Double> =
        Expressions.avg(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumInt")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Int?>): Expression<Long> = Expressions.sum(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumLong")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Long?>): Expression<Long> =
        Expressions.sum(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumFloat")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Float?>): Expression<Double> =
        Expressions.sum(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDouble")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, Double?>): Expression<Double> =
        Expressions.sum(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigInteger")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, BigInteger?>): Expression<BigInteger> =
        Expressions.sum(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigDecimal")
    @SinceJdsl("3.0.0")
    fun <T : Any> sum(expr: KProperty1<T, BigDecimal?>): Expression<BigDecimal> =
        Expressions.sum(distinct = false, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumInt")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Int>): Expression<Long> = Expressions.sum(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumLong")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Long>): Expression<Long> = Expressions.sum(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumFloat")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Float>): Expression<Double> = Expressions.sum(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDouble")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<Double>): Expression<Double> = Expressions.sum(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigInteger")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigInteger>): Expression<BigInteger> =
        Expressions.sum(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumBigDecimal")
    @SinceJdsl("3.0.0")
    fun sum(expr: Expressionable<BigDecimal>): Expression<BigDecimal> =
        Expressions.sum(distinct = false, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctInt")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Int?>): Expression<Long> =
        Expressions.sum(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctLong")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Long?>): Expression<Long> =
        Expressions.sum(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctFloat")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Float?>): Expression<Double> =
        Expressions.sum(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctDouble")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, Double?>): Expression<Double> =
        Expressions.sum(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigInteger")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, BigInteger?>): Expression<BigInteger> =
        Expressions.sum(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigDecimal")
    @SinceJdsl("3.0.0")
    fun <T : Any> sumDistinct(expr: KProperty1<T, BigDecimal?>): Expression<BigDecimal> =
        Expressions.sum(distinct = true, Paths.path(expr))

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctInt")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Int>): Expression<Long> = Expressions.sum(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctLong")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Long>): Expression<Long> =
        Expressions.sum(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctFloat")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Float>): Expression<Double> =
        Expressions.sum(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctDouble")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<Double>): Expression<Double> =
        Expressions.sum(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigInteger")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<BigInteger>): Expression<BigInteger> =
        Expressions.sum(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sumDistinctBigDecimal")
    @SinceJdsl("3.0.0")
    fun sumDistinct(expr: Expressionable<BigDecimal>): Expression<BigDecimal> =
        Expressions.sum(distinct = true, expr.toExpression())

    /**
     * Creates an expression that represents the DTO projection.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> new(
        type: KClass<T>,
        vararg args: Any,
    ): Expression<T> = Expressions.new(type, args.map { valueOrExpression(it) })

    /**
     * Creates an expression that represents the DTO projection.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> new(
        type: KClass<T>,
        vararg args: Expressionable<*>,
    ): Expression<T> = Expressions.new(type, args.map { it.toExpression() })

    /**
     * Creates an expression that represents the result for the first predicate that is true.
     * If no predicate is true, the result after `ELSE` is returned, or null if there is no `ELSE`.
     */
    @SinceJdsl("3.0.0")
    fun caseWhen(predicate: Predicatable): CaseThenFirstStep = CaseThenFirstStepDsl(predicate.toPredicate())

    /**
     * Creates an expression that represents the result for the first value = compareValue comparison that is true.
     * If no comparison is true, the result after `ELSE` is returned, or null if there is no `ELSE` part.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> caseValue(value: Pathable<T>): CaseValueWhenFirstStep<T> = CaseValueWhenFirstStepDsl(value.toPath())

    /**
     * Creates an expression that represents the first non-null value in the values,
     * or null if there are no non-null value.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> coalesce(
        value: Expressionable<@Exact T>,
        alternate: S,
        vararg others: S,
    ): Expression<T> =
        Expressions.coalesce(
            value = value.toExpression(),
            alternate = Expressions.value(alternate),
            others = others.map { Expressions.value(it) },
        )

    /**
     * Creates an expression that represents the first non-null value in the values,
     * or null if there are no non-null value.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> coalesce(
        value: Expressionable<@Exact T>,
        alternate: Expressionable<S>,
        vararg others: Expressionable<S>,
    ): Expression<T> =
        Expressions.coalesce(
            value = value.toExpression(),
            alternate = alternate.toExpression(),
            others = others.map { it.toExpression() },
        )

    /**
     * Creates an expression that represents null if left = right is true, otherwise represents left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> nullIf(
        value: Expressionable<@Exact T>,
        compareValue: S,
    ): Expression<T> = Expressions.nullIf(value.toExpression(), Expressions.value(compareValue))

    /**
     * Creates an expression that represents null if left = right is true, otherwise represents left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullIf(
        value: Expressionable<@Exact T>,
        compareValue: Expressionable<T>,
    ): Expression<T> = Expressions.nullIf(value.toExpression(), compareValue.toExpression())

    /**
     * Creates an expression that represents the type of the entity.
     *
     * This is the same as ```TYPE(entity)``` and can be used to restrict query polymorphism.
     *
     * Examples:
     * ```sql
     * TYPE(entity) IN (Exempt, Contractor)
     * ```
     * ```sql
     * CASE TYPE(entity) WHEN Exempt THEN 'Exempt'
     *                   WHEN Contractor THEN 'Contractor'
     *                   ELSE 'NonExempt'
     * END
     * ```
     */
    @SinceJdsl("3.0.0")
    fun type(entity: Entityable<*>): Expression<KClass<*>> = Expressions.type(entity.toEntity())

    /**
     * Creates an expression that represents the type of the path.
     *
     * This is the same as ```TYPE(entity)``` and can be used to restrict query polymorphism.
     *
     * Examples:
     * ```sql
     * TYPE(entity) IN (Exempt, Contractor)
     * ```
     * ```sql
     * CASE TYPE(entity) WHEN Exempt THEN 'Exempt'
     *                   WHEN Contractor THEN 'Contractor'
     *                   ELSE 'NonExempt'
     * END
     * ```
     */
    @SinceJdsl("3.0.0")
    fun type(path: Pathable<*>): Expression<KClass<*>> = Expressions.type(path.toPath())

    /**
     * Creates an expression that represents the concatenation of two or more string values.
     */
    @SinceJdsl("3.2.0")
    fun concat(
        value1: String?,
        value2: String?,
        vararg others: String?,
    ): Expression<String> =
        Expressions.concat(
            Expressions.value(value1),
            Expressions.value(value2),
            others.map { Expressions.value(it) },
        )

    /**
     * Creates an expression that represents the concatenation of two or more string values.
     */
    @SinceJdsl("3.2.0")
    fun concat(
        value1: Expressionable<String>,
        value2: Expressionable<String>,
        vararg others: Expressionable<String>,
    ): Expression<String> =
        Expressions.concat(
            value1.toExpression(),
            value2.toExpression(),
            others.map { it.toExpression() },
        )

    /**
     * Creates an expression that represents a substring of the specified length from the start position of the string.
     * If the length is not specified, it is returned from the start position of the string to the end of the string.
     * The first position of a string is 1.
     */
    @SinceJdsl("3.2.0")
    fun substring(
        value: String,
        start: Int,
        length: Int? = null,
    ): Expression<String> =
        Expressions.substring(
            Expressions.value(value),
            Expressions.value(start),
            length?.let { Expressions.value(length) },
        )

    /**
     * Creates an expression that represents a substring of the specified length from the start position of the string.
     * If the length is not specified, it is returned from the start position of the string to the end of the string.
     * The first position of a string is 1.
     */
    @SinceJdsl("3.2.0")
    fun substring(
        value: Expressionable<String>,
        start: Int,
        length: Int? = null,
    ): Expression<String> =
        Expressions.substring(
            value.toExpression(),
            Expressions.value(start),
            length?.let { Expressions.value(length) },
        )

    /**
     * Creates an expression that represents a substring of the specified length from the start position of the string.
     * If the length is not specified, it is returned from the start position of the string to the end of the string.
     * The first position of a string is 1.
     */
    @SinceJdsl("3.2.0")
    fun substring(
        value: Expressionable<String>,
        start: Expressionable<Int>,
        length: Expressionable<Int>? = null,
    ): Expression<String> =
        Expressions.substring(
            value.toExpression(),
            start.toExpression(),
            length?.toExpression(),
        )

    /**
     * Creates an expression that represents a string with the whitespaces all trimmed
     * from the both sides of the string.
     */
    @SinceJdsl("3.1.0")
    fun trim(value: String): Expression<String> = Expressions.trim(value = Expressions.value(value))

    /**
     * Creates an expression that represents a string with the whitespaces all trimmed
     * from the both sides of the string.
     */
    @SinceJdsl("3.1.0")
    fun trim(value: Expressionable<String>): Expression<String> = Expressions.trim(value = value.toExpression())

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.1.0")
    fun trim(character: Char? = null): TrimFromStep = TrimFromStepDsl(character?.let { Expressions.value(it) })

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trim(character: Expressionable<Char>? = null): TrimFromStep = TrimFromStepDsl(character?.toExpression())

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the leading side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.1.0")
    fun trimLeading(character: Char? = null): TrimFromStep =
        TrimLeadingFromStepDsl(character?.let { Expressions.value(it) })

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the leading side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimLeading(character: Expressionable<Char>? = null): TrimFromStep =
        TrimLeadingFromStepDsl(character?.toExpression())

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the trailing side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.1.0")
    fun trimTrailing(character: Char? = null): TrimFromStep =
        TrimTrailingFromStepDsl(character?.let { Expressions.value(it) })

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the trailing side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimTrailing(character: Expressionable<Char>? = null): TrimFromStep =
        TrimTrailingFromStepDsl(character?.toExpression())

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.1.0")
    fun trimBoth(character: Char? = null): TrimFromStep = TrimBothFromStepDsl(character?.let { Expressions.value(it) })

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimBoth(character: Expressionable<Char>? = null): TrimFromStep = TrimBothFromStepDsl(character?.toExpression())

    /**
     * Creates an expression that represents the string in uppercase.
     */
    @SinceJdsl("3.0.0")
    fun upper(value: String): Expression<String> = Expressions.upper(Expressions.value(value))

    /**
     * Creates an expression that represents the string in uppercase.
     */
    @SinceJdsl("3.0.0")
    fun upper(value: Expressionable<String>): Expression<String> = Expressions.upper(value.toExpression())

    /**
     * Creates an expression that represents the string in lowercase.
     */
    @SinceJdsl("3.0.0")
    fun lower(value: String): Expression<String> = Expressions.lower(Expressions.value(value))

    /**
     * Creates an expression that represents the string in lowercase.
     */
    @SinceJdsl("3.0.0")
    fun lower(value: Expressionable<String>): Expression<String> = Expressions.lower(value.toExpression())

    /**
     * Creates an expression that represents the length of the string as an integer.
     */
    @SinceJdsl("3.0.0")
    fun length(value: String): Expression<Int> = Expressions.length(Expressions.value(value))

    /**
     * Creates an expression that represents the length of the string as an integer.
     */
    @SinceJdsl("3.0.0")
    fun length(value: Expressionable<String>): Expression<Int> = Expressions.length(value.toExpression())

    /**
     * Creates an expression that represents the position of the first occurrence of a substring in a string.
     * If the substring is not found, returns 0.
     * The position starts with 1.
     */
    @SinceJdsl("3.0.0")
    fun locate(
        substring: String,
        string: String,
        start: Int? = null,
    ): Expression<Int> =
        Expressions.locate(
            Expressions.value(substring),
            Expressions.value(string),
            start?.let { Expressions.value(it) },
        )

    /**
     * Creates an expression that represents the position of the first occurrence of a substring in a string.
     * If the substring is not found, returns 0.
     * The position starts with 1.
     */
    @SinceJdsl("3.0.0")
    fun locate(
        substring: String,
        string: Expressionable<String>,
        start: Int? = null,
    ): Expression<Int> =
        Expressions.locate(
            Expressions.value(substring),
            string.toExpression(),
            start?.let { Expressions.value(it) },
        )

    /**
     * Creates an expression that represents the position of the first occurrence of a substring in a string.
     * If the substring is not found, returns 0.
     * The position starts with 1.
     */
    @SinceJdsl("3.0.0")
    fun locate(
        substring: Expressionable<String>,
        string: Expressionable<String>,
        start: Expressionable<Int>? = null,
    ): Expression<Int> =
        Expressions.locate(
            substring.toExpression(),
            string.toExpression(),
            start?.toExpression(),
        )

    /**
     * Creates a step to cast a string expression to another type.
     */
    @SinceJdsl("3.6.0")
    fun cast(value: Expressionable<String>): CastStep = JpqlCastStep(value.toExpression())

    /**
     * Creates a step to cast a scalar expression to a string.
     */
    @SinceJdsl("3.6.0")
    fun <T : Any> cast(value: Expressionable<T>): CastStepToString = JpqlCastStepToString(value.toExpression())

    /**
     * Creates an expression that returns the leftmost count characters from a string.
     */
    @SinceJdsl("3.6.0")
    fun left(
        value: Expressionable<String>,
        len: Expressionable<Int>,
    ): Expression<String> = Expressions.left(value.toExpression(), len.toExpression())

    /**
     * Creates an expression that returns the leftmost count characters from a string.
     */
    @SinceJdsl("3.6.0")
    fun left(
        value: Expressionable<String>,
        len: Int,
    ): Expression<String> = left(value.toExpression(), intLiteral(len))

    /**
     * Creates an expression that returns the rightmost count characters from a string.
     */
    @SinceJdsl("3.6.0")
    fun right(
        value: Expressionable<String>,
        len: Expressionable<Int>,
    ): Expression<String> = Expressions.right(value.toExpression(), len.toExpression())

    /**
     * Creates an expression that returns the rightmost count characters from a string.
     */
    @SinceJdsl("3.6.0")
    fun right(
        value: Expressionable<String>,
        len: Int,
    ): Expression<String> = right(value.toExpression(), intLiteral(len))

    /**
     * Creates an expression that replaces all occurrences of a search string with a replacement string.
     */
    @SinceJdsl("3.6.0")
    fun replace(
        value: Expressionable<String>,
        substring: Expressionable<String>,
        replacement: Expressionable<String>,
    ): Expression<String> =
        Expressions.replace(value.toExpression(), substring.toExpression(), replacement.toExpression())

    /**
     * Creates an expression that replaces all occurrences of a search string with a replacement string.
     */
    @SinceJdsl("3.6.0")
    fun replace(
        value: Expressionable<String>,
        substring: String,
        replacement: String,
    ): Expression<String> = replace(value.toExpression(), stringLiteral(substring), stringLiteral(replacement))

    /**
     * Creates an expression that replaces all occurrences of a search string with a replacement string.
     */
    @SinceJdsl("3.6.0")
    fun replace(
        value: Expressionable<String>,
        substring: Expressionable<String>,
        replacement: String,
    ): Expression<String> = replace(value.toExpression(), substring, stringLiteral(replacement))

    /**
     * Creates an expression that replaces all occurrences of a search string with a replacement string.
     */
    @SinceJdsl("3.6.0")
    fun replace(
        value: Expressionable<String>,
        substring: String,
        replacement: Expressionable<String>,
    ): Expression<String> = replace(value.toExpression(), substring, replacement)

    /**
     * Creates an expression that represents predefined database functions and user-defined database functions.
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.0.0")
    fun <T : Any> function(
        type: KClass<T>,
        name: String,
        vararg args: Any,
    ): Expression<T> = Expressions.function(type, name, args.map { valueOrExpression(it) })

    /**
     * Creates an expression that represents predefined database functions and user-defined database functions.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> function(
        type: KClass<T>,
        name: String,
        vararg args: Expressionable<*>,
    ): Expression<T> = Expressions.function(type, name, args.map { it.toExpression() })

    /**
     * Creates an expression that represents the user-defined expression.
     *
     * The template for the user-defined expression can have placeholders.
     * Placeholders in template are replaced with the expression in args, matching with index.
     *
     * ```
     * Placeholder: { ArgumentIndex }
     * ```
     *
     * Examples:
     * ```
     * customExpression(String::class, "CAST({0} AS VARCHAR)", 100)
     * ```
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(
        type: KClass<T>,
        template: String,
        vararg args: Any,
    ): Expression<T> = Expressions.customExpression(type, template, args.map { valueOrExpression(it) })

    /**
     * Creates an expression that represents the user-defined expression.
     *
     * The template for the user-defined expression can have placeholders.
     * Placeholders in template are replaced with the expression in args, matching with index.
     *
     * ```
     * Placeholder: { ArgumentIndex }
     * ```
     *
     * Examples:
     * ```
     * customExpression(String::class, "CAST({0} AS VARCHAR)", path(User::age))
     * ```
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(
        type: KClass<T>,
        template: String,
        vararg args: Expressionable<*>,
    ): Expression<T> = Expressions.customExpression(type, template, args.map { it.toExpression() })

    /**
     * Creates a subquery with the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asSubquery(): Subquery<T> = Expressions.subquery(this.toQuery())

    /**
     * Creates a derived entity with the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asEntity(): Entity<T> = Entities.derivedEntity(this.toQuery())

    /**
     * Creates a derived entity with the select query and alias.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> JpqlQueryable<SelectQuery<T>>.asEntity(alias: String): Entity<T> =
        Entities.derivedEntity(this.toQuery(), alias)

    /**
     * Creates a join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> join(type: KClass<T>): JoinOnStep<T> = JoinDsl(Entities.entity(type), JoinType.INNER)

    /**
     * Creates a join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> join(entity: Entityable<T>): JoinOnStep<T> = JoinDsl(entity.toEntity(), JoinType.INNER)

    /**
     * Creates an association join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> join(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @JvmName("joinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> join(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> join(path: Pathable<T>): AssociationJoinOnStep<T> =
        AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)

    /**
     * Creates an association join with the path.
     */
    @JvmName("joinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> join(path: Pathable<S>): AssociationJoinOnStep<T> =
        AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)

    /**
     * Creates a join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> innerJoin(type: KClass<T>): JoinOnStep<T> = JoinDsl(Entities.entity(type), JoinType.INNER)

    /**
     * Creates a join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> innerJoin(entity: Entityable<T>): JoinOnStep<T> = JoinDsl(entity.toEntity(), JoinType.INNER)

    /**
     * Creates an association join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> innerJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @JvmName("innerJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> innerJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> innerJoin(path: Pathable<T>): AssociationJoinOnStep<T> =
        AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)

    /**
     * Creates an association join with the path.
     */
    @JvmName("innerJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> innerJoin(path: Pathable<S>): AssociationJoinOnStep<T> =
        AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)

    /**
     * Creates a join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> leftJoin(type: KClass<T>): JoinOnStep<T> = JoinDsl(Entities.entity(type), JoinType.LEFT)

    /**
     * Creates a join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> leftJoin(entity: Entityable<T>): JoinOnStep<T> = JoinDsl(entity.toEntity(), JoinType.LEFT)

    /**
     * Creates an association join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> leftJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    /**
     * Creates an association join with the path specified by the property.
     */
    @JvmName("leftJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> leftJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    /**
     * Creates an association join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> leftJoin(path: Pathable<T>): AssociationJoinOnStep<T> =
        AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.LEFT)

    /**
     * Creates an association join with the path.
     */
    @JvmName("leftJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> leftJoin(path: Pathable<S>): AssociationJoinOnStep<T> =
        AssociationJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.LEFT)

    /**
     * Creates a fetch join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> fetchJoin(type: KClass<T>): JoinOnStep<T> = FetchJoinDsl(Entities.entity(type), JoinType.INNER)

    /**
     * Creates a fetch join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> fetchJoin(entity: Entityable<T>): JoinOnStep<T> = FetchJoinDsl(entity.toEntity(), JoinType.INNER)

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> fetchJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @JvmName("fetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> fetchJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> fetchJoin(path: Pathable<T>): AssociationJoinOnStep<T> =
        AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)

    /**
     * Creates an association fetch join with the path.
     */
    @JvmName("fetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> fetchJoin(path: Pathable<S>): AssociationJoinOnStep<T> =
        AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)

    /**
     * Creates a fetch join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> innerFetchJoin(type: KClass<T>): JoinOnStep<T> = FetchJoinDsl(Entities.entity(type), JoinType.INNER)

    /**
     * Creates a fetch join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> innerFetchJoin(entity: Entityable<T>): JoinOnStep<T> = FetchJoinDsl(entity.toEntity(), JoinType.INNER)

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> innerFetchJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @JvmName("innerFetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> innerFetchJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.INNER)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> innerFetchJoin(path: Pathable<T>): AssociationJoinOnStep<T> =
        AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)

    /**
     * Creates an association fetch join with the path.
     */
    @JvmName("innerFetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> innerFetchJoin(path: Pathable<S>): AssociationJoinOnStep<T> =
        AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.INNER)

    /**
     * Creates a fetch join with the entity specified by the type.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> leftFetchJoin(type: KClass<T>): JoinOnStep<T> = FetchJoinDsl(Entities.entity(type), JoinType.LEFT)

    /**
     * Creates a fetch join with the entity.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> leftFetchJoin(entity: Entityable<T>): JoinOnStep<T> = FetchJoinDsl(entity.toEntity(), JoinType.LEFT)

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V> leftFetchJoin(property: KProperty1<T, @Exact V>): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    /**
     * Creates an association fetch join with the path specified by the property.
     */
    @JvmName("leftFetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <T : Any, reified V, S : Collection<V>> leftFetchJoin(
        property: KProperty1<T, @Exact S>,
    ): AssociationJoinOnStep<V & Any> {
        @Suppress("UNCHECKED_CAST")
        val entity = Entities.entity(V::class as KClass<V & Any>)

        return AssociationFetchJoinDsl(entity, Paths.path(property), JoinType.LEFT)
    }

    /**
     * Creates an association fetch join with the path.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> leftFetchJoin(path: Pathable<T>): AssociationJoinOnStep<T> =
        AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.LEFT)

    /**
     * Creates an association fetch join with the path.
     */
    @JvmName("leftFetchJoinCollection")
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any, S : Collection<T>> leftFetchJoin(path: Pathable<S>): AssociationJoinOnStep<T> =
        AssociationFetchJoinDsl(Entities.entity(T::class), path.toPath(), JoinType.LEFT)

    /**
     * Creates a predicate the inverse of the predicate.
     */
    @SinceJdsl("3.0.0")
    fun not(predicate: Predicatable): Predicate = Predicates.not(predicate.toPredicate())

    /**
     * Creates a predicate that combines predicates with `AND`.
     * The predicates are each enclosed in parentheses.
     *
     * This is the same as ```(predicate1) AND (predicate2)```.
     *
     * If predicates is empty, then it represents `1 = 1`.
     */
    @SinceJdsl("3.0.0")
    fun and(vararg predicates: Predicatable?): Predicate =
        Predicates.and(predicates.mapNotNull { it?.toPredicate() }.map { Predicates.parentheses(it) })

    /**
     * Creates a predicate that combines predicates with `AND`.
     * The predicates are each enclosed in parentheses.
     *
     * This is the same as ```predicate1 AND predicate2```.
     */
    @SinceJdsl("3.0.0")
    fun Predicatable.and(predicate: Predicatable): Predicate =
        Predicates.and(listOf(this.toPredicate(), predicate.toPredicate()))

    /**
     * Creates a predicate that combines predicates with `OR`.
     * The predicates are each enclosed in parentheses.
     *
     * This is the same as ```(predicate1) OR (predicate2)```.
     *
     * If predicates is empty, then it represents `0 = 1`.
     */
    @SinceJdsl("3.0.0")
    fun or(vararg predicates: Predicatable?): Predicate =
        Predicates.or(predicates.mapNotNull { it?.toPredicate() }.map { Predicates.parentheses(it) })

    /**
     * Creates a predicate that combines predicates with `OR`.
     * The predicates are each enclosed in parentheses.
     *
     * This is the same as ```predicate1 OR predicate2```.
     */
    @SinceJdsl("3.0.0")
    fun Predicatable.or(predicate: Predicatable): Predicate =
        Predicates.or(listOf(this.toPredicate(), predicate.toPredicate()))

    /**
     * Creates a predicate that tests whether the value is null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.isNull(): Predicate = Predicates.isNull(this.toExpression())

    /**
     * Creates a predicate that tests whether the value is not null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.isNotNull(): Predicate = Predicates.isNotNull(this.toExpression())

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.equal(value: S): Predicate =
        Predicates.equal(this.toExpression(), Expressions.value(value))

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equal(value: Expressionable<T>): Predicate =
        Predicates.equal(this.toExpression(), value.toExpression())

    /**
     * Creates a predicate that tests whether the value is equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equalAll(subquery: Subquery<T>): Predicate =
        Predicates.equalAll(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether the value is equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.equalAny(subquery: Subquery<T>): Predicate =
        Predicates.equalAny(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.eq(compareValue: S): Predicate =
        Predicates.equal(this.toExpression(), Expressions.value(compareValue))

    /**
     * Creates a predicate that tests whether values are equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eq(compareValue: Expressionable<T>): Predicate =
        Predicates.equal(this.toExpression(), compareValue.toExpression())

    /**
     * Creates a predicate that tests whether the value is equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eqAll(subquery: Subquery<T>): Predicate =
        Predicates.equalAll(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether the value is equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.eqAny(subquery: Subquery<T>): Predicate =
        Predicates.equalAny(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notEqual(value: S): Predicate =
        Predicates.notEqual(this.toExpression(), Expressions.value(value))

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqual(value: Expressionable<T>): Predicate =
        Predicates.notEqual(this.toExpression(), value.toExpression())

    /**
     * Creates a predicate that tests whether the value is not equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqualAll(subquery: Subquery<T>): Predicate =
        Predicates.notEqualAll(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether the value is not equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notEqualAny(subquery: Subquery<T>): Predicate =
        Predicates.notEqualAny(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.ne(value: S): Predicate =
        Predicates.notEqual(this.toExpression(), Expressions.value(value))

    /**
     * Creates a predicate that tests whether values are not equal.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.ne(value: Expressionable<T>): Predicate =
        Predicates.notEqual(this.toExpression(), value.toExpression())

    /**
     * Creates a predicate that tests whether the value is not equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.neAll(subquery: Subquery<T>): Predicate =
        Predicates.notEqualAll(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether the value is not equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.neAny(subquery: Subquery<T>): Predicate =
        Predicates.notEqualAny(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThan(
        value: S,
        inclusive: Boolean,
    ): Predicate =
        if (inclusive) {
            Predicates.lessThanOrEqualTo(this.toExpression(), Expressions.value(value))
        } else {
            Predicates.lessThan(this.toExpression(), Expressions.value(value))
        }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThan(value: S): Predicate =
        lessThan(value, inclusive = false)

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThan(
        value: Expressionable<T>,
        inclusive: Boolean,
    ): Predicate =
        if (inclusive) {
            Predicates.lessThanOrEqualTo(this.toExpression(), value.toExpression())
        } else {
            Predicates.lessThan(this.toExpression(), value.toExpression())
        }

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThan(value: Expressionable<T>): Predicate =
        lessThan(value, inclusive = false)

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAll(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate =
        if (inclusive) {
            Predicates.lessThanOrEqualToAll(this.toExpression(), subquery)
        } else {
            Predicates.lessThanAll(this.toExpression(), subquery)
        }

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAny(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate =
        if (inclusive) {
            Predicates.lessThanOrEqualToAny(this.toExpression(), subquery)
        } else {
            Predicates.lessThanAny(this.toExpression(), subquery)
        }

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAll(subquery: Subquery<T>): Predicate =
        lessThanAll(subquery, inclusive = false)

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanAny(subquery: Subquery<T>): Predicate =
        lessThanAny(subquery, inclusive = false)

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(
        value: T,
        inclusive: Boolean,
    ): Predicate = lessThan(value, inclusive)

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lt(value: S): Predicate =
        lessThan(value, inclusive = false)

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(
        value: Expressionable<T>,
        inclusive: Boolean,
    ): Predicate = lessThan(value, inclusive)

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAll(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate = lessThanAll(subquery, inclusive)

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAny(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate = lessThanAny(subquery, inclusive)

    /**
     * Creates a predicate that tests whether the value1 is less than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lt(value: Expressionable<T>): Predicate =
        lessThan(value, inclusive = false)

    /**
     * Creates a predicate that tests whether the value is less than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAll(subquery: Subquery<T>): Predicate =
        lessThanAll(subquery, inclusive = false)

    /**
     * Creates a predicate that tests whether the value is less than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ltAny(subquery: Subquery<T>): Predicate =
        lessThanAny(subquery, inclusive = false)

    /**
     * Creates a predicate that tests whether the value1 is less than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.lessThanOrEqualTo(value: S): Predicate =
        lessThan(value, inclusive = true)

    /**
     * Creates a predicate that tests whether the value1 is less than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualTo(value: Expressionable<T>): Predicate =
        lessThan(value, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is less than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualToAll(subquery: Subquery<T>): Predicate =
        lessThanAll(subquery, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is less than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.lessThanOrEqualToAny(subquery: Subquery<T>): Predicate =
        lessThanAny(subquery, inclusive = true)

    /**
     * Creates a predicate that tests whether the value1 is less than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.le(value: S): Predicate = lessThan(value, inclusive = true)

    /**
     * Creates a predicate that tests whether the value1 is less than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.le(value: Expressionable<T>): Predicate =
        lessThan(value, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is less than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.leAll(subquery: Subquery<T>): Predicate =
        lessThanAll(subquery, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is less than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.leAny(subquery: Subquery<T>): Predicate =
        lessThanAny(subquery, inclusive = true)

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThan(
        value: S,
        inclusive: Boolean,
    ): Predicate =
        if (inclusive) {
            Predicates.greaterThanOrEqualTo(this.toExpression(), Expressions.value(value))
        } else {
            Predicates.greaterThan(this.toExpression(), Expressions.value(value))
        }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThan(value: S): Predicate =
        greaterThan(value, inclusive = false)

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThan(
        value: Expressionable<T>,
        inclusive: Boolean,
    ): Predicate =
        if (inclusive) {
            Predicates.greaterThanOrEqualTo(this.toExpression(), value.toExpression())
        } else {
            Predicates.greaterThan(this.toExpression(), value.toExpression())
        }

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAll(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate =
        if (inclusive) {
            Predicates.greaterThanOrEqualToAll(this.toExpression(), subquery)
        } else {
            Predicates.greaterThanAll(this.toExpression(), subquery)
        }

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAny(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate =
        if (inclusive) {
            Predicates.greaterThanOrEqualToAny(this.toExpression(), subquery)
        } else {
            Predicates.greaterThanAny(this.toExpression(), subquery)
        }

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThan(value: Expressionable<T>): Predicate =
        greaterThan(value, inclusive = false)

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAll(subquery: Subquery<T>): Predicate =
        greaterThanAll(subquery, inclusive = false)

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanAny(subquery: Subquery<T>): Predicate =
        greaterThanAny(subquery, inclusive = false)

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.gt(
        value: S,
        inclusive: Boolean,
    ): Predicate = greaterThan(value, inclusive)

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.gt(value: S): Predicate =
        greaterThan(value, inclusive = false)

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gt(
        value: Expressionable<T>,
        inclusive: Boolean,
    ): Predicate = greaterThan(value, inclusive)

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAll(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate = greaterThanAll(subquery, inclusive)

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAny(
        subquery: Subquery<T>,
        inclusive: Boolean,
    ): Predicate = greaterThanAny(subquery, inclusive)

    /**
     * Creates a predicate that tests whether the value1 is greater than the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gt(value: Expressionable<T>): Predicate =
        greaterThan(value, inclusive = false)

    /**
     * Creates a predicate that tests whether the value is greater than all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAll(subquery: Subquery<T>): Predicate =
        greaterThanAll(subquery, inclusive = false)

    /**
     * Creates a predicate that tests whether the value is greater than any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.gtAny(subquery: Subquery<T>): Predicate =
        greaterThanAny(subquery, inclusive = false)

    /**
     * Creates a predicate that tests whether the value1 is greater than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.greaterThanOrEqualTo(value: S): Predicate =
        greaterThan(value, inclusive = true)

    /**
     * Creates a predicate that tests whether the value1 is greater than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualTo(value: Expressionable<T>): Predicate =
        greaterThan(value, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is greater than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualToAll(subquery: Subquery<T>): Predicate =
        greaterThanAll(subquery, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is greater than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.greaterThanOrEqualToAny(subquery: Subquery<T>): Predicate =
        greaterThanAny(subquery, inclusive = true)

    /**
     * Creates a predicate that tests whether the value1 is greater than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.ge(value: S): Predicate =
        greaterThan(value, inclusive = true)

    /**
     * Creates a predicate that tests whether the value1 is greater than or equal to the value2.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.ge(value: Expressionable<T>): Predicate =
        greaterThan(value, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is greater than or equal to all values in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.geAll(subquery: Subquery<T>): Predicate =
        greaterThanAll(subquery, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is greater than or equal to any value in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.geAny(subquery: Subquery<T>): Predicate =
        greaterThanAny(subquery, inclusive = true)

    /**
     * Creates a predicate that tests whether the value is between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.between(
        min: S,
        max: S,
    ): Predicate = Predicates.between(this.toExpression(), Expressions.value(min), Expressions.value(max))

    /**
     * Creates a predicate that tests whether the value is between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.between(
        min: Expressionable<T>,
        max: Expressionable<T>,
    ): Predicate = Predicates.between(this.toExpression(), min.toExpression(), max.toExpression())

    /**
     * Creates a predicate that tests whether the value is not between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>, S : T?> Expressionable<@Exact T>.notBetween(
        min: S,
        max: S,
    ): Predicate = Predicates.notBetween(this.toExpression(), Expressions.value(min), Expressions.value(max))

    /**
     * Creates a predicate that tests whether the value is not between in min and max.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<T>> Expressionable<@Exact T>.notBetween(
        min: Expressionable<T>,
        max: Expressionable<T>,
    ): Predicate = Predicates.notBetween(this.toExpression(), min.toExpression(), max.toExpression())

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.`in`(vararg compareValues: S): Predicate =
        Predicates.`in`(this.toExpression(), compareValues.map { Expressions.value(it) })

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.`in`(compareValues: Iterable<S>): Predicate =
        Predicates.`in`(this.toExpression(), compareValues.map { Expressions.value(it) })

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(vararg compareValues: Expressionable<T>?): Predicate =
        Predicates.`in`(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })

    /**
     * Creates a predicate that tests whether the value is in compareValues.
     */
    @JvmName("inExpressions")
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(compareValues: Iterable<Expressionable<T>?>): Predicate =
        Predicates.`in`(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })

    /**
     * Creates a predicate that tests whether the value is in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.`in`(subquery: Subquery<T>): Predicate =
        Predicates.`in`(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notIn(compareValues: Iterable<S>): Predicate =
        Predicates.notIn(this.toExpression(), compareValues.map { Expressions.value(it) })

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T?> Expressionable<@Exact T>.notIn(vararg compareValues: S): Predicate =
        Predicates.notIn(this.toExpression(), compareValues.map { Expressions.value(it) })

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(vararg compareValues: Expressionable<T>?): Predicate =
        Predicates.notIn(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })

    /**
     * Creates a predicate that tests whether the value is not in compareValues.
     */
    @JvmName("notInExpressions")
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(compareValues: Iterable<Expressionable<T>?>): Predicate =
        Predicates.notIn(this.toExpression(), compareValues.mapNotNull { it?.toExpression() })

    /**
     * Creates a predicate that tests whether the value is not in the subquery.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> Expressionable<@Exact T>.notIn(subquery: Subquery<T>): Predicate =
        Predicates.notIn(this.toExpression(), subquery)

    /**
     * Creates a predicate that tests whether the value matches the pattern.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<String>.like(
        pattern: String,
        escape: Char? = null,
    ): Predicate =
        Predicates.like(
            this.toExpression(),
            Expressions.value(pattern),
            escape?.let { Expressions.value(it) },
        )

    /**
     * Creates a predicate that tests whether the value matches the pattern.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<String>.like(
        pattern: Expressionable<String>,
        escape: Expressionable<Char>? = null,
    ): Predicate =
        Predicates.like(
            this.toExpression(),
            pattern.toExpression(),
            escape?.toExpression(),
        )

    /**
     * Creates a predicate that tests whether the value does not match the pattern.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<String>.notLike(
        pattern: String,
        escape: Char? = null,
    ): Predicate =
        Predicates.notLike(
            this.toExpression(),
            Expressions.value(pattern),
            escape?.let { Expressions.value(it) },
        )

    /**
     * Creates a predicate that tests whether the value does not match the pattern.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<String>.notLike(
        pattern: Expressionable<String>,
        escape: Expressionable<Char>? = null,
    ): Predicate =
        Predicates.notLike(
            this.toExpression(),
            pattern.toExpression(),
            escape?.toExpression(),
        )

    /**
     * Creates a predicate that tests whether the path has no elements.
     */
    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> Pathable<S>.isEmpty(): Predicate = Predicates.isEmpty(this.toPath())

    /**
     * Creates a predicate that tests whether the path has an element.
     */
    @SinceJdsl("3.0.0")
    fun <T, S : Collection<T>> Pathable<S>.isNotEmpty(): Predicate = Predicates.isNotEmpty(this.toPath())

    /**
     * Creates a predicate that tests whether the subquery has a row.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> exists(subquery: Subquery<T>): Predicate = Predicates.exists(subquery)

    /**
     * Creates a predicate that tests whether the subquery has no row.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> notExists(subquery: Subquery<T>): Predicate = Predicates.notExists(subquery)

    /**
     * Creates a predicate that represents predefined database functions and user-defined database functions.
     */
    @Suppress("UNUSED_PARAMETER")
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.0.0")
    fun function(
        type: KClass<Boolean>,
        name: String,
        vararg args: Any,
    ): Predicate = Predicates.function(name, args.map { valueOrExpression(it) })

    /**
     * Creates a predicate that represents predefined database functions and user-defined database functions.
     */
    @Suppress("UNUSED_PARAMETER")
    @SinceJdsl("3.0.0")
    fun function(
        type: KClass<Boolean>,
        name: String,
        vararg args: Expressionable<*>,
    ): Predicate = Predicates.function(name, args.map { it.toExpression() })

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
     * customPredicate("{0} MEMBER OF {1}", value(author), path(Book::authors))
     * ```
     */
    @LowPriorityInOverloadResolution
    @SinceJdsl("3.3.0")
    fun customPredicate(
        template: String,
        vararg args: Any,
    ): Predicate = Predicates.customPredicate(template, args.map { valueOrExpression(it) })

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
     * customPredicate("{0} MEMBER OF {1}", value(author), path(Book::authors))
     * ```
     */
    @SinceJdsl("3.3.0")
    fun customPredicate(
        template: String,
        vararg args: Expressionable<*>,
    ): Predicate = Predicates.customPredicate(template, args.map { it.toExpression() })

    /**
     * Creates a sort that sorts the expression in ascending order.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.asc(): SortNullsStep = SortDsl(this.toExpression(), Sort.Order.ASC)

    /**
     * Creates a sort that sorts the expression in descending order.
     */
    @SinceJdsl("3.0.0")
    fun Expressionable<*>.desc(): SortNullsStep = SortDsl(this.toExpression(), Sort.Order.DESC)

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.5.5")
    fun <T : Any> select(
        type: KClass<T>,
        expr: Expressionable<T>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            type,
            distinct = false,
            listOf(expr.toExpression()),
        )

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(expr: Expressionable<T>): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            T::class,
            distinct = false,
            listOf(expr.toExpression()),
        )

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.5.5")
    fun <T : Any> select(
        type: KClass<T>,
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            type,
            distinct = false,
            listOf(expr.toExpression()) + exprs.map { it.toExpression() },
        )

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> select(
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            T::class,
            distinct = false,
            listOf(expr.toExpression()) + exprs.map { it.toExpression() },
        )

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.5.5")
    fun <T : Any> selectDistinct(
        type: KClass<T>,
        expr: Expressionable<T>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            type,
            distinct = true,
            listOf(expr.toExpression()),
        )

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(expr: Expressionable<T>): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            T::class,
            distinct = true,
            listOf(expr.toExpression()),
        )

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.5.5")
    fun <T : Any> selectDistinct(
        type: KClass<T>,
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            type,
            distinct = true,
            listOf(expr.toExpression()) + exprs.map { it.toExpression() },
        )

    /**
     * Creates a select clause in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinct(
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            T::class,
            distinct = true,
            listOf(expr.toExpression()) + exprs.map { it.toExpression() },
        )

    /**
     * Creates a select clause with the DTO projection in a select query.
     */
    @SinceJdsl("3.5.5")
    fun <T : Any> selectNew(
        type: KClass<T>,
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            returnType = type,
            distinct = false,
            select =
                listOf(
                    Expressions.new(
                        type,
                        listOf(expr.toExpression()) + exprs.map { it.toExpression() },
                    ),
                ),
        )

    /**
     * Creates a select clause with the DTO projection in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectNew(
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            returnType = T::class,
            distinct = false,
            select =
                listOf(
                    Expressions.new(
                        T::class,
                        listOf(expr.toExpression()) + exprs.map { it.toExpression() },
                    ),
                ),
        )

    /**
     * Creates a select clause with the DTO projection in a select query.
     */
    @SinceJdsl("3.5.5")
    fun <T : Any> selectDistinctNew(
        type: KClass<T>,
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            returnType = type,
            distinct = true,
            select =
                listOf(
                    Expressions.new(
                        type,
                        listOf(expr.toExpression()) + exprs.map { it.toExpression() },
                    ),
                ),
        )

    /**
     * Creates a select clause with the DTO projection in a select query.
     */
    @SinceJdsl("3.0.0")
    inline fun <reified T : Any> selectDistinctNew(
        expr: Expressionable<*>,
        vararg exprs: Expressionable<*>,
    ): SelectQueryFromStep<T> =
        SelectQueryFromStepDsl(
            returnType = T::class,
            distinct = true,
            select =
                listOf(
                    Expressions.new(
                        T::class,
                        listOf(expr.toExpression()) + exprs.map { it.toExpression() },
                    ),
                ),
        )

    /**
     * Creates an update clause in an update query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> update(entity: Entityable<T>): UpdateQuerySetFirstStep<T> =
        UpdateQuerySetStepFirstDsl(entity.toEntity())

    /**
     * Creates a delete from clause in a delete query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> deleteFrom(entity: Entityable<T>): DeleteQueryWhereStep<T> = DeleteQueryDsl(entity.toEntity())

    /**
     * Creates a UNION query with two select queries.
     */
    @SinceJdsl("3.6.0")
    @JvmName("union")
    inline fun <reified T : Any> union(
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> =
        SetOperatorQueryDsl(
            returnType = T::class,
            leftQuery = left,
            setOperator = SetOperator.UNION,
            rightQuery = right,
        )

    /**
     * Creates a UNION ALL query with two select queries.
     */
    @JvmName("unionAll")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> unionAll(
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> =
        SetOperatorQueryDsl(
            returnType = T::class,
            leftQuery = left,
            setOperator = SetOperator.UNION_ALL,
            rightQuery = right,
        )

    /**
     * Creates a UNION query that represents the union of this query and the [right] query.
     */
    @JvmName("unionExtension")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> JpqlQueryable<SelectQuery<T>>.union(
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> = union(this, right)

    /**
     * Creates a UNION ALL that represents the union all of this query and the [right] query.
     */
    @JvmName("unionAllExtension")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> JpqlQueryable<SelectQuery<T>>.unionAll(
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> = unionAll(this, right)

    /**
     * Creates an EXCEPT query with two select queries.
     */
    @SinceJdsl("3.6.0")
    @JvmName("except")
    inline fun <reified T : Any> except(
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> =
        SetOperatorQueryDsl(
            returnType = T::class,
            leftQuery = left,
            setOperator = SetOperator.EXCEPT,
            rightQuery = right,
        )

    /**
     * Creates an EXCEPT ALL query with two select queries.
     */
    @JvmName("exceptAll")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> exceptAll(
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> =
        SetOperatorQueryDsl(
            returnType = T::class,
            leftQuery = left,
            setOperator = SetOperator.EXCEPT_ALL,
            rightQuery = right,
        )

    /**
     * Creates an EXCEPT query that represents the except of this query and the [right] query.
     */
    @JvmName("exceptExtension")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> JpqlQueryable<SelectQuery<T>>.except(
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> = except(this, right)

    /**
     * Creates an EXCEPT ALL that represents the except all of this query and the [right] query.
     */
    @JvmName("exceptAllExtension")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> JpqlQueryable<SelectQuery<T>>.exceptAll(
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> = exceptAll(this, right)

    /**
     * Creates an INTERSECT query with two select queries.
     */
    @SinceJdsl("3.6.0")
    @JvmName("intersect")
    inline fun <reified T : Any> intersect(
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> =
        SetOperatorQueryDsl(
            returnType = T::class,
            leftQuery = left,
            setOperator = SetOperator.INTERSECT,
            rightQuery = right,
        )

    /**
     * Creates an INTERSECT ALL query with two select queries.
     */
    @JvmName("intersectAll")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> intersectAll(
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> =
        SetOperatorQueryDsl(
            returnType = T::class,
            leftQuery = left,
            setOperator = SetOperator.INTERSECT_ALL,
            rightQuery = right,
        )

    /**
     * Creates an INTERSECT query that represents the intersecting of this query and the [right] query.
     */
    @JvmName("intersectExtension")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> JpqlQueryable<SelectQuery<T>>.intersect(
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> = intersect(this, right)

    /**
     * Creates an INTERSECT ALL that represents to intersect all of this query and the [right] query.
     */
    @JvmName("intersectAllExtension")
    @SinceJdsl("3.6.0")
    inline fun <reified T : Any> JpqlQueryable<SelectQuery<T>>.intersectAll(
        right: JpqlQueryable<SelectQuery<T>>,
    ): SelectQueryOrderByStep<T> = intersectAll(this, right)

    private fun valueOrExpression(value: Any): Expression<*> =
        if (value is Expression<*>) {
            value
        } else {
            Expressions.value(value)
        }
}
