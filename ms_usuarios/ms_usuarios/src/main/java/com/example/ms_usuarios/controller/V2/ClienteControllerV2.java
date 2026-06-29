package com.example.ms_usuarios.controller.V2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_usuarios.DTO.ClienteDTO;
import com.example.ms_usuarios.assembler.ClienteModelAssembler;
import com.example.ms_usuarios.model.Cliente;
import com.example.ms_usuarios.service.ClienteService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/clientes") 
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assembler;

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerDatosV2(@PathVariable Long id) {
        ClienteDTO dto = clienteService.obtenerClientePorId(id);
        
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<ClienteDTO>> listarClientesV2() {
        List<Cliente> clientes = clienteService.obtenerTodos();
       
        List<ClienteDTO> clientesModel = clientes.stream()
                .map(clienteService::convertirADto)
                .map(assembler::toModel) 
                .collect(Collectors.toList());

        CollectionModel<ClienteDTO> collection = CollectionModel.of(clientesModel,
                linkTo(methodOn(ClienteControllerV2.class).listarClientesV2()).withSelfRel());

        return ResponseEntity.ok(collection);
    }
}