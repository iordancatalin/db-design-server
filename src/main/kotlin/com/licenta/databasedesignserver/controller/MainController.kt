package com.licenta.databasedesignserver.controller

import com.licenta.databasedesignserver.enums.DatabaseType
import com.licenta.databasedesignserver.model.Diagram
import com.licenta.databasedesignserver.services.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api")
class MainController @Autowired constructor(val mainService: MainService) {

    @PostMapping("/generate/{database}")
    fun requestGenerate(@PathVariable database: DatabaseType, @RequestBody payload: Diagram) = mainService.generate(database, payload)

    @GetMapping("/dummy")
    fun dummy() = "It's working"
}