package com.linecorp.kotlinjdsl.querymodel.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAbs
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAliasedExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAvg
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseValue
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseWhen
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCast
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCeiling
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCoalesce
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlConcat
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCount
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrentDate
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrentTime
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrentTimestamp
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCustomExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlDivide
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlEntityType
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExp
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpressionParentheses
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFloor
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFunctionExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlId
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLeft
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLength
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLiteral
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLn
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocalDate
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocalDateTime
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocalTime
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocate
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLower
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMax
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMin
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMinus
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMod
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNew
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNullIf
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlParam
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPathType
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPlus
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPower
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlReplace
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlRight
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlRound
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSign
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSize
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSqrt
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubstring
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSum
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTimes
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrim
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimBoth
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimLeading
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimTrailing
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlUpper
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlVersion
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIndex
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.internal.Exact
import kotlin.reflect.KClass

/**
 * Factory class that creates [Expression].
 */
@SinceJdsl("3.0.0")
object Expressions {
    /**
     * Creates a parameter expression with a generated name and the value.
     */
    @SinceJdsl("3.0.0")
    fun <T> value(value: @Exact T): Expression<T & Any> =
        if (value == null) {
            nullValue()
        } else {
            JpqlValue(value)
        }

    /**
     * Creates a parameter expression with a generated name and null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullValue(): Expression<T> {
        @Suppress("UNCHECKED_CAST")
        return JpqlNull as Expression<T>
    }

    /**
     * Creates a literal expression with the int.
     */
    @SinceJdsl("3.0.0")
    fun intLiteral(int: Int): Expression<Int> = JpqlLiteral.IntLiteral(int)

    /**
     * Creates a literal expression with the long.
     */
    @SinceJdsl("3.0.0")
    fun longLiteral(long: Long): Expression<Long> = JpqlLiteral.LongLiteral(long)

    /**
     * Creates a literal expression with the float.
     */
    @SinceJdsl("3.0.0")
    fun floatLiteral(float: Float): Expression<Float> = JpqlLiteral.FloatLiteral(float)

    /**
     * Creates a literal expression with the double.
     */
    @SinceJdsl("3.0.0")
    fun doubleLiteral(double: Double): Expression<Double> = JpqlLiteral.DoubleLiteral(double)

    /**
     * Creates a literal expression with the boolean.
     */
    @SinceJdsl("3.0.0")
    fun booleanLiteral(boolean: Boolean): Expression<Boolean> = JpqlLiteral.BooleanLiteral(boolean)

    /**
     * Creates a literal expression with the char.
     * If the char is '(single quote), it is rendered as ''(two single quotes).
     */
    @SinceJdsl("3.0.0")
    fun charLiteral(char: Char): Expression<Char> = JpqlLiteral.CharLiteral(char)

    /**
     * Creates a literal expression with the string.
     * If the string contains '(single quote), it is rendered as ''(two single quotes).
     * For example: literal''s.
     */
    @SinceJdsl("3.0.0")
    fun stringLiteral(string: String): Expression<String> = JpqlLiteral.StringLiteral(string)

    /**
     * Creates a literal expression with the enum.
     */
    @SinceJdsl("3.0.0")
    fun <T : Enum<T>> enumLiteral(enum: T): Expression<T> = JpqlLiteral.EnumLiteral(enum)

    /**
     * Creates a literal expression with null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullLiteral(): Expression<T> {
        @Suppress("UNCHECKED_CAST")
        return JpqlLiteral.NullLiteral as Expression<T>
    }

    /**
     * Creates a parameter expression with the name.
     */
    @SinceJdsl("3.0.0")
    fun <T> param(name: String): Expression<T & Any> = JpqlParam(name, null)

    /**
     * Creates a parameter expression with the name and value.
     * The value can be overridden in rendering.
     */
    @SinceJdsl("3.0.0")
    fun <T> param(
        name: String,
        value: @Exact T,
    ): Expression<T & Any> = JpqlParam(name, value)

