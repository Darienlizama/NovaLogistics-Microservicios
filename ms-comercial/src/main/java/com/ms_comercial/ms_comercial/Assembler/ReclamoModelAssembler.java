package com.ms_comercial.ms_comercial.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.ms_comercial.ms_comercial.DTO.ReclamoDTO;
import com.ms_comercial.ms_comercial.controller.ReclamoController;

@Component
public class ReclamoModelAssembler implements RepresentationModelAssembler<ReclamoDTO, ReclamoDTO> {

    @Override
    public ReclamoDTO toModel(ReclamoDTO dto) {
        dto.add(
                linkTo(methodOn(ReclamoController.class).buscarReclamoPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(ReclamoController.class).listarReclamos()).withRel("reclamos"),
                Link.of(linkTo(methodOn(ReclamoController.class).eliminar(dto.getId())).toUri().toString())
                        .withRel("eliminar"));
        return dto;
    }
}