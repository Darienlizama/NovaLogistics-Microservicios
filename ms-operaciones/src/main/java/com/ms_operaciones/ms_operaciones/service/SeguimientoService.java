package com.ms_operaciones.ms_operaciones.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms_operaciones.ms_operaciones.DTO.SeguimientoDTO;
import com.ms_operaciones.ms_operaciones.model.Seguimiento;
import com.ms_operaciones.ms_operaciones.repository.EnvioRepository;
import com.ms_operaciones.ms_operaciones.repository.SeguimientoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SeguimientoService {
    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private EnvioRepository envioRepository; 

    @Transactional
    public Seguimiento agregarSeguimiento(Seguimiento seguimiento){
        log.info("Agregando nuevo estado de seguimiento...");
        
        // CORRECCIÓN: Si NO existe el envío, lanzamos el error
        if(!envioRepository.existsById(seguimiento.getEnvio().getId())){
            throw new RuntimeException("Error: El envío asignado al seguimiento no existe.");
        }
        
        return seguimientoRepository.save(seguimiento);
    }

    @Transactional
    public void eliminarSeguimiento(Long id){
        // CORRECCIÓN: Si NO existe el ID, lanzamos el error
        if(!seguimientoRepository.existsById(id)){
            throw new RuntimeException("No se puede eliminar: Seguimiento no encontrado con el ID: "+id);
        }
        seguimientoRepository.deleteById(id);
        log.info("Seguimiento eliminado con éxito");
    }

    public List<Seguimiento> listarSeguimiento(){
        return seguimientoRepository.findAll();
    }

    public SeguimientoDTO convertirDto(Seguimiento seguimiento) {
        SeguimientoDTO dto = new SeguimientoDTO();
        dto.setId(seguimiento.getId());
        
        if (seguimiento.getEnvio() != null) {
            dto.setNumeroGuia(seguimiento.getEnvio().getNumeroGuia());
        }
        
        dto.setEstado(seguimiento.getEstado());
        dto.setUbicacion(seguimiento.getUbicacion());
        dto.setFecha_hora(seguimiento.getFecha_hora());
        
        return dto;
    }   

    public SeguimientoDTO buscarPorId(Long id){
        log.info("Buscando seguimiento por id: {}", id);
        Seguimiento seguimiento = seguimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado con el ID: " + id));
        
        return convertirDto(seguimiento);
    }

    @Transactional
    public Seguimiento actualizarSeguimiento(Long id, Seguimiento datosNuevos) {
        log.info("Actualizando registro de seguimiento ID: {}", id);

        Seguimiento seguimientoExistente = seguimientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede actualizar: Registro de seguimiento no encontrado con ID: " + id));

        seguimientoExistente.setEstado(datosNuevos.getEstado());
        seguimientoExistente.setUbicacion(datosNuevos.getUbicacion());

        return seguimientoRepository.save(seguimientoExistente);
    }
}