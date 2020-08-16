package com.licenta.databasedesignserver.model

import com.fasterxml.jackson.annotation.JsonAlias

data class CheckConstraint(@JsonAlias("_id") val id: String,
                           @JsonAlias("_name") val name: String?,
                           @JsonAlias("_expression") val expression: String) {
    override fun equals(other: Any?): Boolean {
        if (!(other is CheckConstraint)) {
            return false
        }

        return id === other.id
    }
}