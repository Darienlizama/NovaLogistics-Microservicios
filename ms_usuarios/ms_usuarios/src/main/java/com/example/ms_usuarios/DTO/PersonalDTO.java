package com.example.ms_usuarios.DTO;

import lombok.Data;

@Data
public class PersonalDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String cargo;   
}
