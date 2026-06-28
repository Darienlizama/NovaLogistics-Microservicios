package com.ms_operaciones.ms_operaciones.controller;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ms_operaciones.ms_operaciones.DTO.SeguimientoDTO;
import com.ms_operaciones.ms_operaciones.model.Seguimiento;
import com.ms_operaciones.ms_operaciones.service.SeguimientoService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/seguimiento")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    // 1. Crear un nuevo estado (ej: "En camino")
    @PostMapping
    public ResponseEntity<Seguimiento> agregarSeguimiento(@Valid @RequestBody SeguimientoDTO seguimiento) {
        log.info("Petición recibida para agregar nuevo estado de seguimiento");
        Seguimiento nuevo = seguimientoService.agregarSeguimiento(seguimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Seguimiento>> listarTodos() {
        log.info("Consultando todos los registros de seguimiento");
        return ResponseEntity.ok(seguimientoService.listarSeguimiento());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> buscarPorId(@PathVariable Long id) {
        log.info("Consultando seguimiento ID: {}", id);
        return ResponseEntity.ok(seguimientoService.buscarPorId(id));
    }

    // 4. Actualizar un estado o ubicación
    @PutMapping("/{id}")
    public ResponseEntity<Seguimiento> actualizar(@PathVariable Long id, @Valid @RequestBody SeguimientoDTO seguimientodDto) {
        log.info("Petición para actualizar seguimiento ID: {}", id);
        Seguimiento actualizado = seguimientoService.actualizarSeguimiento(id, seguimientodDto);
        return ResponseEntity.ok(actualizado);
    }

    // 5. Eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        log.info("Petición para eliminar seguimiento ID: {}", id);
        seguimientoService.eliminarSeguimiento(id);
        return ResponseEntity.ok("Seguimiento con ID " + id + " eliminado con éxito.");
    }
}