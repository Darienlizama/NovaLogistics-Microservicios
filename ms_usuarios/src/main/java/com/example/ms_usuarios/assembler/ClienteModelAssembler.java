package com.example.ms_usuarios.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_usuarios.DTO.ClienteDTO;
import com.example.ms_usuarios.controller.V2.ClienteControllerV2;


@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDTO, ClienteDTO> {

    @Override
    public ClienteDTO toModel(ClienteDTO dto) {
    
        dto.removeLinks();

   
        dto.add(linkTo(methodOn(ClienteControllerV2.class).obtenerDatosV2(dto.getId())).withSelfRel());
       
        dto.add(linkTo(methodOn(ClienteControllerV2.class).listarClientesV2()).withRel("todos-los-clientes"));

        return dto;
    }
}
