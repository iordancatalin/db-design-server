package com.licenta.databasedesignserver.interfaces

import com.licenta.databasedesignserver.model.Diagram
import com.licenta.databasedesignserver.response.GeneratedResponse
import java.util.concurrent.CompletableFuture

interface IGenerator {
    fun generate(diagram: Diagram): CompletableFuture<GeneratedResponse>
}