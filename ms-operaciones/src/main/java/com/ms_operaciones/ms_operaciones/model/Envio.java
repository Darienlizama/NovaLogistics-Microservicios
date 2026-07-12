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
    @Size(min = 1, max = 20 , message = "el numero guia debe ser entre 1 y 20 caracteres")
    @Column(unique = true, name = "numero_guia")
    private String numeroGuia;

    
    @NotNull(message = "El cliente es obligatorio")
    @Min(value = 1 , message = "el id cliente no puede ser menor a 1")
    @Max(value = 200 , message = "el id cliente no debe mayor a 200")
    @Column(name = "id_cliente" , nullable = false)
    private Long idcliente; 

    @ManyToOne
    @JoinColumn(name = "id_paquete", nullable = false)
    @NotNull(message = "El paquete es obligatorio")
    private Paquete paquete; 

    @NotBlank(message = "La dirección de destino es obligatoria")
    @Size(min =  10 , max = 50,message = "la dirreccion debe estar entre 10 y 50 caracteres")
    @Column(name = "direccion_destino")
    private String direccionDestino;

    @NotBlank(message = "La ciudad de destino es obligatoria")
    @Size(min = 10 , max = 20 , message = "la ciudad de destino debe estar entre 10 y 20 caracteres")
    @Column(name = "ciudad_destino")
    private String ciudadDestino;

    @NotNull(message = "el precio no puede estar vacio")
    @Min(value = 500 , message = "el precio no puede ser menor a 500 pesos")
    @Max(value = 10000 , message = "el precio no debe mayor a 10.000 pesos")
    @Column(name = "precio" , nullable = false)
    private int precio;

    @Column(name = "fecha_envio")
    private LocalDateTime fecha;

    @Column(name = "estado_envio" , nullable =  false)
    private boolean estadoEnvio;


    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
        // Generación automática simple del número de guía si no existe
        if (this.numeroGuia == null) {
            this.numeroGuia = "NVL-" + System.currentTimeMillis();
        }
    }
}