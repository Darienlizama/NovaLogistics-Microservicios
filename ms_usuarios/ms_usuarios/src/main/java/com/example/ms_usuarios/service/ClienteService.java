package com.example.ms_usuarios.service;

import lombok.extern.slf4j.Slf4j; 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ms_usuarios.DTO.ClienteDTO;
import com.example.ms_usuarios.model.Cliente;
import com.example.ms_usuarios.repository.ClienteRepository;
@Service
@Slf4j

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional 
    public Cliente guardarCliente(Cliente cliente) {

        log.info("Guardando Cliente con RUT: {}", cliente.getRut());
        
        if(clienteRepository.existsByRut(cliente.getRut())) {
            log.error("Error, el RUT {} ya existe", cliente.getRut());
            throw new RuntimeException("RUT duplicado");
        }
        
        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void eliminarCliente(Long id){
        if(!clienteRepository.existsById(id))
            throw new RuntimeException("Cliente no encontrado con el ID: " + id);

        clienteRepository.deleteById(id);
        log.info("Cliente con ID {} eliminado con éxito", id);
    }

    public ClienteDTO convertirADto(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setRut(cliente.getRut());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setCorreo(cliente.getCorreo());
        dto.setTelefono(cliente.getTelefono());
        // categoriaCliente queda fuera si no existe en el Model
        return dto;
    }

    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorId(Long id) {
        log.info("Accediendo a datos del cliente ID {}", id);
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertirADto(cliente); 
    }
    
    @Transactional
    public Cliente actualizarCliente(Long id, Cliente clienteNuevo){
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setRut(clienteNuevo.getRut());
        cliente.setNombre(clienteNuevo.getNombre());
        cliente.setApellido(clienteNuevo.getApellido());
        cliente.setCorreo(clienteNuevo.getCorreo());
        cliente.setTelefono(clienteNuevo.getTelefono());

        return clienteRepository.save(cliente);
    }
}
