package com.licenta.databasedesignserver.model

import com.fasterxml.jackson.annotation.JsonAlias

data class Column(@JsonAlias("_id") val id: String,
                  @JsonAlias("_name") val name: String,
                  @JsonAlias("_type") val type: String,
                  @JsonAlias("_nullable") val nullable: Boolean,
                  @JsonAlias("_primaryKey") val primaryKey: Boolean,
                  @JsonAlias("_foreignKey") val foreignKey: Boolean,
                  @JsonAlias("_autoincrement") val autoincrement: Boolean,
                  @JsonAlias("_comment") val comment: String?,
                  @JsonAlias("_defaultValue") val defaultValue: String?,
                  @JsonAlias("_unique") val unique: Boolean) {

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (!(other is Column)) {
            return false
        }

        return id === other.id
    }
}