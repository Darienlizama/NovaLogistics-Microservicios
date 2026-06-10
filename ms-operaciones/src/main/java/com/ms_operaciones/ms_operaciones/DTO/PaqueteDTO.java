package com.ms_operaciones.ms_operaciones.DTO;
import lombok.Data;

@Data
public class PaqueteDTO {
    private Long id;
    private String descripcion;
    private Double peso_kg;
    private Boolean es_fragil;

}