package com.example.polizas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import com.example.polizas.dto.CoreEventoDTO;

import com.example.polizas.service.CoreMockService;

@RestController
@RequestMapping("/core-mock")
public class CoreMockController {

    private final CoreMockService service;

    public CoreMockController(CoreMockService service) {
        this.service = service;
    }

    @PostMapping("/evento")
    public ResponseEntity<String> enviarEvento(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody CoreEventoDTO dto) {

        return service.enviarEvento(apiKey, dto);
    }
}
