package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Avg
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import org.junit.jupiter.api.Test

class AvgDslTest : AbstractJpqlDslTest() {
    @Test
    fun `avg expression`() {
        // when
        val expression = testJpql {
            avg(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Avg(
            Field<Int?>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg expression distinct true`() {
        // when
        val expression = testJpql {
            avg(path(TestTable::int1), distinct = true)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Avg(
            Field<Int?>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg expression distinct false`() {
        // when
        val expression = testJpql {
            avg(path(TestTable::int1), distinct = false)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Avg(
            Field<Int?>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg nullable expression`() {
        // when
        val expression = testJpql {
            avg(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Avg(
            Field<Int?>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg nullable expression distinct true`() {
        // when
        val expression = testJpql {
            avg(path(TestTable::nullableInt1), distinct = true)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Avg(
            Field<Int?>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg nullable expression distinct false`() {
        // when
        val expression = testJpql {
            avg(path(TestTable::nullableInt1), distinct = false)
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Avg(
            Field<Int?>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            distinct = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avgDistinct expression`() {
        // when
        val expression = testJpql {
            avgDistinct(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Avg(
            Field<Int?>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avgDistinct nullable expression`() {
        // when
        val expression = testJpql {
            avgDistinct(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Double?> = expression // for type check

        // then
        val expected = Avg(
            Field<Int?>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            distinct = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}
