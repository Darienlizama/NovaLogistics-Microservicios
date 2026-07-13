package com.example.ms_usuarios.controller.V2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_usuarios.DTO.AuditoriaDTO;
import com.example.ms_usuarios.assembler.AuditoriaModelAssembler;
import com.example.ms_usuarios.service.AuditoriaService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/auditorias")
public class AuditoriaControllerV2 {

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private AuditoriaModelAssembler assembler;

    // Listar todas las auditorías con HATEOAS
    @GetMapping
    public ResponseEntity<CollectionModel<AuditoriaDTO>> listarAuditorias() {
       
        List<AuditoriaDTO> auditoriasList = auditoriaService.findAll();
        
        List<AuditoriaDTO> auditoriasModelList = auditoriasList.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

   
        CollectionModel<AuditoriaDTO> collection = CollectionModel.of(auditoriasModelList,
                linkTo(methodOn(AuditoriaControllerV2.class).listarAuditorias()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDTO> obtenerPorId(@PathVariable Long id) {
        AuditoriaDTO dto = auditoriaService.findById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }
}