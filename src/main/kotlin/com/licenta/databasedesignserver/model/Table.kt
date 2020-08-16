package com.licenta.databasedesignserver.model

import com.fasterxml.jackson.annotation.JsonAlias

data class Table(@JsonAlias("_id") val id: String,
                 @JsonAlias("_name") val name: String,
                 @JsonAlias("_primaryKeyName") val primaryKeyName: String?,
                 @JsonAlias("_comment") val comment: String?,
                 @JsonAlias("_columns") val columns: Array<Column>,
                 @JsonAlias("_checkConstraints") val checkConstraints: Array<CheckConstraint>,
                 @JsonAlias("_uniqueConstraints") val uniqueConstraints: Array<UniqueConstraint>) {

    fun getColumn(id: String) = columns.firstOrNull { it.id.equals(id) }
}