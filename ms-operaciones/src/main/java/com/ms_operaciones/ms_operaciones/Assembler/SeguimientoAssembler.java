package com.ms_operaciones.ms_operaciones.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.ms_operaciones.ms_operaciones.DTO.SeguimientoDTO;
import com.ms_operaciones.ms_operaciones.controller.SeguimientoController;
import com.ms_operaciones.ms_operaciones.controller.V2.SeguimientoControllerV2;
import com.ms_operaciones.ms_operaciones.model.Seguimiento;

@Component
public class SeguimientoAssembler implements RepresentationModelAssembler<Seguimiento, EntityModel<Seguimiento>> {

	@Override
	public EntityModel<Seguimiento> toModel(Seguimiento seguimiento) {
		return EntityModel.of(seguimiento,
            linkTo(methodOn(SeguimientoControllerV2.class).getseguimientoById(seguimiento.getId().longValue())).withSelfRel(),
            linkTo(methodOn(SeguimientoControllerV2.class).getallSeguimiento()).withRel("Seguimiento")
        );
	}

}
