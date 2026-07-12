package com.ms_comercial.ms_comercial.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.ms_comercial.ms_comercial.DTO.PrecioDTO;
import com.ms_comercial.ms_comercial.controller.PrecioController;

@Component
public class PrecioModelAssembler implements RepresentationModelAssembler<PrecioDTO, PrecioDTO> {

    @Override
    public PrecioDTO toModel(PrecioDTO dto) {
        // Se corrige methodOnq por methodOn
        dto.add(
            linkTo(methodOn(PrecioController.class).buscarPrecioPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(PrecioController.class).listarPrecio()).withRel("precios"),
            linkTo(methodOn(PrecioController.class).eliminarPrecio(dto.getId())).withRel("eliminar")
        );
        return dto;
    }
}