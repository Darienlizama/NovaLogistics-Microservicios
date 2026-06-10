package com.ms_operaciones.ms_operaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms_operaciones.ms_operaciones.model.Envio;
import com.ms_operaciones.ms_operaciones.model.Seguimiento;

import java.util.List;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long > {
    // Buscar historial por el objeto envio
    List<Seguimiento> findByEnvio(Envio envio);

    // Buscar historial directamente por el ID del envío (Muy útil)
    List<Seguimiento> findByEnvioId(Long envioId);
}
