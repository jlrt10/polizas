package com.example.polizas.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.example.polizas.repository.PolizaRepository;
import com.example.polizas.repository.RiesgoRepository;
import com.example.polizas.model.Poliza;
import com.example.polizas.model.TipoPoliza;
import com.example.polizas.model.EstadoPoliza;
import com.example.polizas.model.Riesgo;

@Service
public class PolizaService {

    private final PolizaRepository polizaRepository;
    private final RiesgoRepository riesgoRepository;

    private static final double IPC = 0.10;

    public PolizaService(PolizaRepository polizaRepository, RiesgoRepository riesgoRepository) {
        this.polizaRepository = polizaRepository;
        this.riesgoRepository = riesgoRepository;
    }

    public List<Poliza> listar(TipoPoliza tipo, EstadoPoliza estado) {
        return polizaRepository.findByTipoAndEstado(tipo, estado);
    }

    public List<Riesgo> riesgos(Long polizaId) {
        return riesgoRepository.findByPolizaId(polizaId);
    }

    public Poliza renovar(Long id) {

        Poliza poliza = polizaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poliza no encontrada"));

        if (poliza.getEstado() == EstadoPoliza.CANCELADA) {
            throw new RuntimeException("No se puede renovar una póliza cancelada");
        }

        poliza.setCanon(poliza.getCanon() * (1 + IPC));
        poliza.setPrima(poliza.getPrima() * (1 + IPC));
        poliza.setEstado(EstadoPoliza.RENOVADA);

        return polizaRepository.save(poliza);
    }

    public void cancelar(Long id) {

        Poliza poliza = polizaRepository.findById(id)
                .orElseThrow();

        poliza.setEstado(EstadoPoliza.CANCELADA);

        poliza.getRiesgos().forEach(r -> r.setActivo(false));

        polizaRepository.save(poliza);
    }

    public Riesgo agregarRiesgo(Long polizaId, Riesgo riesgo) {

        Poliza poliza = polizaRepository.findById(polizaId)
                .orElseThrow();

        if (poliza.getTipo() == TipoPoliza.INDIVIDUAL &&
                poliza.getRiesgos().size() >= 1) {
            throw new RuntimeException("Poliza individual solo permite 1 riesgo");
        }

        if (poliza.getTipo() != TipoPoliza.COLECTIVA) {
            throw new RuntimeException("Solo polizas colectivas pueden agregar riesgos");
        }

        riesgo.setPoliza(poliza);

        return riesgoRepository.save(riesgo);
    }

}
