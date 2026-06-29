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

import com.ms_comercial.ms_comercial.model.Precio;
import com.ms_comercial.ms_comercial.repository.PrecioRepository;
import com.ms_comercial.ms_comercial.service.PrecioService;
import com.ms_comercial.ms_comercial.DTO.PrecioDTO;

class PrecioServiceTest 
{
    @Mock
    private PrecioRepository precioRepository;

    @InjectMocks
    private PrecioService precioService;

    @BeforeEach
    public void setUp() 
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTotalPrecios() 
    {
        // GIVEN
        Precio pr1 = new Precio();
        pr1.setId(1);
        pr1.setPrecioBase(10.0);

        Precio pr2 = new Precio();
        pr2.setId(2);
        pr2.setPrecioBase(20.0);

        when(precioRepository.findAll()).thenReturn(Arrays.asList(pr1, pr2));

        // WHEN
        List<PrecioDTO> result = precioService.totalPrecios();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(precioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPoridExitoso() 
    {
        // GIVEN
        Integer id = 1;
        Precio pr = new Precio();
        pr.setId(id);
        pr.setPrecioBase(15.5);

        when(precioRepository.findById(id)).thenReturn(Optional.of(pr));

        // WHEN
        PrecioDTO resultado = precioService.buscarPorid(id);

        // THEN
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(15.5, resultado.getPrecio_base());
        verify(precioRepository, times(1)).findById(id);
    }

    @Test
    void testBuscarPoridNotFound() 
    {
        // GIVEN
        Integer id = 99;
        when(precioRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> 
        {
            precioService.buscarPorid(id);
        });
        verify(precioRepository, times(1)).findById(id);
    }

    @Test
    void testGuardarPrecio() 
    {
        // GIVEN
        Precio prIn = new Precio();
        prIn.setPrecioBase(10.0);
            
        Precio prOut = new Precio();
        prOut.setId(1);
        prOut.setPrecioBase(10.0);

        when(precioRepository.save(any(Precio.class))).thenReturn(prOut);

        // WHEN
        Precio resultado = precioService.guardarPrecio(prIn);

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(precioRepository, times(1)).save(any(Precio.class));
    }

    @Test
    void testEliminarPrecioExistente() 
    {
        // GIVEN
        Integer id = 1;
        when(precioRepository.existsById(id)).thenReturn(true);

        // WHEN
        assertDoesNotThrow(() -> precioService.eliminarPrecio(id));

        // THEN
        verify(precioRepository, times(1)).existsById(id);
        // OJO: Si añades precioRepository.deleteById(id) a tu servicio real, debes descomentar la línea de abajo.
        // verify(precioRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarPrecioNoExistente() 
    {
        // GIVEN
        Integer id = 99;
        when(precioRepository.existsById(id)).thenReturn(false);

        // WHEN & THEN
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> 
        {
            precioService.eliminarPrecio(id);
        });

        assertTrue(thrown.getMessage().contains("el capitalismo gano"));
        verify(precioRepository, times(1)).existsById(id);
    }

    @Test
    void testActualizarPrecio() 
    {
        // GIVEN
        Integer id = 1;
        Precio prExistente = new Precio();
        prExistente.setId(id);
        prExistente.setPrecioBase(30.0);

        Precio prNuevosDatos = new Precio();
        prNuevosDatos.setPrecioBase(50.0);

        when(precioRepository.findById(id)).thenReturn(Optional.of(prExistente));
        when(precioRepository.save(any(Precio.class))).thenReturn(prExistente);

        // WHEN
        Precio resultado = precioService.actualizarPrecio(id, prNuevosDatos);

        // THEN
        assertNotNull(resultado);
        assertEquals(50.0, resultado.getPrecioBase());
        verify(precioRepository, times(1)).findById(id);
        verify(precioRepository, times(1)).save(any(Precio.class));
    }
}