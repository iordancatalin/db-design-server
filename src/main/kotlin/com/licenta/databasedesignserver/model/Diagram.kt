package com.licenta.databasedesignserver.model

import com.fasterxml.jackson.annotation.JsonAlias
import java.util.*

data class Diagram(@JsonAlias("_id") val id: String,
                   @JsonAlias("_name") val name: String,
                   @JsonAlias("_creationDate") val creationDate: Date,
                   @JsonAlias("_owner") val owner: String,
                   @JsonAlias("_foreignKeys") val foreignKeys: Array<ForeignKeyConstraint>,
                   @JsonAlias("_tables") val tables: Array<Table>) {

    fun getTable(id: String) = tables.firstOrNull { it.id.equals(id) }
}