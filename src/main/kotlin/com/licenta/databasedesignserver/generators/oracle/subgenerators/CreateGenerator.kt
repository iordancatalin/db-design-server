package com.licenta.databasedesignserver.generators.oracle.subgenerators

import com.licenta.databasedesignserver.enums.StatementType
import com.licenta.databasedesignserver.factories.Oracle12cStatementFactory
import com.licenta.databasedesignserver.interfaces.IGenerator
import com.licenta.databasedesignserver.model.Column
import com.licenta.databasedesignserver.model.Diagram
import com.licenta.databasedesignserver.model.Table
import com.licenta.databasedesignserver.response.GeneratedResponse
import com.licenta.databasedesignserver.util.Constants
import java.util.concurrent.CompletableFuture

class CreateGenerator : IGenerator {

    override fun generate(diagram: Diagram): CompletableFuture<GeneratedResponse> = CompletableFuture.supplyAsync {
        GeneratedResponse(generateScript(diagram, StatementType.Html), generateScript(diagram, StatementType.Text))
    }

    private fun generateScript(diagram: Diagram, statementType: StatementType): String {
        val builder = StringBuilder()

        diagram.tables.forEach { builder.append(generateTable(it, statementType)) }

        return builder.toString()
    }

    private fun generateTable(table: Table, statementType: StatementType): String {

        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        var newLine = statementLoader?.getStatement("style.new.line")

        var statement = statementLoader?.getStatement("create.table")

        if (statement !== null) {
            builder.append("${String.format(statement, table.name)}")
            builder.append(newLine)

//            statement = statementLoader?.getStatement("create.table.start")!!
//
//            builder.append(statement)
//            builder.append(newLine)

            table.columns
                    .forEachIndexed { index, column -> builder.append(generateColumn(column, statementType, index.equals(table.columns.size - 1))) }

            statement = statementLoader?.getStatement("create.table.end")!!

            builder.append(statement)
            builder.append(newLine)
        }

        builder.append(newLine)
        return builder.toString()
    }

    private fun generateColumn(column: Column, statementType: StatementType, lastColumn: Boolean): String {

        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        var newLine = statementLoader?.getStatement("style.new.line")

        if (newLine === null) {
            newLine = ""
        }

        var statement = statementLoader?.getStatement("create.column")
        if (statement !== null) {
            val type = if (Constants.CHAR_TYPES.contains(column.type)) {
                column.type + "(255)"
            } else {
                column.type
            }

            builder.append(String.format(statement, column.name, type))

            if (column.defaultValue !== null) {
                statement = statementLoader?.getStatement("create.column.default")
                if (statement !== null) {
                    builder.append(" ${String.format(statement, column.defaultValue)}")
                }
            }

            if (!column.nullable && !column.primaryKey && !column.autoincrement) {
                statement = statementLoader?.getStatement("create.column.not.null")
                if (statement !== null) {
                    builder.append(" ${statement}")
                }
            }

            if (column.autoincrement) {
                statement = statementLoader?.getStatement("create.column.autoincrement")
                if (statement !== null) {
                    builder.append(" ${statement}")
                }
            }

            if (!lastColumn) {
                statement = statementLoader?.getStatement("create.column.end")
                if (statement !== null) {
                    builder.append(statement)
                }
            }

            builder.append(newLine)
        }

        return builder.toString()
    }

}