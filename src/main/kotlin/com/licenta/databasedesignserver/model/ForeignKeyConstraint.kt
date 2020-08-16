package com.licenta.databasedesignserver.model

import com.fasterxml.jackson.annotation.JsonAlias

data class ForeignKeyConstraint(@JsonAlias("_id") val id: String,
                                @JsonAlias("_name") val name: String,
                                @JsonAlias("_childTableId") val childTableId: String,
                                @JsonAlias("_parentTableId") val parentTableId: String,
                                @JsonAlias("_comment") val comment: String?,
                                @JsonAlias("_relations") val relations: Array<Relation>)