    /**
     * Creates an expression that represents the plus of values.
     *
     * This is the same as ```value1 + value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> plus(
        value1: Expression<T>,
        value2: Expression<S>,
    ): Expression<T> = JpqlPlus(value1, value2)

    /**
     * Creates an expression that represents the minus of values.
     *
     * This is the same as ```value1 - value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> minus(
        value1: Expression<T>,
        value2: Expression<S>,
    ): Expression<T> = JpqlMinus(value1, value2)

    /**
     * Creates an expression that represents the times of values.
     *
     * This is the same as ```value1 * value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> times(
        value1: Expression<T>,
        value2: Expression<S>,
    ): Expression<T> = JpqlTimes(value1, value2)

    /**
     * Creates an expression that represents the divide of values.
     *
     * This is the same as ```value1 / value2```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number, S : T> div(
        value1: Expression<T>,
        value2: Expression<S>,
    ): Expression<T> = JpqlDivide(value1, value2)

    /**
     * Creates an expression that represents the absolute value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> abs(value: Expression<T>): Expression<T> = JpqlAbs(value)

    /**
     * Creates an expression that is enclosed in ceiling.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> ceiling(value: Expression<T>): Expression<T> = JpqlCeiling(value)

    /**
     * Creates an expression that represents the exponential value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> exp(value: Expression<T>): Expression<Double> = JpqlExp(value)

    /**
     * Creates an expression that is enclosed in floor.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> floor(value: Expression<T>): Expression<T> = JpqlFloor(value)

    /**
     * Creates an expression that represents the index of the value in an ordered list.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any> index(entity: Entity<T>): Expression<Int> = JpqlIndex(entity)

    /**
     * Creates an expression that represents the natural logarithm of the value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> ln(value: Expression<T>): Expression<Double> = JpqlLn(value)

    /**
     * Creates an expression that represents the mod of values.
     */
    @SinceJdsl("3.4.0")
    fun mod(
        value1: Expression<Int>,
        value2: Expression<Int>,
    ): Expression<Int> = JpqlMod(value1, value2)

    /**
     * Create an expression that represents the power of [base] and [exponent].
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> power(
        base: Expression<T>,
        exponent: Expression<T>,
    ): Expression<Double> = JpqlPower(base, exponent)

    /**
     * Creates an expression that represents the sign of a numeric value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> sign(value: Expression<T>): Expression<Int> = JpqlSign(value)

    /**
     * Creates an expression that represents the square root of the value.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> sqrt(value: Expression<T>): Expression<Double> = JpqlSqrt(value)

    /**
     * Creates an expression that represents the rounding of the specified value to a specified scale.
     */
    @SinceJdsl("3.4.0")
    fun <T : Number> round(
        value: Expression<T>,
        scale: Expression<Int>,
    ): Expression<T> = JpqlRound(value, scale)

    /**
     * Creates an expression that represents the number of elements of the collection.
     */
    @SinceJdsl("3.4.0")
    fun <T, S : Collection<T>> size(path: Path<S>): Expression<Int> = JpqlSize(path)

    /**
     * Creates an expression that represents the current date.
     *
     * This is the same as ```CURRENT_DATE```.
     */
    @SinceJdsl("3.4.0")
    fun currentDate(): Expression<Date> = JpqlCurrentDate

    /**
     * Creates an expression that represents the current time.
     *
     * This is the same as ```CURRENT_TIME```.
     */
    @SinceJdsl("3.4.0")
    fun currentTime(): Expression<Time> = JpqlCurrentTime

    /**
     * Creates an expression that represents the current timestamp.
     *
     * This is the same as ```CURRENT_TIMESTAMP```.
     */
    @SinceJdsl("3.4.0")
    fun currentTimestamp(): Expression<Timestamp> = JpqlCurrentTimestamp

    /**
     * Creates an expression that represents the local date.
     *
     * This is the same as ```LOCAL DATE```.
     */
    @SinceJdsl("3.4.0")
    fun localDate(): Expression<LocalDate> = JpqlLocalDate

    /**
     * Creates an expression that represents the local time.
     *
     * This is the same as ```LOCAL TIME```.
     */
    @SinceJdsl("3.4.0")
    fun localTime(): Expression<LocalTime> = JpqlLocalTime

    /**
     * Creates an expression that represents the local datetime.
     *
     * This is the same as ```LOCAL DATETIME```.
     */
    @SinceJdsl("3.4.0")
    fun localDateTime(): Expression<LocalDateTime> = JpqlLocalDateTime

