package com.ms_comercial.ms_comercial.DTO;


import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
 
@Data
@EqualsAndHashCode(callSuper = false)
public class PrecioDTO extends RepresentationModel<PrecioDTO> {
    private Integer id;
    private Double precio_base;
}