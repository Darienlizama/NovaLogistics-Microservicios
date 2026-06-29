package com.ms_comercial.ms_comercial.DTO;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
 
@Data
@EqualsAndHashCode(callSuper = false)
public class ReclamoDTO extends RepresentationModel<ReclamoDTO> {
    private Long id;
    private Long id_cliente;
    private String detalle_cliente;
    private String titulo;
    private boolean estado_reclamo;
}
 