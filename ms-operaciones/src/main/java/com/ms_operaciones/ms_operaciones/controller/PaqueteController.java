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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("/api/V1/paquetes")
public class PaqueteController {
    @Autowired
    private PaqueteService paqueteService;

    // guardar
    @PostMapping
    @Operation(summary = "Crear paquete", description = "Crea un nuevo paquete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paquete creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<?> guardarPaquete(@Valid @RequestBody PaqueteDTO paquete) {
        try {

            Paquete nuevo = paqueteService.guardarPaquetes(paquete);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("solicitud invalida", HttpStatus.BAD_REQUEST);
        }
    }

    // listar
    @GetMapping()
    @Operation(summary = "Listar paquetes", description = "Obtiene una lista de paquetes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de paquetes obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay paquetes disponibles")
    })
    public ResponseEntity<List<Paquete>> listarPaquetes() {
        log.info("consultando lista de envios");
        List<Paquete> paquetes = paqueteService.totalPaquetes();
        return new ResponseEntity<>(paquetes, HttpStatus.OK);

    }

    // buscarporid
    @GetMapping("/{id}")
    @Operation(summary = "Buscar paquete por ID", description = "Busca un paquete por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paquete encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {

            Paquete dto = paqueteService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paquete", description = "Elimina un paquete por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paquete eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
    })
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {

            paqueteService.eliminarPaquetes(id);
            return ResponseEntity.ok("El Paquete con el ID:" + id + " fue eliminado con exito");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paquete", description = "Actualiza un paquete existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paquete actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody PaqueteDTO paquete) {
        try {

            log.info("Petición recibida para actualizar el paquete ID: {}", id);
            Paquete actualizado = paqueteService.actualizarPaquete(id, paquete);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
