package com.licenta.databasedesignserver.generators.oracle

import com.licenta.databasedesignserver.generators.oracle.subgenerators.AlterGenerator
import com.licenta.databasedesignserver.generators.oracle.subgenerators.CommentGenerator
import com.licenta.databasedesignserver.generators.oracle.subgenerators.CreateGenerator
import com.licenta.databasedesignserver.generators.oracle.subgenerators.DropGenerator
import com.licenta.databasedesignserver.interfaces.IGenerator
import com.licenta.databasedesignserver.model.Diagram
import com.licenta.databasedesignserver.response.GeneratedResponse
import java.util.concurrent.CompletableFuture

class Oracle12cGenerator : IGenerator {

    override fun generate(diagram: Diagram): CompletableFuture<GeneratedResponse> {
        val list = arrayListOf(DropGenerator().generate(diagram),
                CreateGenerator().generate(diagram),
                AlterGenerator().generate(diagram),
                CommentGenerator().generate(diagram))
                .map { it.join() }

        val htmlBuilder = StringBuilder()
        val textBuilder = StringBuilder()

        list.forEach {
            htmlBuilder.append(it.htmlScript)
            textBuilder.append(it.textScript)
        }

        return CompletableFuture.supplyAsync { GeneratedResponse(textScript = textBuilder.toString(), htmlScript = htmlBuilder.toString()) }
    }

}