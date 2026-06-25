package com.ms_comercial.ms_comercial.Service;

import static org.junit.jupiter.api.Assertions.*;   
import static org.mockito.ArgumentsMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
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

public class PrecioServiceTest 
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
    void testFindAll() 
    {
        List<Precio> precios = new ArrayList<>();
        Precio pr1 = new Precio(1L, 10);
        Precio pr2 = new Precio(2L, 20);

        when(precioRepository.findAll()).thenReturn(pr1,pr2);

        List<PrecioDTO> result = precioService.findAll();

        assertNotNull(resultado);
        assertEquals(2, result.size());
        verify(precioRepository, times(1)).findAll();

    }
    @Test
    void testBuscarPorId() 
    {
        Long id = 1L;
        Precio pr = new Precio(id, 10);

        when(precioRepository.findById(id)).thenReturn(Optional.of(pr));

        PrecioDTO resultado = precioService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(10, resultado.getPrecio());
        verify(precioRepository, times(1)).findById(id);
    }
    @Test
    void testBuscarPorIdNotFound() 
    {
        Long id = 99L;

        when(precioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
        {
            precioService.buscarPorId(id);

        });
        verify(precioRepository, times(1)).findById(id);
    }
    @Test
    void testGuardar() 
    {
        Precio pr = new PrecioServiceTest(null, 10);
            
        when(precioRepository.save(any(Precio.class))).thenReturn(new Precio(1L, 10));

        Precio resultado = precioService.guardar(pr);

        assertNotNull(resultado);
        verify(precioRepository, times(1)).save(any(Precio.class));
    }


    @Test
    void testEliminar() 
    {
        Long id = 1L;

        Precio pr = new Precio(id, 10);
        when(precioRepository.findById(id)).thenReturn(Optional.of(pr));
        doNothing().when(precioRepository).deleteById(id);

        assertDoesNotThrow(() -> precioService.eliminar(id));

        verify(precioRepository, times(1)).findById(id);
        verify(precioRepository, times(1)).deleteById(id);
    }

    @Test
    void testActualizar() 
    {
        Long id = 1L;
        Precio prExistente = new Precio(null, 30);

        Precio prNuevosDatos = new Precio(null, 10);

        when(precioRepository.findById(id)).thenReturn(Optional.of(prExistente));
        when(precioRepository.save(any(Precio.class))).thenReturn(prNuevosDatos);

        Precio resultado = precioService.actualizar(id, prNuevosDatos);

        assertNotNull(resultado);
        verify(precioRepository, times(1)).findById(id);
        verify(precioRepository, times(1)).save(any(Precio.class));

    }


}
