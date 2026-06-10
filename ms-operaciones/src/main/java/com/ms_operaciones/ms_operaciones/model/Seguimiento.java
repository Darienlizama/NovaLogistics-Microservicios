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
    private String estado; 

    private String ubicacion;

    private LocalDateTime fecha_hora;

    @PrePersist
    protected void onCreate() {
        this.fecha_hora = LocalDateTime.now();
    }
}