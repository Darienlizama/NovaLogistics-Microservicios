package com.example.ms_usuarios.controller.V2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_usuarios.DTO.PersonalDTO;
import com.example.ms_usuarios.assembler.PersonalModelAssembler;
import com.example.ms_usuarios.service.PersonalService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/personal")
public class PersonalControllerV2 {

    @Autowired
    private PersonalService personalService;

    @Autowired
    private PersonalModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<PersonalDTO>> listarPersonalV2() {
        
        List<PersonalDTO> personalList = personalService.FindAll();
        if (personalList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Pasamos cada DTO por el assembler
        List<PersonalDTO> personalModelList = personalList.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<PersonalDTO> collection = CollectionModel.of(personalModelList,
                linkTo(methodOn(PersonalControllerV2.class).listarPersonalV2()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalDTO> buscarPersonalPorIdV2(@PathVariable Long id) {
        try {
            // Usamos tu método SearchById() del servicio
            PersonalDTO dto = personalService.SearchById(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}