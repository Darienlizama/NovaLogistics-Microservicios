package com.ms_comercial.ms_comercial.Service; 

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

import com.ms_comercial.ms_comercial.model.Reclamo;
import com.ms_comercial.ms_comercial.repository.ReclamoRepository;
import com.ms_comercial.ms_comercial.service.ReclamoService;
import com.ms_comercial.ms_comercial.DTO.ReclamoDTO;

class ReclamoServiceTest 
{
    @Mock
    private ReclamoRepository reclamoRepository;

    @InjectMocks
    private ReclamoService reclamoService;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll()
    {
        // GIVEN
        Reclamo r1 = new Reclamo();
        r1.setId(1L);
        r1.setTitulo("Titulo 1");
        
        Reclamo r2 = new Reclamo();
        r2.setId(2L);
        r2.setTitulo("Titulo 2");

        when(reclamoRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        // WHEN
        List<ReclamoDTO> result = reclamoService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reclamoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId()
    {
        // GIVEN
        Long id = 1L;
        Reclamo r = new Reclamo();
        r.setId(id);
        r.setTitulo("Titulo");

        when(reclamoRepository.findById(id)).thenReturn(Optional.of(r));

        // WHEN
        ReclamoDTO resultado = reclamoService.findById(id);

        // THEN
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(reclamoRepository, times(1)).findById(id);
    }

    @Test
    void testBuscarPorIdNotFound()
    {
        // GIVEN
        Long id = 99L;
        when(reclamoRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN (Asumimos que lanza excepción como el resto de tu proyecto)
        assertThrows(RuntimeException.class, () -> 
        {
            reclamoService.findById(id);
        });
        verify(reclamoRepository, times(1)).findById(id);
    }

    @Test
    void testGuardarReclamo()
    {
        // GIVEN
        Reclamo reclamoIn = new Reclamo();
        reclamoIn.setTitulo("Titulo");
        reclamoIn.setEstado_reclamo(true);

        Reclamo savedReclamo = new Reclamo();
        savedReclamo.setId(1L);
        savedReclamo.setTitulo("Titulo");
        savedReclamo.setEstado_reclamo(true);

        when(reclamoRepository.save(any(Reclamo.class))).thenReturn(savedReclamo);

        // WHEN (¡Línea cortada arreglada!)
        Reclamo resultado = reclamoService.save(savedReclamo);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(reclamoRepository, times(1)).save(any(Reclamo.class));
    }

    @Test
    void testEliminarReclamoPorIdNoExistente()
    {
        // GIVEN
        Long id = 99L;
        when(reclamoRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reclamoService.deleteById(id);
        });

        assertTrue(exception.getMessage().contains("reclamo no encontrado"));
        verify(reclamoRepository, times(1)).findById(id);
    }
    
    @Test 
    void testActualizarReclamo()
    {
        // GIVEN
        Long id = 1L;
        Reclamo existingReclamo = new Reclamo();
        existingReclamo.setId(id);
        existingReclamo.setTitulo("Titulo");

        Reclamo updatedReclamo = new Reclamo();
        updatedReclamo.setId(id);
        updatedReclamo.setTitulo("Titulo actualizado");

        when(reclamoRepository.findById(id)).thenReturn(Optional.of(existingReclamo));
        when(reclamoRepository.save(any(Reclamo.class))).thenReturn(existingReclamo);

        // WHEN

        Reclamo resultado = reclamoService.updateReclamo(id, updatedReclamo);

        // THEN
        assertNotNull(resultado);
        verify(reclamoRepository, times(1)).findById(id);
        verify(reclamoRepository, times(1)).save(any(Reclamo.class));
    }
}