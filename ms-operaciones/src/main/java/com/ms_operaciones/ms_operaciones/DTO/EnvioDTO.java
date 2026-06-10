package com.ms_operaciones.ms_operaciones.DTO;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EnvioDTO {
    private Long id;
    private String numeroGuia;
    
    private String nombreCliente;
    private String rutCliente;

    private String descripcionPaquete;
    private Double pesoPaquete;

    private String direccionDestino;
    private String ciudadDestino;
    private Double precio;
    private LocalDateTime fecha;



}