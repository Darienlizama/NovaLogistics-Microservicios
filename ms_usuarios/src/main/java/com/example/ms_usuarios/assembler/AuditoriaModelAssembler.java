package com.example.ms_usuarios.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_usuarios.DTO.AuditoriaDTO;
import com.example.ms_usuarios.controller.V2.AuditoriaControllerV2;

@Component
public class AuditoriaModelAssembler implements RepresentationModelAssembler<AuditoriaDTO, AuditoriaDTO> {

    @Override
    public AuditoriaDTO toModel(AuditoriaDTO dto) {
        dto.removeLinks();
        
        // Link a sí mismo usando el controlador V2
        dto.add(linkTo(methodOn(AuditoriaControllerV2.class).obtenerPorId(dto.getId())).withSelfRel());
        
        // Link hacia la lista completa usando el controlador V2
        dto.add(linkTo(methodOn(AuditoriaControllerV2.class).listarAuditorias()).withRel("auditorias-all"));

        return dto;
    }
}