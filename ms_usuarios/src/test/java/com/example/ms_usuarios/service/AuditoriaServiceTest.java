package com.example.ms_usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.ms_usuarios.DTO.AuditoriaDTO;
import com.example.ms_usuarios.model.Auditoria;
import com.example.ms_usuarios.model.Cliente;
import com.example.ms_usuarios.repository.AuditoriaRepository;

class AuditoriaServiceTest {

    @Mock
    private AuditoriaRepository auditoriaRepository;

    @InjectMocks
    private AuditoriaService auditoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // GIVEN
        Auditoria a1 = new Auditoria(1L, new Cliente(), "Login", LocalDateTime.now());
        Auditoria a2 = new Auditoria(2L, new Cliente(), "Logout", LocalDateTime.now());
        when(auditoriaRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        // WHEN
        List<AuditoriaDTO> resultado = auditoriaService.findAll();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(auditoriaRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExitoso() {
        // GIVEN
        Long id = 1L;
        Auditoria a = new Auditoria(id, new Cliente(), "Crear", LocalDateTime.now());
        when(auditoriaRepository.findById(id)).thenReturn(Optional.of(a));

        // WHEN
        AuditoriaDTO resultado = auditoriaService.findById(id);

        // THEN
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(auditoriaRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNoEncontrado() {
        // GIVEN
        Long id = 99L;
        when(auditoriaRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> {
            auditoriaService.findById(id);
        });
        verify(auditoriaRepository, times(1)).findById(id);
    }

    @Test
    void testSave() {
        // GIVEN
        Auditoria a = new Auditoria(null, new Cliente(), "Eliminar", LocalDateTime.now());
        when(auditoriaRepository.save(any(Auditoria.class))).thenReturn(new Auditoria(1L, new Cliente(), "Eliminar", LocalDateTime.now()));

        // WHEN
        Auditoria resultado = auditoriaService.save(a);

        // THEN
        assertNotNull(resultado);
        verify(auditoriaRepository, times(1)).save(any(Auditoria.class));
    }

    @Test
    void testDeleteById() {
        // GIVEN
        Long id = 1L;
        Auditoria a = new Auditoria(id, new Cliente(), "Accion", LocalDateTime.now());
        when(auditoriaRepository.findById(id)).thenReturn(Optional.of(a));
        doNothing().when(auditoriaRepository).deleteById(id);

        // WHEN
        assertDoesNotThrow(() -> auditoriaService.deleteById(id));

        // THEN
        verify(auditoriaRepository, times(1)).findById(id);
        verify(auditoriaRepository, times(1)).deleteById(id);
    }
}