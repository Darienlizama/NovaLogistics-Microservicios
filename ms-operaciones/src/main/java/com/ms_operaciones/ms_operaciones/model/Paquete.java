package com.ms_operaciones.ms_operaciones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="paquetes")
public class Paquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 5 , max = 70 , message = "la descricon debe estar entre 5 y 70 caracteres" )
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull(message = "el peso no puede estar vacio")
    @Min(value = 1, message = "El peso debe ser mayor a 0")
    @Column(name = "peso" , nullable = false)
    private Double peso_kg;

    @NotNull(message = "se debe indicar si el paquete es fragil")
    @Column(name = "es_fragil" , nullable = false)
    private Boolean es_fragil;
}