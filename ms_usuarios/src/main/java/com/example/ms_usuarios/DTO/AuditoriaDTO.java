package com.example.ms_usuarios.DTO;

import java.time.LocalDateTime;
import org.springframework.hateoas.RepresentationModel;
import com.example.ms_usuarios.model.Cliente;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuditoriaDTO extends RepresentationModel<AuditoriaDTO>{
    private Long id;
    private Cliente usuario;
    private String accion_realizada;
    private LocalDateTime fecha_evento;
}
