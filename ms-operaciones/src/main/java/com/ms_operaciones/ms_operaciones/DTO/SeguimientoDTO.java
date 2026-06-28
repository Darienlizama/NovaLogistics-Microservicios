package com.ms_operaciones.ms_operaciones.DTO;

import lombok.Data;
import java.time.LocalDateTime;

import com.ms_operaciones.ms_operaciones.model.Envio;

@Data
public class SeguimientoDTO {
    private Long id;
    private Envio envio;
    private String numeroGuia; 
    private String estado;
    private String ubicacion;
    private LocalDateTime fecha_hora;

}