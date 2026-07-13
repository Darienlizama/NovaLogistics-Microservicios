package com.ms_operaciones.ms_operaciones.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms_operaciones.ms_operaciones.Assembler.SeguimientoAssembler;
import com.ms_operaciones.ms_operaciones.DTO.SeguimientoDTO;
import com.ms_operaciones.ms_operaciones.model.Seguimiento;
import com.ms_operaciones.ms_operaciones.service.SeguimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "seguimiento", description = "operacion relacionada con los seguimientos ")
@RestController
@RequestMapping("/api/V2/seguimientos")
public class SeguimientoControllerV2 {

    @Autowired
    private SeguimientoService seguimientoService;

    @Autowired
    private SeguimientoAssembler seguimientoAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "listar seguimientos", description = "obtiene una lista de los seguimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "la de los seguimientos se obtuvo exitosamente"),
            @ApiResponse(responseCode = "204", description = "no hay seguimientos")
    })
    public CollectionModel<EntityModel<Seguimiento>> getallSeguimiento() {
        List<EntityModel<Seguimiento>> seguimiento = seguimientoService.listarSeguimiento().stream()
                .map(seguimientoAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(seguimiento,
                linkTo(methodOn(SeguimientoControllerV2.class).getallSeguimiento()).withSelfRel());

    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "buscar seguimiento por id ", description = "busca un seguimiento por el id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "seguimiento encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "seguimiento no encontrado")
    })
    public ResponseEntity<?> getseguimientoById(@PathVariable Long id) {
        try {
            Seguimiento seguimiento = seguimientoService.buscarPorId(id);
            return ResponseEntity.ok(seguimientoAssembler.toModel(seguimiento));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "crear seguimiento", description = "Crea una nuevo seguimiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "seguimiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<?> createseguimiento(@Valid @RequestBody SeguimientoDTO seguimientodto) {
        try {
            Seguimiento saved = seguimientoService.agregarSeguimiento(seguimientodto);
            return ResponseEntity
                    .created(linkTo(methodOn(SeguimientoControllerV2.class).getseguimientoById(saved.getId())).toUri())
                    .body(seguimientoAssembler.toModel(saved));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "actualizar seguimiento", description = "actualiza un seguimiento que ya existe ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "seguimiento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "seguimiento no encontrado")
    })
    public ResponseEntity<?> updateseguimiento(@PathVariable Long id, @RequestBody SeguimientoDTO seguimiento) {
        try {
            seguimiento.setId(id);
            Seguimiento updatedsSeguimiento = seguimientoService.actualizarSeguimiento(id, seguimiento);
            return ResponseEntity.ok(seguimientoAssembler.toModel(updatedsSeguimiento));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar seguimiento", description = "Elimina un seguimiento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "seguimiento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "seguimiento no encontrado")
    })
    public ResponseEntity<?> deleteseguimiento(@PathVariable Long id) {
        try {
            seguimientoService.eliminarSeguimiento(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
