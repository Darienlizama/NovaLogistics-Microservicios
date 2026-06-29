package com.ms_infraestructura.ms_infraestructura.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import com.ms_infraestructura.ms_infraestructura.DTO.VehiculoDTO;
import com.ms_infraestructura.ms_infraestructura.controller.VehiculoController;
 
@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<VehiculoDTO, VehiculoDTO> {
 
    @Override
    public VehiculoDTO toModel(VehiculoDTO dto) {
        dto.add(
            linkTo(methodOn(VehiculoController.class).buscarVehiculoPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(VehiculoController.class).listarVehiculo()).withRel("vehiculos"),
            Link.of(linkTo(methodOn(VehiculoController.class).eliminarVehiculo(dto.getId())).toUri().toString())
                .withRel("eliminar")
        );
        return dto;
    }
}