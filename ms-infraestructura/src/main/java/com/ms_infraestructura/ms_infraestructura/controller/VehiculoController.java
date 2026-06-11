package com.ms_infraestructura.ms_infraestructura.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ms_infraestructura.ms_infraestructura.DTO.VehiculoDTO;
import com.ms_infraestructura.ms_infraestructura.model.Vehiculo;
import com.ms_infraestructura.ms_infraestructura.service.VehiculoService;

@RestController
@RequestMapping("/api/v1/vehiculo")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    
        @GetMapping
    public ResponseEntity<List<VehiculoDTO>> listarVehiculo() {
        List<VehiculoDTO> vehiculos = vehiculoService.obtenerVehiculo();
        
        if (vehiculos == null || vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(vehiculos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> buscarVehiculoPorId(@PathVariable Integer id)
    {
        try
        {
            VehiculoDTO vehiculos = vehiculoService.buscarPorId(id);
            return ResponseEntity.ok(vehiculos);
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }


    }
    @PostMapping
    public ResponseEntity<Vehiculo> guardarVehiculo(@RequestBody Vehiculo vehiculo)
    {
        Vehiculo nuevoVehiculo = vehiculoService.guardarVehiculo(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVehiculo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(@PathVariable Integer id, @RequestBody Vehiculo vehiculo)
    {
        try
        {
            Vehiculo vehiculoActualizado = vehiculoService.actualizarVehiculo(id, vehiculo);
            return ResponseEntity.ok(vehiculoActualizado);
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Integer id)
    {
        try
        {
            vehiculoService.eliminarVehiculo(id);
            return ResponseEntity.noContent().build();  
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }    
    }        

}
