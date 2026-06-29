package com.ms_infraestructura.ms_infraestructura.controller;
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
import com.ms_infraestructura.ms_infraestructura.DTO.SucursalDTO;
import com.ms_infraestructura.ms_infraestructura.Assembler.SucursalModelAssembler;
import com.ms_infraestructura.ms_infraestructura.model.Sucursal;
import com.ms_infraestructura.ms_infraestructura.service.SucursalService;

@RestController
@RequestMapping("/api/v1/sucursal")
public class SucursalController {
 
    @Autowired
    private SucursalService sucursalService;
 
    @Autowired                                              // ← nuevo
    private SucursalModelAssembler assembler;               // ← nuevo
 
    @GetMapping
    public ResponseEntity<CollectionModel<SucursalDTO>> listarSucursal() {  // ← reemplaza
        List<SucursalDTO> sucursales = sucursalService.obtenerSucursal();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
 
        List<SucursalDTO> sucursalesConLinks = sucursales.stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
 
        CollectionModel<SucursalDTO> collection = CollectionModel.of(
            sucursalesConLinks,
            linkTo(methodOn(SucursalController.class).listarSucursal()).withSelfRel()
        );
 
        return ResponseEntity.ok(collection);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> buscarSucursalPorId(@PathVariable Integer id) {  // ← reemplaza
        try {
            SucursalDTO sucursal = sucursalService.obtenerSucursalPorId(id);
            return ResponseEntity.ok(assembler.toModel(sucursal));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
 
    @PostMapping                                            // ← sin cambios
    public ResponseEntity<Sucursal> guardarSucursal(@RequestBody Sucursal sucursal) {
        Sucursal sucursalCreada = sucursalService.guardarSucursal(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalCreada);
    }
 
    @PutMapping("/{id}")                                   // ← sin cambios
    public ResponseEntity<Sucursal> actualizar(@PathVariable Integer id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal sucursalActualizado = sucursalService.actualizarSucursal(id, sucursal);
            return ResponseEntity.ok(sucursalActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
 
    @DeleteMapping("/{id}")                                // ← sin cambios
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            sucursalService.eliminarSucursal(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
 