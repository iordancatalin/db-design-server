package com.licenta.databasedesignserver.model

import com.fasterxml.jackson.annotation.JsonAlias

class UniqueConstraint(@JsonAlias("_id") val id: String,
                       @JsonAlias("_name") val name: String?,
                       @JsonAlias("_columns") val columns: Array<String>) {

    override fun equals(other: Any?): Boolean {
        if (!(other is UniqueConstraint)) {
            return false
        }

        return id === other.id
    }
}