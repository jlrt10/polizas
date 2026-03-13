package com.example.polizas.service;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.example.polizas.repository.RiesgoRepository;
import com.example.polizas.model.Riesgo;
import com.example.polizas.dto.CoreEventoDTO;

@Service
public class CoreMockService {

    public ResponseEntity<String> enviarEvento(String apiKey, CoreEventoDTO dto) {

        if (!"123456".equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println("Evento enviado al CORE -> " +
                dto.getEvento() +
                " Poliza: " +
                dto.getPolizaId());

        return ResponseEntity.ok("Evento procesado");

    }

}
