package com.example.polizas.service;

import org.springframework.stereotype.Service;

import com.example.polizas.repository.RiesgoRepository;
import com.example.polizas.model.Riesgo;

@Service
public class RiesgoService {

    private final RiesgoRepository repository;

    public RiesgoService(RiesgoRepository repository) {
        this.repository = repository;
    }

    public void cancelar(Long id) {

        Riesgo riesgo = repository.findById(id)
                .orElseThrow();

        riesgo.setActivo(false);

        repository.save(riesgo);
    }
}
