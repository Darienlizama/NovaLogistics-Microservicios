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

import com.example.ms_usuarios.DTO.PersonalDTO;
import com.example.ms_usuarios.model.Personal;
import com.example.ms_usuarios.service.PersonalService;



@RestController
@RequestMapping("/api/v1/personal")
public class PersonalController {
    @Autowired
    private PersonalService personalService;

    @GetMapping
    public ResponseEntity<List<PersonalDTO>> listarAsientos() {
        List<PersonalDTO> personals = personalService.FindAll();
        if (personals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(personals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalDTO> buscarAsientoPorId(@PathVariable Long id) {
        try {
            PersonalDTO personals = personalService.SearchById(id);
            return ResponseEntity.ok(personals);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Personal> guardar(@RequestBody Personal personal) {
        Personal personals = personalService.save(personal);
        return ResponseEntity.status(HttpStatus.CREATED).body(personals);
    }


     @PutMapping("/{id}")
     public ResponseEntity<Personal> actualizar(@PathVariable Long id, @RequestBody Personal personal) {
        try {
            Personal personalActualizado = personalService.updatePersonal(id, personal);
            return ResponseEntity.ok(personalActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            personalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
