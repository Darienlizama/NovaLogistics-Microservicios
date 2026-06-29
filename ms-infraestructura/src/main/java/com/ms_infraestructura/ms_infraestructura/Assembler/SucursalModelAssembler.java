package com.ms_infraestructura.ms_infraestructura.Assembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
 
import com.ms_infraestructura.ms_infraestructura.DTO.SucursalDTO;
import com.ms_infraestructura.ms_infraestructura.controller.SucursalController;
 
@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalDTO, SucursalDTO> {
 
    @Override
    public SucursalDTO toModel(SucursalDTO dto) {
        dto.add(
            linkTo(methodOn(SucursalController.class).buscarSucursalPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(SucursalController.class).listarSucursal()).withRel("sucursales"),
            Link.of(linkTo(methodOn(SucursalController.class).eliminar(dto.getId())).toUri().toString())
                .withRel("eliminar")
        );
        return dto;
    }
}