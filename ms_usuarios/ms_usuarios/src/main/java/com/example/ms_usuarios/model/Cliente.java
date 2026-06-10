package com.example.ms_usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name="clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El RUT no puede estar vacío")
    @Pattern(
        regexp = "^[0-9]{7,8}[0-9Kk]$", 
        message = "El RUT debe tener entre 8 y 9 dígitos, sin puntos ni guion (ej: 12345678K)"
    )
    @Column(unique = true)
    private String rut;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio!")
    private String apellido;

    @Email(message = "Debe ingresar un correo válido")
    private String correo;

    @NotBlank(message ="el telefono no puede estar vacio!" )
    private String telefono;
}
