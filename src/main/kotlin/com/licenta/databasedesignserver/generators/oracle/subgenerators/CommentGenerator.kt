package com.licenta.databasedesignserver.generators.oracle.subgenerators

import com.licenta.databasedesignserver.enums.StatementType
import com.licenta.databasedesignserver.factories.Oracle12cStatementFactory
import com.licenta.databasedesignserver.interfaces.IGenerator
import com.licenta.databasedesignserver.model.Column
import com.licenta.databasedesignserver.model.Diagram
import com.licenta.databasedesignserver.model.Table
import com.licenta.databasedesignserver.response.GeneratedResponse
import java.util.concurrent.CompletableFuture

class CommentGenerator : IGenerator {
    override fun generate(diagram: Diagram): CompletableFuture<GeneratedResponse> = CompletableFuture.supplyAsync {
        GeneratedResponse(generateScript(diagram, StatementType.Html), generateScript(diagram, StatementType.Text))
    }

    private fun generateScript(diagram: Diagram, statementType: StatementType): String {
        val builder = StringBuilder()

        diagram.tables.forEach { builder.append(generateTableComment(it, statementType)) }

        return builder.toString()
    }

    private fun generateTableComment(table: Table, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        var newLine = statementLoader?.getStatement("style.new.line")

        if (newLine === null) {
            newLine = ""
        }

        if (table.comment !== null && table.comment.isNotEmpty() && table.comment.isNotBlank()) {
            val statement = statementLoader?.getStatement("comment.table")


            if (statement !== null) {
                builder.append(String.format(statement, table.name, table.comment)).append(newLine)
            }
        }

        table.columns.filter { it.comment !== null && it.comment.isNotEmpty() && it.comment.isNotBlank() }
                .forEach { builder.append(generateColumnComment(table.name, it, statementType)).append(newLine) }

        return builder.toString()
    }

    private fun generateColumnComment(tableName: String, column: Column, statementType: StatementType): String {
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        val statement = statementLoader?.getStatement("comment.column")

        if (statement !== null) {
            return String.format(statement, "$tableName.${column.name}", column.comment)
        }

        return ""
    }
}