package com.ms_infraestructura.ms_infraestructura.Service;

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

import com.ms_infraestructura.ms_infraestructura.DTO.SucursalDTO;
import com.ms_infraestructura.ms_infraestructura.model.Sucursal;
import com.ms_infraestructura.ms_infraestructura.repository.SucursalRepository;
import com.ms_infraestructura.ms_infraestructura.service.SucursalService;

class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarSucursalExitoso() {
        // GIVEN
        Sucursal sucursalIn = new Sucursal(null, "Sucursal Central", "Avenida 1", "Santiago", "Centro");
        Sucursal sucursalOut = new Sucursal(1, "Sucursal Central", "Avenida 1", "Santiago", "Centro");

        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalOut);

        // WHEN
        Sucursal resultado = sucursalService.guardarSucursal(sucursalIn);

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.getId()); // Usamos Integer, no Long
        assertEquals("Sucursal Central", resultado.getNombre());
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }

    @Test
    void testObtenerSucursal() {
        // GIVEN
        Sucursal s1 = new Sucursal(1, "Norte", "Calle 123", "Santiago", "Quilicura");
        Sucursal s2 = new Sucursal(2, "Sur", "Calle 456", "Santiago", "San Bernardo");

        when(sucursalRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        // WHEN
        List<SucursalDTO> resultado = sucursalService.obtenerSucursal();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        // Verificamos que el DTO mapeó bien la dirección
        assertEquals("Calle 123", resultado.get(0).getDireccion()); 
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    void testEliminarSucursalPorIdExistente() {
        // GIVEN
        Integer id = 1;
        when(sucursalRepository.existsById(id)).thenReturn(true);
        doNothing().when(sucursalRepository).deleteById(id);

        // WHEN & THEN
        assertDoesNotThrow(() -> sucursalService.eliminarSucursal(id));
        verify(sucursalRepository, times(1)).existsById(id);
        verify(sucursalRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarSucursalPorIdNoExistente() {
        // GIVEN
        Integer id = 99;
        when(sucursalRepository.existsById(id)).thenReturn(false);

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sucursalService.eliminarSucursal(id);
        });

        // Validamos el mensaje exacto que tienes en tu Service
        assertEquals("No se puede eliminar la sucursal, porque no existe", exception.getMessage());
        verify(sucursalRepository, times(1)).existsById(id);
        verify(sucursalRepository, never()).deleteById(any());
    }

    @Test
    void testObtenerSucursalPorIdExitoso() {
        // GIVEN
        Integer id = 1;
        Sucursal sucursal = new Sucursal(id, "Este", "Avenida Los Leones", "Santiago", "Providencia");

        when(sucursalRepository.findById(id)).thenReturn(Optional.of(sucursal));

        // WHEN
        SucursalDTO resultado = sucursalService.obtenerSucursalPorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Avenida Los Leones", resultado.getDireccion());
        verify(sucursalRepository, times(1)).findById(id);
    }

    @Test
    void testActualizarSucursal() {
        // GIVEN
        Integer id = 1;
        Sucursal sucursalExistente = new Sucursal(id, "Oeste", "Calle Vieja", "Santiago", "Maipú");
        Sucursal sucursalNuevosDatos = new Sucursal(null, "Oeste", "Calle Nueva", "Santiago", "Maipú");
        
        when(sucursalRepository.findById(id)).thenReturn(Optional.of(sucursalExistente));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalExistente);

        // WHEN
        Sucursal resultado = sucursalService.actualizarSucursal(id, sucursalNuevosDatos);

        // THEN
        assertNotNull(resultado);
        // Validamos que se haya actualizado la dirección correctamente
        assertEquals("Calle Nueva", resultado.getDireccion());
        verify(sucursalRepository, times(1)).findById(id);
        verify(sucursalRepository, times(1)).save(any(Sucursal.class));
    }
}