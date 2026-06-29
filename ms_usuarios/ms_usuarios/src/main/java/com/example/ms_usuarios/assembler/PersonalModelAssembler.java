package com.example.ms_usuarios.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.example.ms_usuarios.DTO.PersonalDTO;
import com.example.ms_usuarios.controller.V2.PersonalControllerV2;


@Component
public class PersonalModelAssembler implements RepresentationModelAssembler<PersonalDTO, PersonalDTO> {

    @Override
    public PersonalDTO toModel(PersonalDTO dto) {
        dto.removeLinks();
        
        // Enlace al recurso individual (V2)
        dto.add(linkTo(methodOn(PersonalControllerV2.class).buscarPersonalPorIdV2(dto.getId())).withSelfRel());
        
        // Enlace a la colección completa (V2)
        dto.add(linkTo(methodOn(PersonalControllerV2.class).listarPersonalV2()).withRel("personal-all"));

        return dto;
    }
}