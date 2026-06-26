package com.ms_operaciones.ms_operaciones.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms_operaciones.ms_operaciones.DTO.EnvioDTO;
import com.ms_operaciones.ms_operaciones.model.Envio;
import com.ms_operaciones.ms_operaciones.repository.EnvioRepository;
import com.ms_operaciones.ms_operaciones.repository.PaqueteRepository;
import com.ms_operaciones.ms_operaciones.client.UsuarioClient; // <-- El Teléfono

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnvioService {
    
    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private PaqueteRepository paqueteRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public Envio guardarEnvio(Envio envio){
        log.info("Guardando envio...");

        if (envio.getIdcliente() != null) {
            try {
                log.info(" Consultando a MS-USUARIOS si el cliente {} existe...", envio.getIdcliente());
                
                // Llamamos a ms-usuarios por internet
                usuarioClient.obtenerClientePorId(envio.getIdcliente());
                
                log.info(" ¡Cliente verificado! Procediendo con el guardado.");
                
            } catch (FeignException.NotFound e) {
                throw new RuntimeException("Error: El cliente asignado al envío no existe en Usuarios.");
            } catch (FeignException e) {
                throw new RuntimeException("Error Crítico: No se pudo comunicar con MS-USUARIOS.");
            }
        } else {
            throw new RuntimeException("Error: El ID del cliente es obligatorio.");
        }

        // 2. Validar Paquete
        if (!paqueteRepository.existsById(envio.getPaquete().getId())) {
            throw new RuntimeException("Error: El paquete asignado al envío no existe.");
        }

        // 3. Validar precio
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
            throw new RuntimeException("No se puede eliminar: Envio no encontrado con el ID: "+id);
        }

        envioRepository.deleteById(id);
        log.info("Envio eliminado con exito");
    }

    public EnvioDTO convertirDTO(Envio envio){
        EnvioDTO dto = new EnvioDTO();
        dto.setId(envio.getId());
        dto.setNumeroGuia(envio.getNumeroGuia());
        
        // dto.setNombreCliente("Cliente ID: " + envio.getIdcliente());
        
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
    
    public Envio buscarPorId(Long id){
        log.info("Buscando Envio por id...");
        
        return envioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Envio no encontrado con ID: " + id));
        
       
    }

    public Envio actualizarEnvio(Long id, Envio datosNuevos) {
        log.info("Actualizando envio con ID: {}", id);

        Envio envioNuevo= envioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se puede actualizar: Envio no encontrado con ID: " + id));

        // Mapeamos el nuevo idcliente
        if(datosNuevos.getIdcliente() != null){
            envioNuevo.setIdcliente(datosNuevos.getIdcliente());
        }
        
        envioNuevo.setPaquete(datosNuevos.getPaquete());
        envioNuevo.setDireccionDestino(datosNuevos.getDireccionDestino());
        envioNuevo.setCiudadDestino(datosNuevos.getCiudadDestino());
        envioNuevo.setPrecio(datosNuevos.getPrecio());

        return envioRepository.save(envioNuevo);
    }
}