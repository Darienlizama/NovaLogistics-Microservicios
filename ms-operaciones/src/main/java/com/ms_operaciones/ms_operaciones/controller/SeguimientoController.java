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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/seguimiento")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    // 1. Crear un nuevo estado (ej: "En camino")
    @PostMapping
    @Operation(summary = "crear seguimiento", description = "Crea una nuevo seguimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "seguimiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Seguimiento> agregarSeguimiento(@Valid @RequestBody SeguimientoDTO seguimiento) {
        log.info("Petición recibida para agregar nuevo estado de seguimiento");
        Seguimiento nuevo = seguimientoService.agregarSeguimiento(seguimiento);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "listar seguimientos" , description = "obtiene una lista de los seguimiento")
     @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "la de los seguimientos se obtuvo exitosamente"),
        @ApiResponse(responseCode = "204" , description = "no hay seguimientos")
     })
    public ResponseEntity<List<Seguimiento>> listarTodos() {
        log.info("Consultando todos los registros de seguimiento");
        return ResponseEntity.ok(seguimientoService.listarSeguimiento());
    }

    @GetMapping("/{id}")
    @Operation(summary = "buscar seguimiento por id ", description = "busca un seguimiento por el id ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "seguimiento encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "seguimiento no encontrado")
})
    public ResponseEntity<Seguimiento> buscarPorId(@PathVariable Long id) {
        log.info("Consultando seguimiento ID: {}", id);
        return ResponseEntity.ok(seguimientoService.buscarPorId(id));
    }

    // 4. Actualizar un estado o ubicación
    @PutMapping("/{id}")
    @Operation(summary = "actualizar seguimiento", description = "actualiza un seguimiento que ya existe ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "seguimiento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "seguimiento no encontrado")
    })
    public ResponseEntity<Seguimiento> actualizar(@PathVariable Long id, @Valid @RequestBody SeguimientoDTO seguimientodDto) {
        log.info("Petición para actualizar seguimiento ID: {}", id);
        Seguimiento actualizado = seguimientoService.actualizarSeguimiento(id, seguimientodDto);
        return ResponseEntity.ok(actualizado);
    }

    // 5. Eliminar un registro
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar seguimiento", description = "Elimina un seguimiento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "seguimiento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "seguimiento no encontrado")
    })
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        log.info("Petición para eliminar seguimiento ID: {}", id);
        seguimientoService.eliminarSeguimiento(id);
        return ResponseEntity.ok("Seguimiento con ID " + id + " eliminado con éxito.");
    }
}