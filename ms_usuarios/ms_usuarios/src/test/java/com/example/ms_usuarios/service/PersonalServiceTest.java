package com.example.ms_usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.ms_usuarios.DTO.PersonalDTO;
import com.example.ms_usuarios.model.Personal;
import com.example.ms_usuarios.repository.PersonalRepository;

class PersonalServiceTest {

    @Mock
    private PersonalRepository personalRepository;

    @InjectMocks
    private PersonalService personalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // GIVEN
        Personal p1 = new Personal(1L, "11111111-1", "Juan", "Perez", "Cajero");
        Personal p2 = new Personal(2L, "22222222-2", "Ana", "Gomez", "Supervisor");
        when(personalRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // WHEN
        List<PersonalDTO> resultado = personalService.FindAll();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(personalRepository, times(1)).findAll();
    }

    @Test
    void testSearchByIdExitoso() {
        // GIVEN
        Long id = 1L;
        Personal p = new Personal(id, "12345678-9", "Carlos", "Ruiz", "Operario");
        when(personalRepository.findById(id)).thenReturn(Optional.of(p));

        // WHEN
        PersonalDTO resultado = personalService.SearchById(id);

        // THEN
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Carlos", resultado.getNombre());
        verify(personalRepository, times(1)).findById(id);
    }

    @Test
    void testSearchByIdNoEncontrado() {
        // GIVEN
        Long id = 99L;
        when(personalRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> {
            personalService.SearchById(id);
        });
        verify(personalRepository, times(1)).findById(id);
    }

    @Test
    void testSave() {
        // GIVEN
        Personal p = new Personal(null, "98765432-1", "Luisa", "Morales", "Asistente");
        when(personalRepository.save(any(Personal.class))).thenReturn(new Personal(1L, "98765432-1", "Luisa", "Morales", "Asistente"));

        // WHEN
        Personal resultado = personalService.save(p);

        // THEN
        assertNotNull(resultado);
        verify(personalRepository, times(1)).save(any(Personal.class));
    }

    @Test
    void testDeleteById() {
        // GIVEN
        Long id = 1L;
        Personal p = new Personal(id, "12345678-9", "Carlos", "Ruiz", "Operario");
        when(personalRepository.findById(id)).thenReturn(Optional.of(p));
        doNothing().when(personalRepository).deleteById(id);

        // WHEN
        assertDoesNotThrow(() -> personalService.deleteById(id));

        // THEN
        verify(personalRepository, times(1)).findById(id);
        verify(personalRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdatePersonal() {
        // GIVEN
        Long id = 1L;
        Personal pExistente = new Personal(id, "12345678-9", "Carlos", "Ruiz", "Operario");
        Personal pNuevosDatos = new Personal(null, "12345678-9", "Carlos Modificado", "Ruiz", "Jefe Operaciones");

        when(personalRepository.findById(id)).thenReturn(Optional.of(pExistente));
        when(personalRepository.save(any(Personal.class))).thenReturn(pExistente);

        // WHEN
        Personal resultado = personalService.updatePersonal(id, pNuevosDatos);

        // THEN
        assertNotNull(resultado);
        verify(personalRepository, times(1)).findById(id);
        verify(personalRepository, times(1)).save(any(Personal.class));
    }
}