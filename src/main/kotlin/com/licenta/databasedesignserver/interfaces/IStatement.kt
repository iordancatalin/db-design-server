package com.licenta.databasedesignserver.interfaces

interface IStatement {
    fun getStatement(key: String): String?
}