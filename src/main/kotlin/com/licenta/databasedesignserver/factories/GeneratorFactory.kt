package com.licenta.databasedesignserver.factories

import com.licenta.databasedesignserver.enums.DatabaseType
import com.licenta.databasedesignserver.exceptions.GeneratorNotFoundException
import com.licenta.databasedesignserver.generators.oracle.Oracle12cGenerator

class GeneratorFactory {

    companion object {
        fun getGenerator(database: DatabaseType) = when (database) {
            DatabaseType.Oracle12c -> Oracle12cGenerator()
            else -> {
                throw GeneratorNotFoundException("No generator was found for $database")
            }
        }
    }
}