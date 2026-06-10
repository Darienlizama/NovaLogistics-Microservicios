package com.example.ms_usuarios.DTO;

import java.time.LocalDateTime;
import com.example.ms_usuarios.model.Cliente;
import lombok.Data;

@Data
public class AuditoriaDTO {
    private Long id;
    private Cliente usuario;
    private String accion_realizada;
    private LocalDateTime fecha_evento;
}
