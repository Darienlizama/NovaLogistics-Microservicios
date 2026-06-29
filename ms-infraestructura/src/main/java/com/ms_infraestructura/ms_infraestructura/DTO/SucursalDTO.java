package com.ms_infraestructura.ms_infraestructura.DTO;


import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
 
@Data
@EqualsAndHashCode(callSuper = false)
public class SucursalDTO extends RepresentationModel<SucursalDTO> 
{
    private Integer id;
    private String direccion, ciudad, comuna;
}
 