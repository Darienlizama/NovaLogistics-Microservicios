package com.ms_operaciones.ms_operaciones.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms_operaciones.ms_operaciones.DTO.EnvioDTO;
import com.ms_operaciones.ms_operaciones.model.Envio;
import com.ms_operaciones.ms_operaciones.repository.EnvioRepository;
import com.ms_operaciones.ms_operaciones.repository.PaqueteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;

    

    @Autowired
    private PaqueteRepository paqueteRepository;

    public Envio guardarEnvio(Envio envio){
        log.info("Guardando envio...");


        // Validar Cliente
        //if (!clienteRepository.existsById(envio.getCliente().getId())) {
          //  throw new RuntimeException("Error: El cliente asignado al envío no existe.");
        //}

        // Validar Paquete
        if (!paqueteRepository.existsById(envio.getPaquete().getId())) {
            throw new RuntimeException("Error: El paquete asignado al envío no existe.");
        }

        // Validar precio
        if (envio.getPrecio() == null || envio.getPrecio() <= 0) {
            throw new RuntimeException("El precio del envío debe ser mayor a 0");
        }
        
        return envioRepository.save(envio);
    }

    public List<Envio>listaEnvios(){
        return envioRepository.findAll();
    }

    public void eliminarEnvio(Long id){
        if(!envioRepository.existsById(id)){
            throw new RuntimeException("No se puede eliminar:Envio no encontrado con el ID: "+id);
        }

        envioRepository.deleteById(id);
        log.info("Envio eliminado con exito");
    
    }

    public EnvioDTO convertirDTO(Envio envio){
        EnvioDTO dto = new EnvioDTO();
        dto.setId(envio.getId());
        dto.setNumeroGuia(envio.getNumeroGuia());
        
        //if (envio.getCliente() != null) {
            //dto.setNombreCliente(envio.getCliente().getNombre());
          //  dto.setRutCliente(envio.getCliente().getRut());
        //}
        
        if (envio.getPaquete() != null) {
            dto.setDescripcionPaquete(envio.getPaquete().getDescripcion());
            dto.setPesoPaquete(envio.getPaquete().getPeso_kg());
        }
        
        dto.setDireccionDestino(envio.getDireccionDestino());
        dto.setCiudadDestino(envio.getCiudadDestino());
        dto.setPrecio(envio.getPrecio());
        dto.setFecha(envio.getFecha());
        return dto;
    }
    
    public EnvioDTO buscarPorId(Long id){
        log.info("Buscando Envio por id...");
        
        Envio envio = envioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Envio no encontrado con ID: " + id));
        
        return convertirDTO(envio);

    }

    public Envio actualizarEnvio(Long id, Envio datosNuevos) {
    log.info("Actualizando envio con ID: {}", id);

     //Buscamos si existe 
    Envio envioNuevo= envioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No se puede actualizar: Envio no encontrado con ID: " + id));

    //envioNuevo.setCliente(datosNuevos.getCliente());
    envioNuevo.setPaquete(datosNuevos.getPaquete());
    envioNuevo.setDireccionDestino(datosNuevos.getDireccionDestino());
    envioNuevo.setCiudadDestino(datosNuevos.getCiudadDestino());
    envioNuevo.setPrecio(datosNuevos.getPrecio());

    return envioRepository.save(envioNuevo);
    }


}