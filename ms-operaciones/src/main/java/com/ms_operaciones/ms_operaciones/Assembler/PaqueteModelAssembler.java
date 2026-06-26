package com.ms_operaciones.ms_operaciones.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.ms_operaciones.ms_operaciones.DTO.PaqueteDTO;
import com.ms_operaciones.ms_operaciones.controller.V2.PaqueteControllerV2;
import com.ms_operaciones.ms_operaciones.model.Paquete;

@Component
public class PaqueteModelAssembler implements RepresentationModelAssembler<Paquete, EntityModel<Paquete>> {

    @Override
    public EntityModel<Paquete> toModel(Paquete paquete) {
        return EntityModel.of(paquete,
            linkTo(methodOn(PaqueteControllerV2.class).getPaqueteById(paquete.getId())).withSelfRel(),
            linkTo(methodOn(PaqueteControllerV2.class).getAllPaquetes()).withRel("paquetes"),
            linkTo(methodOn(PaqueteControllerV2.class).updatePaquete(paquete.getId(), paquete)).withRel("actualizar"),
            linkTo(methodOn(PaqueteControllerV2.class).deletePaquete(paquete.getId())).withRel("eliminar"),
            linkTo(methodOn(PaqueteControllerV2.class).createPaquete(paquete)).withRel("crear")
        );
    }
}
