package com.example.polizas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import com.example.polizas.model.Poliza;
import com.example.polizas.model.TipoPoliza;
import com.example.polizas.model.EstadoPoliza;
import com.example.polizas.model.Riesgo;

import com.example.polizas.service.PolizaService;

@RestController
@RequestMapping("/polizas")
public class PolizaController {

    private final PolizaService service;

    public PolizaController(PolizaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Poliza> listar(
            @RequestParam TipoPoliza tipo,
            @RequestParam EstadoPoliza estado) {

        return service.listar(tipo, estado);
    }

    @GetMapping("/{id}/riesgos")
    public List<Riesgo> riesgos(@PathVariable Long id) {
        return service.riesgos(id);
    }

    @PostMapping("/{id}/renovar")
    public Poliza renovar(@PathVariable Long id) {
        return service.renovar(id);
    }

    @PostMapping("/{id}/cancelar")
    public void cancelar(@PathVariable Long id) {
        service.cancelar(id);
    }

    @PostMapping("/{id}/riesgos")
    public Riesgo agregar(@PathVariable Long id, @RequestBody Riesgo riesgo) {
        return service.agregarRiesgo(id, riesgo);
    }

}
