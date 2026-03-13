package com.example.polizas.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.polizas.repository.PolizaRepository;
import com.example.polizas.model.Poliza;
import com.example.polizas.model.TipoPoliza;
import com.example.polizas.model.EstadoPoliza;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Long> {

    List<Poliza> findByTipoAndEstado(TipoPoliza tipo, EstadoPoliza estado);

}
