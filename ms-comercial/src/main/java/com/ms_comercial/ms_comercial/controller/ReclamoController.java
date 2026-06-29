package com.ms_comercial.ms_comercial.controller;

import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
import com.ms_comercial.ms_comercial.DTO.ReclamoDTO;
import com.ms_comercial.ms_comercial.assembler.ReclamoModelAssembler;
import com.ms_comercial.ms_comercial.model.Reclamo;
import com.ms_comercial.ms_comercial.service.ReclamoService;
 
@RestController
@RequestMapping("/api/v1/reclamo")
public class ReclamoController {
 
    @Autowired
    private ReclamoService reclamoService;
 
    @Autowired
    private ReclamoModelAssembler assembler;
 
    @GetMapping
    public ResponseEntity<CollectionModel<ReclamoDTO>> listarReclamos() {
        List<ReclamoDTO> reclamos = reclamoService.findAll();
        if (reclamos.isEmpty()) return ResponseEntity.noContent().build();
 
        List<ReclamoDTO> reclamosConLinks = reclamos.stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
 
        CollectionModel<ReclamoDTO> collection = CollectionModel.of(
            reclamosConLinks,
            linkTo(methodOn(ReclamoController.class).listarReclamos()).withSelfRel()
        );
        return ResponseEntity.ok(collection);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<ReclamoDTO> buscarReclamoPorId(@PathVariable Long id) {
        try {
            ReclamoDTO reclamo = reclamoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(reclamo));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
 
    @PostMapping
    public ResponseEntity<Reclamo> guardar(@RequestBody Reclamo reclamo) {
        Reclamo nuevoReclamo = reclamoService.save(reclamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReclamo);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<Reclamo> actualizar(@PathVariable Long id, @RequestBody Reclamo reclamo) {
        try {
            Reclamo reclamoActualizado = reclamoService.updateReclamo(id, reclamo);
            return ResponseEntity.ok(reclamoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            reclamoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}