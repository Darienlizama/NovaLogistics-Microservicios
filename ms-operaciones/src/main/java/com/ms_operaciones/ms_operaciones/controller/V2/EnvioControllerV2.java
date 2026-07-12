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

import com.ms_operaciones.ms_operaciones.Assembler.EnvioModelAssembler;
import com.ms_operaciones.ms_operaciones.DTO.EnvioDTO;
import com.ms_operaciones.ms_operaciones.model.Envio;
import com.ms_operaciones.ms_operaciones.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "envio", description = "Operaciones relacionadas con los envíos")
@RestController
@RequestMapping("/api/V2/envios")
public class EnvioControllerV2 {

        @Autowired
        private EnvioService envioService;

        @Autowired
        private EnvioModelAssembler envioAssembler;

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Listar envíos", description = "Obtiene una lista de envíos")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay envíos disponibles")
        })
        public CollectionModel<EntityModel<Envio>> getAllEnvios() {
                List<EntityModel<Envio>> envios = envioService.listaEnvios().stream()
                                .map(envioAssembler::toModel)
                                .collect(Collectors.toList());

                return CollectionModel.of(envios,
                                linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Buscar envío por ID", description = "Busca un envío por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Envío encontrado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
        })
        public ResponseEntity<?> getEnvioById(@PathVariable Long id) {
                try {
                        Envio dto = envioService.buscarPorId(id);
                        return ResponseEntity.ok(envioAssembler.toModel(dto));
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                }
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Crear envío", description = "Crea un nuevo envío")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Envío creado exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public ResponseEntity<?> createEnvio(@Valid @RequestBody EnvioDTO envioDTO) {
                try {
                        Envio saved = envioService.guardarEnvio(envioDTO);
                        return ResponseEntity
                                        .created(linkTo(methodOn(EnvioControllerV2.class).getEnvioById(saved.getId()))
                                                        .toUri())
                                        .body(envioAssembler.toModel(saved));
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar envío", description = "Actualiza un envío existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Envío actualizado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
        })
        public ResponseEntity<?> updateEnvio(@PathVariable Long id, @RequestBody EnvioDTO envio) {
                try {
                        envio.setId(id);
                        Envio updated = envioService.actualizarEnvio(id, envio);
                        return ResponseEntity.ok(envioAssembler.toModel(updated));
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                }
        }

        @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Eliminar envío", description = "Elimina un envío por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Envío eliminado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
        })
        public ResponseEntity<?> deleteEnvio(@PathVariable Long id) {
                try {
                        envioService.eliminarEnvio(id);
                        return ResponseEntity.noContent().build();
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                }
        }
}
