package com.example.ms_usuarios.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ms_usuarios.DTO.AuditoriaDTO;
import com.example.ms_usuarios.model.Auditoria;
import com.example.ms_usuarios.repository.AuditoriaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    private AuditoriaDTO convertirADTO(Auditoria auditoria) {
        AuditoriaDTO dto = new AuditoriaDTO();
        dto.setId(auditoria.getId());
        dto.setUsuario(auditoria.getUsuario());
        dto.setFecha_evento(auditoria.getFecha_evento());
        dto.setAccion_realizada(auditoria.getAccion_realizada());

        return dto;
    }


    public List<AuditoriaDTO> findAll() {
        log.info("...la auditoria se esta ejecutando");
        return auditoriaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
                
    }

    public AuditoriaDTO findById(Long id) {
        Auditoria auditoria = auditoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Auditoría no encontrada"));
        log.info("...la auditoria se esta ejecutando\"");
         return convertirADTO(auditoria);
    }

    public Auditoria save(Auditoria auditoria) {
        log.info("...la auditoria se esta guardando");
        return auditoriaRepository.save(auditoria);
    }

    public void deleteById(Long id) {
        log.info("...la auditoria se esta borrando");
        Auditoria auditoria = auditoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auditoría no encontrada"));
        auditoriaRepository.deleteById(auditoria.getId());
    }

    public Auditoria updateAuditoria(Long id, Auditoria auditoria) {
        log.info("...la auditoria se esta actualizando");
        Auditoria auditoriaToUpdate = auditoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auditoría no encontrada"));

       
            auditoriaToUpdate.setUsuario(auditoria.getUsuario());
            auditoriaToUpdate.setFecha_evento(auditoria.getFecha_evento());
            auditoriaToUpdate.setAccion_realizada(auditoria.getAccion_realizada());
            return auditoriaRepository.save(auditoriaToUpdate);
        
    }
}

