package com.example.ms_usuarios.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ms_usuarios.DTO.AuditoriaDTO;
import com.example.ms_usuarios.model.Auditoria;
import com.example.ms_usuarios.service.AuditoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auditoria")
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping()
    public ResponseEntity<List<AuditoriaDTO>> listarAsientos() {
        List<AuditoriaDTO> Auditorias = auditoriaService.findAll();
        if (Auditorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Auditorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDTO> buscarAsientoPorId(@PathVariable Long id) {
        try {
            AuditoriaDTO Auditorias = auditoriaService.findById(id);
            return ResponseEntity.ok(Auditorias);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Auditoria> guardar( @Valid@RequestBody Auditoria auditoria) {
        Auditoria Auditorias = auditoriaService.save(auditoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(Auditorias);
    }


     @PutMapping("/{id}")
     public ResponseEntity<Auditoria> actualizar(@PathVariable Long id, @RequestBody Auditoria auditoria) {
        try {
            Auditoria auditoriaactualizado = auditoriaService.updateAuditoria(id, auditoria);
            return ResponseEntity.ok(auditoriaactualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            auditoriaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

