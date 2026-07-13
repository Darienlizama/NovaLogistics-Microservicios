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

import com.ms_operaciones.ms_operaciones.DTO.EnvioDTO;
import com.ms_operaciones.ms_operaciones.model.Envio;
import com.ms_operaciones.ms_operaciones.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@Tag(name = "envios", description = "operaciones relacionadas con el envio")
@RequestMapping("/api/v1/envios")
public class EnvioController {
    @Autowired
    private EnvioService envioService;

    @PostMapping
    @Operation(summary = "Crear envío", description = "Crea un nuevo envío")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Envío creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<?> guardarEnvio(@Valid @RequestBody EnvioDTO envio) {
        try {

            Envio nuevo = envioService.guardarEnvio(envio);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("solicitud invalida", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Listar envíos", description = "Obtiene una lista de envíos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay envíos disponibles")
    })
    public ResponseEntity<List<Envio>> listaEnvios() {
        log.info("consultando lista de paquetes");
        List<Envio> envios = envioService.listaEnvios();
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío", description = "Elimina un envío por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Envío eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<String> eliminarEnvio(@PathVariable Long id) {
        try {

            envioService.eliminarEnvio(id);
            return ResponseEntity.ok("El envío con el ID: " + id + " fue eliminado con éxito");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar envío por ID", description = "Busca un envío por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Envio dto = envioService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío", description = "Actualiza un envío existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody EnvioDTO envio) {
        log.info("Petición recibida para actualizar el Envio ID: {}", id);
        try {

            Envio actualizado = envioService.actualizarEnvio(id, envio);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "actualizar estado de el envio", description = "actualiza el estado de el envio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "le estado de el envio se actualizo correctamente"),
            @ApiResponse(responseCode = "404", description = "el envio  no fue encontrado")
    })
    public ResponseEntity<Envio> actualizarestado(@PathVariable Long id, @Valid @RequestBody EnvioDTO envio) {

        log.info("Petición recibida para actualizar el estado de el Envio por el ID: {}", id);

        try {
            Envio estadonuevo = envioService.actualizarestado(id, envio);
            return ResponseEntity.ok(estadonuevo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }
    }

}