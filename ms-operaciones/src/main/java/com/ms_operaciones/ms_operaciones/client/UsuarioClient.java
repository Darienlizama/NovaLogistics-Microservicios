package com.ms_operaciones.ms_operaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Le decimos a Feign: "Llama al microservicio que se llama MS-USUARIOS en Eureka"
@FeignClient(name = "MS-USUARIOS") 
public interface UsuarioClient {

    // Feign usará esta ruta exacta para ir a buscar al cliente en el puerto 8081
    @GetMapping("/api/v1/clientes/{id}")
    Object obtenerClientePorId(@PathVariable("id") Long id);
}