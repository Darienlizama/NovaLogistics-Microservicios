package com.example.ms_usuarios.DTO;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PersonalDTO extends RepresentationModel<PersonalDTO> {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String cargo;
}