    /**
     * Creates an expression that represents the count of non-null values.
     *
     * If there are no matching rows, it returns 0.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> count(
        distinct: Boolean,
        expr: Expression<T>,
    ): Expression<Long> = JpqlCount(distinct, expr)

    /**
     * Creates an expression that represents the maximum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> max(
        distinct: Boolean,
        expr: Expression<T>,
    ): Expression<T> = JpqlMax(distinct, expr)

    /**
     * Creates an expression that represents the minimum value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Comparable<*>> min(
        distinct: Boolean,
        expr: Expression<T>,
    ): Expression<T> = JpqlMin(distinct, expr)

    /**
     * Creates an expression that represents the average value.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @SinceJdsl("3.0.0")
    fun <T : Number> avg(
        distinct: Boolean,
        expr: Expression<T>,
    ): Expression<Double> = JpqlAvg(distinct, expr)

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum1")
    @SinceJdsl("3.0.0")
    fun sum(
        distinct: Boolean,
        expr: Expression<Int>,
    ): Expression<Long> = JpqlSum.IntSum(distinct, expr)

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum2")
    @SinceJdsl("3.0.0")
    fun sum(
        distinct: Boolean,
        expr: Expression<Long>,
    ): Expression<Long> = JpqlSum.LongSum(distinct, expr)

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum3")
    @SinceJdsl("3.0.0")
    fun sum(
        distinct: Boolean,
        expr: Expression<Float>,
    ): Expression<Double> = JpqlSum.FloatSum(distinct, expr)

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum4")
    @SinceJdsl("3.0.0")
    fun sum(
        distinct: Boolean,
        expr: Expression<Double>,
    ): Expression<Double> = JpqlSum.DoubleSum(distinct, expr)

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum5")
    @SinceJdsl("3.0.0")
    fun sum(
        distinct: Boolean,
        expr: Expression<BigInteger>,
    ): Expression<BigInteger> = JpqlSum.BigIntegerSum(distinct, expr)

    /**
     * Creates an expression that represents the sum of values.
     *
     * If there are no matching rows, or if all expressions are null, it returns null.
     */
    @JvmName("sum6")
    @SinceJdsl("3.0.0")
    fun sum(
        distinct: Boolean,
        expr: Expression<BigDecimal>,
    ): Expression<BigDecimal> = JpqlSum.BigDecimalSum(distinct, expr)

    /**
     * Creates an expression that represents the DTO projection.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> new(
        type: KClass<T>,
        args: Iterable<Expression<*>>,
    ): Expression<T> = JpqlNew(type, args)

    /**
     * Creates an expression that represents the result for the first predicate that is true.
     * If no predicate is true, the result after `ELSE` is returned, or null if there is no `ELSE`.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> caseWhen(
        whens: Map<Predicate, Expression<T>>,
        `else`: Expression<T>? = null,
    ): Expression<T> =
        JpqlCaseWhen(
            whens = whens,
            `else` = `else`,
        )

    /**
     * Creates an expression that represents the result for the first value = compareValue comparison that is true.
     * If no comparison is true, the result after `ELSE` is returned, or null if there is no `ELSE` part.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, V : Any> caseValue(
        value: Path<T>,
        whens: Map<Expression<T>, Expression<V>>,
        `else`: Expression<V>? = null,
    ): Expression<V> =
        JpqlCaseValue(
            value = value,
            whens = whens,
            `else` = `else`,
        )

    /**
     * Creates an expression that represents the first non-null value in the values,
     * or null if there are no non-null value.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> coalesce(
        value: Expression<T>,
        alternate: Expression<T>,
        others: Iterable<Expression<T>>,
    ): Expression<T> {
        val expr =
            listOf(
                value,
                alternate,
            ) + others

        return JpqlCoalesce(expr)
    }

    /**
     * Creates an expression that represents null if left = right is true, otherwise represents left.
     *
     * This is the same as ```CASE WHEN left = right THEN NULL ELSE left END```.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> nullIf(
        value: Expression<T>,
        compareValue: Expression<T>,
    ): Expression<T> = JpqlNullIf(value, compareValue)

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
    fun type(entity: Entity<*>): Expression<KClass<*>> = JpqlEntityType(entity)

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
    fun type(path: Path<*>): Expression<KClass<*>> = JpqlPathType(path)

    /**
     * Creates an expression that represents the concatenation of two or more string values.
     */
    @SinceJdsl("3.2.0")
    fun concat(
        value1: Expression<String>,
        value2: Expression<String>,
        others: Iterable<Expression<String>>,
    ): Expression<String> {
        val values =
            listOf(
                value1,
                value2,
            ) + others

        return JpqlConcat(values)
    }

    /**
     * Creates an expression that represents a substring of the specified length from the start position of the string.
     * If the length is not specified, it is returned from the start position of the string to the end of the string.
     * The first position of a string is 1.
     */
    @SinceJdsl("3.2.0")
    fun substring(
        value: Expression<String>,
        start: Expression<Int>,
        length: Expression<Int>? = null,
    ): Expression<String> = JpqlSubstring(value, start, length)

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trim(
        character: Expression<Char>? = null,
        value: Expression<String>,
    ): Expression<String> = JpqlTrim(character, value)

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the leading side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimLeading(
        character: Expression<Char>? = null,
        value: Expression<String>,
    ): Expression<String> = JpqlTrimLeading(character, value)

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the trailing side of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimTrailing(
        character: Expression<Char>? = null,
        value: Expression<String>,
    ): Expression<String> = JpqlTrimTrailing(character, value)

    /**
     * Creates an expression that represents a string with the specified characters all trimmed
     * from the both sides of the string.
     * If the character is not specified, it will be assumed to be whitespace.
     */
    @SinceJdsl("3.1.0")
    fun trimBoth(
        character: Expression<Char>? = null,
        value: Expression<String>,
    ): Expression<String> = JpqlTrimBoth(character, value)

