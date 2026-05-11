package com.linecorp.kotlinjdsl.benchmark.sample.query.select

import com.linecorp.kotlinjdsl.benchmark.sample.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

object SelectQuery1 : () -> SelectQuery<*> {
    override fun invoke(): SelectQuery<*> =
        jpql {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
                join(Book::authors),
            ).groupBy(
                path(Book::isbn),
            ).orderBy(
                count(Book::isbn).desc(),
            )
        }
}
