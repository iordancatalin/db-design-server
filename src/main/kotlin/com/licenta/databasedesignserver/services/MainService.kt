package com.licenta.databasedesignserver.services

import com.licenta.databasedesignserver.enums.DatabaseType
import com.licenta.databasedesignserver.factories.GeneratorFactory
import com.licenta.databasedesignserver.model.Diagram
import org.springframework.stereotype.Service

@Service
class MainService {

    fun generate(database: DatabaseType, diagram: Diagram) = GeneratorFactory.getGenerator(database).generate(diagram)
}