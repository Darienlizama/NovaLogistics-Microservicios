package com.ms_infraestructura.ms_infraestructura.DTO;


import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
 
@Data
@EqualsAndHashCode(callSuper = false)
public class VehiculoDTO extends RepresentationModel<VehiculoDTO> {
    private Integer id;
    private String patente, marca, modelo;
}
