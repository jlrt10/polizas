package com.example.polizas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.polizas.service.RiesgoService;

@RestController
@RequestMapping("/riesgos")
public class RiesgoController {

    private final RiesgoService service;

    public RiesgoController(RiesgoService service) {
        this.service = service;
    }

    @PostMapping("/{id}/cancelar")
    public void cancelar(@PathVariable Long id) {
        service.cancelar(id);
    }
}
