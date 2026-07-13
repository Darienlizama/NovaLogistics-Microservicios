package com.ms_operaciones.ms_operaciones.DTO;

import java.time.LocalDateTime;

import com.ms_operaciones.ms_operaciones.model.Paquete;

import lombok.Data;

@Data
public class EnvioDTO {

    private Long id;
    private String numeroGuia;
    private String direccionDestino;
    private String ciudadDestino;
    private LocalDateTime fecha;
    private int precio;
    private boolean estadoEnvio;

    // Campo que faltaba
    private Long idcliente;

    // Relación con paquete (si lo usas en el flujo)
    private Paquete paquete;

    // Campos adicionales para mostrar información del paquete en el DTO
    private String descripcionPaquete;
    private Double pesoPaquete;

    // Opcional: nombreCliente si quieres mostrarlo en la respuesta
    private String nombreCliente;
    private ClienteexternoDTO cliente;

    
}