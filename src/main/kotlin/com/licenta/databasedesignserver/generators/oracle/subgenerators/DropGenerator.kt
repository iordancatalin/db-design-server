package com.licenta.databasedesignserver.generators.oracle.subgenerators

import com.licenta.databasedesignserver.enums.StatementType
import com.licenta.databasedesignserver.factories.Oracle12cStatementFactory
import com.licenta.databasedesignserver.interfaces.IGenerator
import com.licenta.databasedesignserver.model.Diagram
import com.licenta.databasedesignserver.response.GeneratedResponse
import java.lang.StringBuilder
import java.util.concurrent.CompletableFuture

class DropGenerator : IGenerator {
    override fun generate(diagram: Diagram): CompletableFuture<GeneratedResponse>
            = CompletableFuture.supplyAsync { GeneratedResponse(generateScript(diagram, StatementType.Html), generateScript(diagram, StatementType.Text)) }

    private fun generateScript(diagram: Diagram, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        val newLine = statementLoader?.getStatement("style.new.line")

        var statement = statementLoader?.getStatement("drop.foreign.key.constraint")
        if (statement !== null) {
            diagram.foreignKeys.forEach {
                val tableName: String? = diagram.getTable(it.childTableId)?.name

                if (tableName !== null) {
                    builder.append(String.format(statement!!, tableName, it.name))
                    if (newLine !== null) {
                        builder.append(newLine)
                    }
                }
            }
        }

        if (newLine !== null) {
            builder.append(newLine)
        }

        statement = statementLoader?.getStatement("drop.table")
        if (statement !== null) {
            diagram.tables.forEach {
                builder.append(String.format(statement, it.name))
                if (newLine !== null) {
                    builder.append(newLine)
                }
            }
        }

        if (newLine !== null) {
            builder.append(newLine)
        }

        return builder.toString()
    }
}