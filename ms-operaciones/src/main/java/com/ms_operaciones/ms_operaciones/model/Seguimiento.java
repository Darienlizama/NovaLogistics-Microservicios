package com.ms_operaciones.ms_operaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento")
@Data
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_envio", nullable = false)
    @NotNull(message = "El envío es obligatorio para registrar seguimiento")
    private Envio envio;

    @NotBlank(message = "Debe especificar un estado")
    @Size(min = 5 , max = 10 , message = "el estado debe tener entre 5 y 10 caracteres")
    @Column(name = "estado" , nullable = false)
    private String estado; 

    @NotBlank(message = "la ubicacion no debe estar vacia")
    @Size(min = 20 , max = 50 , message = "la ubicacion debe tener entre 20 y 50 caracteres")
    @Column(name = "ubicacion" , nullable = false )
    private String ubicacion;

    @Column(name = "fecha_hora")
    private LocalDateTime fecha_hora;

    @PrePersist
    protected void onCreate() {
        this.fecha_hora = LocalDateTime.now();
    }
}