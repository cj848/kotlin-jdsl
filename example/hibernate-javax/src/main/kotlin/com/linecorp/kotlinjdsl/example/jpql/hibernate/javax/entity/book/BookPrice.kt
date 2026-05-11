package com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.book

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class BookPrice(
    @Column(name = "price", scale = 2)
    val value: BigDecimal,
)

fun BookPrice(int: Int): BookPrice = BookPrice(BigDecimal.valueOf(int.toLong()).setScale(2))

fun BookPrice(double: Double): BookPrice = BookPrice(BigDecimal.valueOf(double).setScale(2))
