package com.linecorp.kotlinjdsl.support.spring.data.jpa.repository

import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.Application
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.author.AuthorRepository
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.book.BookRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(classes = [Application::class])
@Transactional
class KotlinJdslJpqlExecutorProxyTest {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun `test proxy executor works`() {
        bookRepository.findAll {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            )
        }
    }

    @Test
    fun `test proxy with pagination`() {
        val pageable = PageRequest.of(0, 10)
        bookRepository.findPage(pageable) {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            )
        }
    }

    @Test
    fun `test multiple proxies in single test`() {
        bookRepository.findAll {
            select(
                path(Book::isbn),
            ).from(
                entity(Book::class),
            )
        }
        authorRepository.findAll {
            select(
                path(Author::authorId),
            ).from(
                entity(Author::class),
            )
        }
    }
}
