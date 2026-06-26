package com.ms_operaciones.ms_operaciones.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms_operaciones.ms_operaciones.DTO.PaqueteDTO;
import com.ms_operaciones.ms_operaciones.model.Paquete;
import com.ms_operaciones.ms_operaciones.repository.PaqueteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaqueteService {

    @Autowired
    private PaqueteRepository paqueteRepository;

    @Transactional
    public Paquete guardarPaquetes(Paquete paquete){
        log.info("Guardando Paquete: {}", paquete.getDescripcion());
        return paqueteRepository.save(paquete);
    }
    
    @Transactional(readOnly = true)
    public List<Paquete> totalPaquetes(){
        return paqueteRepository.findAll();
    }

    @Transactional
    public void eliminarPaquetes(Long id){
        if (!paqueteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Paquete no encontrado con el ID: " + id);
        }
        paqueteRepository.deleteById(id);
        log.info("Paquete con ID {} eliminado exitosamente", id);
    }

    public PaqueteDTO convertirDTO(Paquete paquete){
        PaqueteDTO dto = new PaqueteDTO();
        
        dto.setId(paquete.getId()); 
        dto.setDescripcion(paquete.getDescripcion());
        dto.setPeso_kg(paquete.getPeso_kg());
        dto.setEs_fragil(paquete.getEs_fragil());
        return dto;
    }

    @Transactional(readOnly = true)
    public Paquete buscarPorId(Long id){
        return paqueteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paquete no encontrado con el ID: " + id));
       
    }

    @Transactional
    public Paquete actualizarPaquete(Long id, Paquete datosNuevos) {
        Paquete paqueteExistente = paqueteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paquete no encontrado con ID: " + id));

        paqueteExistente.setDescripcion(datosNuevos.getDescripcion());
        paqueteExistente.setPeso_kg(datosNuevos.getPeso_kg());
        paqueteExistente.setEs_fragil(datosNuevos.getEs_fragil());

        return paqueteRepository.save(paqueteExistente);
    }
}