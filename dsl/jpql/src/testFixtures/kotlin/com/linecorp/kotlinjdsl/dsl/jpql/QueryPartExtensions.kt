package com.linecorp.kotlinjdsl.dsl.jpql

inline fun <T> queryPart(init: Jpql.() -> T): T = Jpql().init()
