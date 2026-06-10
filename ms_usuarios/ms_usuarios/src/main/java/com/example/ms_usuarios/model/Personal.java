package com.example.ms_usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="personal")
@NoArgsConstructor
@AllArgsConstructor
public class Personal 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true ,nullable = false)
    @NotBlank(message = "El rut no puede estar vacio!")
    private String rut;

    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El apellido no puede estar vacio!")
    private String apellido;
    
    @Column(nullable = false)
    @NotBlank(message ="El cargo no puede estar vacio!")
    private String cargo;   
}

