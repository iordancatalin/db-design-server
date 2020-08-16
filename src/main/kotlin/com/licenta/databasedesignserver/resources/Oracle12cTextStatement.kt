package com.licenta.databasedesignserver.resources

import com.licenta.databasedesignserver.interfaces.IStatement
import java.util.*

object Oracle12cTextStatement : IStatement {

    private val properties: Properties = Properties()

    init {
        properties.load(Oracle12cHtmlStatement::class.java.getResourceAsStream("/database/Oracle12c/text_format.properties"))
    }

    override fun getStatement(key: String): String? = properties.getProperty(key)
}