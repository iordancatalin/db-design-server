package com.licenta.databasedesignserver.model

import com.fasterxml.jackson.annotation.JsonAlias

data class Relation(@JsonAlias("_childColumnId") val childColumnId: String,
                    @JsonAlias("_parentColumnId") val parentColumnId: String)