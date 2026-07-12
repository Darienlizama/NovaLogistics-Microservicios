package com.ms_operaciones.ms_operaciones.DTO;

import lombok.Data;

@Data
public class ClienteexternoDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;

    
}
