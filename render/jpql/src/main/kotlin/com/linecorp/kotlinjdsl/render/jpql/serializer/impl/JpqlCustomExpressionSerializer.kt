package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCustomExpression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.support.JpqlTemplateSerializerSupport
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlCustomExpressionSerializer :
    JpqlTemplateSerializerSupport(),
    JpqlSerializer<JpqlCustomExpression<*>> {
    override fun handledType(): KClass<JpqlCustomExpression<*>> = JpqlCustomExpression::class

    override fun serialize(
        part: JpqlCustomExpression<*>,
        writer: JpqlWriter,
        context: RenderContext,
    ) {
        serialize(part.template, part.args, writer, context)
    }
}
