package com.licenta.databasedesignserver.generators.oracle.subgenerators

import com.licenta.databasedesignserver.enums.StatementType
import com.licenta.databasedesignserver.factories.Oracle12cStatementFactory
import com.licenta.databasedesignserver.interfaces.IGenerator
import com.licenta.databasedesignserver.model.*
import com.licenta.databasedesignserver.response.GeneratedResponse
import com.licenta.databasedesignserver.util.Common
import java.util.concurrent.CompletableFuture


class AlterGenerator : IGenerator {

    override fun generate(diagram: Diagram): CompletableFuture<GeneratedResponse> = CompletableFuture.supplyAsync {
        GeneratedResponse(generateScript(diagram, StatementType.Html), generateScript(diagram, StatementType.Text))
    }

    private fun generateScript(diagram: Diagram, statementType: StatementType): String {
        val builder = StringBuilder()

        builder.append(generatePrimaryKeys(diagram, statementType))
                .append(generateForeignKeys(diagram, statementType))
                .append(generateCheckConstraints(diagram, statementType))
                .append(generateUniqueConstraints(diagram, statementType))

        return builder.toString()
    }

    private fun generatePrimaryKeys(diagram: Diagram, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        val newLine = statementLoader?.getStatement("style.new.line")

        diagram.tables.forEach { builder.append(generatePrimaryKey(it, statementType)).append(newLine).append(newLine) }

        return builder.toString()
    }

    private fun generatePrimaryKey(table: Table, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)

        var statement = statementLoader?.getStatement("alter.primary.key.start")
        val keyName = if (table.primaryKeyName === null || table.primaryKeyName.isEmpty() || table.primaryKeyName.isBlank()) {
            Common.createUUID()
        } else {
            table.primaryKeyName
        }

        if (statement !== null) {
            builder.append(String.format(statement, table.name, keyName))
            builder.append(table.columns.filter { it.primaryKey }.map { it.name }.joinToString())

            statement = statementLoader?.getStatement("alter.primary.key.end")
            builder.append(statement)
        }

        return builder.toString()
    }

    private fun generateForeignKeys(diagram: Diagram, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        val newLine = statementLoader?.getStatement("style.new.line")

        diagram.foreignKeys.forEach { builder.append(generateForeignKey(diagram, it, statementType)).append(newLine).append(newLine) }

        return builder.toString()
    }

    private fun generateForeignKey(diagram: Diagram, foreignKeyConstraint: ForeignKeyConstraint, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)

        var statement = statementLoader?.getStatement("alter.foreign.key.start")
        val childTable = diagram.getTable(foreignKeyConstraint.childTableId)
        val parentTable = diagram.getTable(foreignKeyConstraint.parentTableId)

        if (statement !== null) {
            builder.append(String.format(statement, childTable?.name, foreignKeyConstraint.name))
        }

        builder.append(foreignKeyConstraint.relations.map { childTable?.getColumn(it.childColumnId)?.name }.joinToString())

        statement = statementLoader?.getStatement("alter.foreign.key.end")
        if (statement !== null) {
            builder.append(String.format(statement, parentTable?.name))
        }

        builder.append(foreignKeyConstraint.relations.map { parentTable?.getColumn(it.parentColumnId)?.name }.joinToString())

        statement = statementLoader?.getStatement("alter.foreign.key.final")
        if (statement !== null) {
            builder.append(statement)
        }

        return builder.toString()
    }

    private fun generateCheckConstraints(diagram: Diagram, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        val newLine = statementLoader?.getStatement("style.new.line")

        diagram.tables.forEach { table ->
            table.checkConstraints
                    .forEach { builder.append(generateCheckConstraint(table.name, it, statementType)).append(newLine).append(newLine) }
        }

        return builder.toString()
    }

    private fun generateCheckConstraint(tableName: String, checkConstraint: CheckConstraint, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)

        var statement = statementLoader?.getStatement("alter.check.constraint")
        val constraintName = if (checkConstraint.name === null || checkConstraint.name.isEmpty() ||checkConstraint.name.isBlank()) {
            Common.createUUID()
        } else {
            checkConstraint.name
        }

        if (statement !== null) {
            builder.append(String.format(statement, tableName, constraintName, checkConstraint.expression))
        }

        return builder.toString()
    }

    private fun generateUniqueConstraints(diagram: Diagram, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)
        val newLine = statementLoader?.getStatement("style.new.line")

        diagram.tables.forEach { table ->
            table.uniqueConstraints.forEach {
                builder.append(generateUniqueConstraint(table.name, it, statementType)).append(newLine).append(newLine)
            }
        }

        return builder.toString()
    }

    private fun generateUniqueConstraint(tableName: String, uniqueConstraint: UniqueConstraint, statementType: StatementType): String {
        val builder = StringBuilder()
        val statementLoader = Oracle12cStatementFactory.getStatement(statementType)

        var statement = statementLoader?.getStatement("alter.unique.key.start")
        val constraintName = if (uniqueConstraint.name === null || uniqueConstraint.name.isEmpty() || uniqueConstraint.name.isBlank()) {
            Common.createUUID()
        } else {
            uniqueConstraint.name
        }

        if (statement !== null) {
            builder.append(String.format(statement, tableName, constraintName))

            builder.append(uniqueConstraint.columns.joinToString())

            builder.append(statementLoader?.getStatement("alter.unique.key.end"))
        }

        return builder.toString()
    }
}