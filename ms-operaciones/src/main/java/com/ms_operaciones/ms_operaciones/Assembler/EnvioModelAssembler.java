package com.ms_operaciones.ms_operaciones.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.ms_operaciones.ms_operaciones.controller.V2.EnvioControllerV2;
import com.ms_operaciones.ms_operaciones.model.Envio;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioControllerV2.class).getEnvioById(envio.getId())).withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).getAllEnvios()).withRel("envios"),
                linkTo(methodOn(EnvioControllerV2.class).updateEnvio(envio.getId(), envio)).withRel("actualizar"),
                linkTo(methodOn(EnvioControllerV2.class).deleteEnvio(envio.getId())).withRel("eliminar"),
                linkTo(methodOn(EnvioControllerV2.class).createEnvio(envio)).withRel("crear")
        );
    }
}
