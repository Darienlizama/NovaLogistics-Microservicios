package com.ms_operaciones.ms_operaciones.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms_operaciones.ms_operaciones.DTO.PaqueteDTO;
import com.ms_operaciones.ms_operaciones.model.Paquete;
import com.ms_operaciones.ms_operaciones.service.PaqueteService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("/api/v1/paquete")
public class PaqueteController {
    @Autowired
    private PaqueteService paqueteService;

    //guardar
    @PostMapping
    public ResponseEntity<Paquete>guardarPaquete(@Valid@RequestBody Paquete paquete){
        Paquete nuevo =paqueteService.guardarPaquetes(paquete);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    //listar
    @GetMapping()
    public ResponseEntity<List<Paquete>>listarPaquetes(){
        log.info("consultando lista de envios");
        List<Paquete>paquetes=paqueteService.totalPaquetes();
        return new  ResponseEntity<>(paquetes,HttpStatus.OK);

    }
    //buscarporid
    @GetMapping("/{id}")
    public ResponseEntity<PaqueteDTO>buscarPorId(@PathVariable Long id){
        PaqueteDTO dto=paqueteService.buscarPorId(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>eliminar(@PathVariable Long id){
        paqueteService.eliminarPaquetes(id);
        return ResponseEntity.ok("El Paquete con el ID:"+id+" fue eliminado con exito");


    }
    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Paquete> actualizar(@PathVariable Long id, @Valid @RequestBody Paquete paquete) {
        log.info("Petición recibida para actualizar el paquete ID: {}", id);
        Paquete actualizado = paqueteService.actualizarPaquete(id, paquete);
        return ResponseEntity.ok(actualizado);
    }

}
