package com.ms_comercial.ms_comercial.Service;

import static org.junit.jupiter.api.Assertions.*;   
import static org.mockito.ArgumentsMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ms_comercial.ms_comercial.model.Reclamo;
import com.ms_comercial.ms_comercial.repository.ReclamoRepository;
import com.ms_comercial.ms_comercial.DTO.ReclamoDTO;


public class ReclamoServiceTest 
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
        List<Reclamo> reclamos = new ArrayList<>();
        Reclamo r1 = new Reclamo(1L, 1L, "Detalle 1", "Titulo 1", true);
        Reclamo r2 = new Reclamo(2L, 2L, "Detalle 2", "Titulo 2", false);

        when(reclamoRepository.findAll()).thenReturn(List.of(r1, r2));

        List<ReclamoDTO> result = reclamoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reclamoRepository, times(1)).findAll();
    }
    @Test
    void testBuscarPorId()
    {
        Long id = 1L;
        Reclamo r = new Reclamo(id, 1L, "Detalle", "Titulo", true);

        when(reclamoRepository.findById(id)).thenReturn(Optional.of(r));

        ReclamoDTO resultado = reclamoService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(reclamoRepository, times(1)).findById(id);
    }
    @Test
    void testBuscarPorIdNotFound()
    {
        Long id = 1L;

        when(reclamoRepository.findById(id)).thenReturn(Optional.empty());

        ReclamoDTO resultado = reclamoService.buscarPorId(id);

        assertNull(resultado);
        verify(reclamoRepository, times(1)).findById(id);
    }
    @Test
    void testGuardarReclamo()
    {
        ReclamoDTO reclamoDTO = new ReclamoDTO();
        reclamoDTO.setId_cliente(1L);
        reclamoDTO.setDetalle_cliente("Detalle");
        reclamoDTO.setTitulo("Titulo");
        reclamoDTO.setEstado_reclamo(true);

        Reclamo reclamo = new Reclamo(null, 1L, "Detalle", "Titulo", true);
        Reclamo savedReclamo = new Reclamo(1L, 1L, "Detalle", "Titulo", true);

        when(reclamoRepository.save(any(Reclamo.class))).thenReturn(savedReclamo);

        ReclamoDTO resultado = reclamoService.guardarReclamo(reclamoDTO);

        assertNotNull(resultado);
        assertEquals(savedReclamo.getId(), resultado.getId());
        verify(reclamoRepository, times(1)).save(any(Reclamo.class));
    }
    @Test
    void testEliminarReclamoPorId()
    {
        Long id = 1L;

        Reclamo reclamo = new Reclamo(id, 1L, "Detalle", "Titulo", true);
        when(reclamoRepository.findById(id)).thenReturn(Optional.of(reclamo));
        doNothing().when(reclamoRepository).deleteById(id);

        assertDoesNotThrow(() -> reclamoService.eliminarReclamoPorId(id));
        verify(reclamoRepository, times(1)).findById(id);
        verify(reclamoRepository, times(1)).deleteById(id);

    }

    @Test 
    void testActualizarReclamo()
    {
        Long id = 1L;
        ReclamoDTO reclamoDTO = new ReclamoDTO();
        reclamoDTO.setId_cliente(1L);
        reclamoDTO.setDetalle_cliente("Detalle actualizado");
        reclamoDTO.setTitulo("Titulo actualizado");
        reclamoDTO.setEstado_reclamo(false);

        Reclamo existingReclamo = new Reclamo(id, 1L, "Detalle", "Titulo", true);
        Reclamo updatedReclamo = new Reclamo(id, 1L, "Detalle actualizado", "Titulo actualizado", false);

        when(reclamoRepository.findById(id)).thenReturn(Optional.of(existingReclamo));
        when(reclamoRepository.save(any(Reclamo.class))).thenReturn(updatedReclamo);

        ReclamoDTO resultado = reclamoService.actualizarReclamo(id, reclamoDTO);

        assertNotNull(resultado);
        assertEquals(updatedReclamo.getId(), resultado.getId());
        assertEquals(updatedReclamo.getDetalle_cliente(), resultado.getDetalle_cliente());
        assertEquals(updatedReclamo.getTitulo(), resultado.getTitulo());
        assertEquals(updatedReclamo.isEstado_reclamo(), resultado.isEstado_reclamo());

        verify(reclamoRepository, times(1)).findById(id);
        verify(reclamoRepository, times(1)).save(any(Reclamo.class));
    }
    
    
}
