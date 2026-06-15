package com.example.ms_usuarios.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_usuarios.DTO.ClienteDTO;
import com.example.ms_usuarios.model.Cliente;
import com.example.ms_usuarios.service.ClienteService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Slf4j 
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    // guardar
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.guardarCliente(cliente);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
    //listar
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        log.info("Consultando lista completa de clientes");
        List<Cliente> clientes = clienteService.obtenerTodos();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }


    //buscarporid
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerDatos(@PathVariable Long id) {
        ClienteDTO dto = clienteService.obtenerClientePorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String>eliminarCliente(@PathVariable Long id){
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok("El Cliente con el Id: "+id+" Fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente>actualizarCliente(@PathVariable Long id,@RequestBody Cliente cliente){
        Cliente nuevo = clienteService.actualizarCliente(id, cliente);
        return ResponseEntity.ok(nuevo);
    }
}