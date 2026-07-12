package com.ms_operaciones.ms_operaciones.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ms_operaciones.ms_operaciones.Assembler.PaqueteModelAssembler;
import com.ms_operaciones.ms_operaciones.DTO.PaqueteDTO;
import com.ms_operaciones.ms_operaciones.model.Paquete;
import com.ms_operaciones.ms_operaciones.service.PaqueteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "paquete", description = "Operaciones relacionadas con los paquetes")
@RestController
@RequestMapping("/api/V2/paquetes")
public class PaqueteControllerV2 {

        @Autowired
        private PaqueteService paqueteService;

        @Autowired
        private PaqueteModelAssembler paqueteAssembler;

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Listar paquetes", description = "Obtiene una lista de paquetes")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de paquetes obtenida exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay paquetes disponibles")
        })
        public CollectionModel<EntityModel<Paquete>> getAllPaquetes() {
                List<EntityModel<Paquete>> paquetes = paqueteService.totalPaquetes().stream()
                                .map(paqueteAssembler::toModel)
                                .collect(Collectors.toList());

                return CollectionModel.of(paquetes,
                                linkTo(methodOn(PaqueteControllerV2.class).getAllPaquetes()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Buscar paquete por ID", description = "Busca un paquete por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Paquete encontrado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
        })
        public ResponseEntity<?> getPaqueteById(@PathVariable Long id) {
                try {
                        Paquete dto = paqueteService.buscarPorId(id);
                        return ResponseEntity.ok(paqueteAssembler.toModel(dto));
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                }
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Crear paquete", description = "Crea un nuevo paquete")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Paquete creado exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public ResponseEntity<?> createPaquete(@RequestBody PaqueteDTO paquete) {
                try {
                        Paquete saved = paqueteService.guardarPaquetes(paquete);
                        return ResponseEntity
                                        .created(linkTo(methodOn(PaqueteControllerV2.class)
                                                        .getPaqueteById(saved.getId())).toUri())
                                        .body(paqueteAssembler.toModel(saved));
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar paquete", description = "Actualiza un paquete existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Paquete actualizado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
        })
        public ResponseEntity<?> updatePaquete(@PathVariable Long id, @RequestBody PaqueteDTO paquete) {
                try {
                        paquete.setId(id);
                        Paquete updated = paqueteService.actualizarPaquete(id, paquete);
                        return ResponseEntity.ok(paqueteAssembler.toModel(updated));
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                }
        }

        @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Eliminar paquete", description = "Elimina un paquete por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Paquete eliminado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
        })
        public ResponseEntity<?> deletePaquete(@PathVariable Long id) {
                try {
                        paqueteService.eliminarPaquetes(id);
                        return ResponseEntity.noContent().build();
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                }
        }
}
