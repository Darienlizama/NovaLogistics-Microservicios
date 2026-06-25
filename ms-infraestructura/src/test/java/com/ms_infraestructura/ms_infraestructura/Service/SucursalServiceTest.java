package com.ms_infraestructura.ms_infraestructura.Service;

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

import com.ms_infraestructura.ms_infraestructura.model.Sucursal;
import com.ms_infraestructura.ms_infraestructura.repository.SucursalRepository;
import com.ms_infraestructura.ms_infraestructura.DTO.SucursalDTO;


public class SucursalServiceTest 
{
    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @BeforeEach
    public void setUp() 
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarSucursalExitoso()
    {
        Sucursal sucursalIn = new Sucursal();
        sucursalIn.setNombre("Sucursal 1");
        sucursalIn.setDireccion("Calle Falsa 123");

        Sucursal sucursalOut = new Sucursal();
        sucursalOut.setId(1L);
        sucursalOut.setNombre("Sucursal 1");
        sucursalOut.setDireccion("Calle Falsa 123");

        when(sucursalRepository.existsByNombre("Sucursal 1")).thenReturn(false);
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalOut);

        Sucursal resultado = sucursalService.guardarSucursal(sucursalIn);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sucursal 1", resultado.getNombre());
        assertEquals("Calle Falsa 123", resultado.getDireccion());
        verify(sucursalRepository, times(1)).existsByNombre("Sucursal 1");
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }


    @Test
    void testGuardarSucursalConNombreExistente()
    {
        Sucursal sucursalIn = new Sucursal();
        sucursalIn.setNombre("Sucursal Existente");
        sucursalIn.setDireccion("Calle Falsa 456");

        when(sucursalRepository.existsByNombre("Sucursal Existente")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sucursalService.guardarSucursal(sucursalIn);
        });
        assertEquals("La sucursal con el nombre 'Sucursal Existente' ya existe.", exception.getMessage());
        verify(sucursalRepository, times(1)).existsByNombre("Sucursal Existente");
        verify(sucursalRepository, times(0)).save(any());
    
    }
    @Test
    void testObtenerSucursal()
    {
     when(sucursalRepository.findAll()).thenReturn(Arrays.asList(new Sucursal(), new Sucursal()));
     
        List<Sucursal> resultado = sucursalService.obtenerSucursal();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    void eliminarSucursalPorIdExistente()
    {
        Long id = 1L;
        when(sucursalRepository.findById(id)).thenReturn(true);
        doNothing().when(sucursalRepository).deleteById(id);

        assertDoesNotThrow(() -> sucursalService.eliminarSucursal(id));
        verify(sucursalRepository, times(1)).findById(id);
        verify(sucursalRepository, times(1)).deleteById(id);

    }
    @Test
    void eliminarSucursalPorIdNoExistente()
    {
        Long id=99L;
        when(sucursalRepository.findById(id)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
        {
            sucursalService.eliminarSucursal(id);
        });

        assertTrue(thrown.getMessage().contains("No se puede eliminar: La sucursal con el id " + id + " no existe."));
        verify(sucursalRepository, times(1)).findById(id);
        verify(sucursalRepository, times(0)).deleteById(any());
    }

    @Test
    void testActualizarSucursalExistente()
    {
        Long id=1L;
        Sucursal sucursal =new Sucursal();
        sucursal.setNombre("Sucursal 1");
        sucursal.setDireccion("Calle Falsa 123");

        when(sucursalRepository.findById(id)).thenReturn(Optional.of(sucursal));
        
        SucursalDTO resultado = sucursalService.ObtenerSucursalPorId(id);

        assertNotNull(resultado);
        assertEquals("Sucursal 1", resultado.getNombre());
        assertEquals("Calle Falsa 123", resultado.getDireccion());
        verify(sucursalRepository, times(1)).findById(id);
    }
}
