package com.ms_operaciones.ms_operaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de guía es obligatorio")
    @Column(unique = true, name = "numero_guia")
    private String numeroGuia;

    //@ManyToOne
    //@JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Long idcliente; 

    @ManyToOne
    @JoinColumn(name = "id_paquete", nullable = false)
    @NotNull(message = "El paquete es obligatorio")
    private Paquete paquete; 

    @NotBlank(message = "La dirección de destino es obligatoria")
    @Column(name = "direccion_destino")
    private String direccionDestino;

    @NotBlank(message = "La ciudad de destino es obligatoria")
    @Column(name = "ciudad_destino")
    private String ciudadDestino;

    private Double precio;

    @Column(name = "fecha_envio")
    private LocalDateTime fecha;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
        // Generación automática simple del número de guía si no existe
        if (this.numeroGuia == null) {
            this.numeroGuia = "NVL-" + System.currentTimeMillis();
        }
    }
}