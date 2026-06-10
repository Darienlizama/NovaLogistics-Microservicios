package com.example.ms_usuarios.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ms_usuarios.DTO.PersonalDTO;
import com.example.ms_usuarios.model.Personal;
import com.example.ms_usuarios.repository.PersonalRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonalService {
    @Autowired
    private PersonalRepository personalRepository;


    private PersonalDTO convertirADTO(Personal personal) {
        PersonalDTO dto = new PersonalDTO();
        dto.setId(personal.getId());
        dto.setNombre(personal.getNombre());
        dto.setCargo(personal.getCargo());
        dto.setApellido(personal.getApellido());

        return dto;
    }
    


    public List<PersonalDTO> FindAll(){
        log.info("...la auditoria se esta ejecutando");
        return personalRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public PersonalDTO SearchById(Long id){
        log.info("...la auditoria se esta ejecutando");
         Personal personal = personalRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
         return convertirADTO(personal);
    }

    public Personal save(Personal personal) {
        log.info("...la auditoria se esta guardando");
        return personalRepository.save(personal);
    }

    public void deleteById(Long id) {
        log.info("...la auditoria se esta eliminando");
        Personal personal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        personalRepository.deleteById(personal.getId());
    }

    public Personal updatePersonal(Long id, Personal personal) {
        log.info("...la auditoria se esta actualizando");
        Personal PersonalUpdate = personalRepository.findById(id)
         .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

            PersonalUpdate.setRut(personal.getRut());
            PersonalUpdate.setApellido(personal.getApellido());
            PersonalUpdate.setNombre(personal.getNombre());
            PersonalUpdate.setCargo(personal.getCargo());
            return personalRepository.save(PersonalUpdate);
       
    }

}