    /**
     * Creates an expression that represents the string in uppercase.
     */
    @SinceJdsl("3.0.0")
    fun upper(value: Expression<String>): Expression<String> = JpqlUpper(value)

    /**
     * Creates an expression that represents the string in lowercase.
     */
    @SinceJdsl("3.0.0")
    fun lower(value: Expression<String>): Expression<String> = JpqlLower(value)

    /**
     * Creates an expression that represents the length of the string as an integer.
     */
    @SinceJdsl("3.0.0")
    fun length(value: Expression<String>): Expression<Int> = JpqlLength(value)

    /**
     * Creates an expression that represents the position of the first occurrence of a substring in a string.
     * If the substring is not found, returns 0.
     * The position starts with 1.
     */
    @SinceJdsl("3.0.0")
    fun locate(
        substring: Expression<String>,
        string: Expression<String>,
        start: Expression<Int>? = null,
    ): Expression<Int> = JpqlLocate(substring, string, start)

    /**
     * Creates an expression that represents the casting of a value to a different type.
     */
    @SinceJdsl("3.6.0")
    fun <T : Any> cast(
        value: Expression<*>,
        type: KClass<T>,
    ): Expression<T> = JpqlCast(value, type)

    /**
     * Creates an expression that returns the leftmost count characters from a string.
     */
    @SinceJdsl("3.6.0")
    fun left(
        value: Expression<String>,
        len: Expression<Int>,
    ): Expression<String> = JpqlLeft(value, len)

    /**
     * Creates an expression that returns the leftmost count characters from a string.
     */
    @SinceJdsl("3.6.0")
    fun left(
        value: Expression<String>,
        len: Int,
    ): Expression<String> = left(value, intLiteral(len))

    /**
     * Creates an expression that returns the rightmost count characters from a string.
     */
    @SinceJdsl("3.6.0")
    fun right(
        value: Expression<String>,
        len: Expression<Int>,
    ): Expression<String> = JpqlRight(value, len)

    /**
     * Creates an expression that replaces all occurrences of a search string with a replacement string.
     */
    @SinceJdsl("3.6.0")
    fun replace(
        value: Expression<String>,
        substring: Expression<String>,
        replacement: Expression<String>,
    ): Expression<String> = JpqlReplace(value, substring, replacement)

    /**
     * Creates an expression that represents predefined database functions and user-defined database functions.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> function(
        type: KClass<T>,
        name: String,
        args: Iterable<Expression<*>>,
    ): Expression<T> = JpqlFunctionExpression(type, name, args)

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
     * Expressions.customExpression(String::class, "CAST({0} AS VARCHAR)", listOf(Paths.path(User::age)))
     * ```
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> customExpression(
        type: KClass<T>,
        template: String,
        args: Iterable<Expression<*>>,
    ): Expression<T> = JpqlCustomExpression(type, template, args)

    /**
     * Creates a subquery with the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> subquery(selectQuery: SelectQuery<T>): Subquery<T> {
        val trimmed =
            if (selectQuery is JpqlSelectQuery) {
                JpqlSelectQuery(
                    returnType = selectQuery.returnType,
                    select = selectQuery.select,
                    distinct = selectQuery.distinct,
                    from = selectQuery.from,
                    where = selectQuery.where,
                    groupBy = selectQuery.groupBy,
                    having = selectQuery.having,
                    orderBy = null,
                )
            } else {
                selectQuery
            }

        return JpqlSubquery(
            selectQuery = trimmed,
        )
    }

    /**
     * Creates an aliased expression with the alias expression.
     * The aliased expression can be referenced by the alias expression.
     *
     * @see expression for creating an alias expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> alias(
        expr: Expression<T>,
        alias: Expression<T>,
    ): Expression<T> =
        if (expr is JpqlAliasedExpression) {
            JpqlAliasedExpression(expr.expr, alias)
        } else {
            JpqlAliasedExpression(expr, alias)
        }

    /**
     * Creates an expression to reference.
     * The expression can be used for aliasing and referencing.
     *
     * @see alias for aliasing an expression.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> expression(
        type: KClass<T>,
        alias: String,
    ): Expression<T> = JpqlExpression(type, alias)

    /**
     * Creates an expression that is enclosed in parentheses.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> parentheses(expr: Expression<T>): Expression<T> = JpqlExpressionParentheses(expr)

    /**
     * Creates an expression that represents the id of the entity.
     */
    @SinceJdsl("3.6.0")
    fun <ID : Any> id(entity: Expressionable<*>): Expression<ID> = JpqlId(entity)

    /**
     * Creates an expression that represents the version of the entity.
     */
    @SinceJdsl("3.6.0")
    fun <VERSION : Any> version(expr: Expressionable<*>): Expression<VERSION> = JpqlVersion(expr)
}
