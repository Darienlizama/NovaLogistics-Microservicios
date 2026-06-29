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

import com.ms_infraestructura.ms_infraestructura.DTO.VehiculoDTO;
import com.ms_infraestructura.ms_infraestructura.model.Vehiculo;
import com.ms_infraestructura.ms_infraestructura.repository.VehiculoRepository;
import com.ms_infraestructura.ms_infraestructura.service.VehiculoService;

class VehiculoServiceTest 
{
    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @BeforeEach
    public void setUp() 
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarVehiculoExitoso()
    {
        // GIVEN
        Vehiculo vehiculoIn = new Vehiculo();
        vehiculoIn.setMarca("Toyota");
        vehiculoIn.setModelo("Corolla");
        vehiculoIn.setPatente("ABC123");

        Vehiculo vehiculoOut = new Vehiculo();
        vehiculoOut.setId(1); // Usamos Integer, no Long
        vehiculoOut.setMarca("Toyota");
        vehiculoOut.setModelo("Corolla");
        vehiculoOut.setPatente("ABC123");

        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculoOut);

        // WHEN
        Vehiculo resultado = vehiculoService.guardarVehiculo(vehiculoIn);

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("ABC123", resultado.getPatente());
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    void testObtenerVehiculo() 
    {
        // GIVEN
        Vehiculo v1 = new Vehiculo();
        v1.setId(1); v1.setPatente("ABC123");
        
        Vehiculo v2 = new Vehiculo();
        v2.setId(2); v2.setPatente("XYZ789");

        when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(v1, v2));

        // WHEN
        List<VehiculoDTO> resultado = vehiculoService.obtenerVehiculo();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        // Verificamos que el mapeo a DTO funcione
        assertEquals("ABC123", resultado.get(0).getPatente());
        verify(vehiculoRepository, times(1)).findAll();
    }

    @Test
    void testEliminarVehiculoExistente()
    {
        // GIVEN
        Integer id = 1;
        when(vehiculoRepository.existsById(id)).thenReturn(true);
        doNothing().when(vehiculoRepository).deleteById(id);

        // WHEN & THEN
        assertDoesNotThrow(() -> vehiculoService.eliminarVehiculo(id));

        verify(vehiculoRepository, times(1)).existsById(id);
        verify(vehiculoRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarVehiculoNoExistente()
    {
        // GIVEN
        Integer id = 99;
        when(vehiculoRepository.existsById(id)).thenReturn(false);

        // WHEN & THEN
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> 
        {
            vehiculoService.eliminarVehiculo(id);
        });

        assertTrue(thrown.getMessage().contains("No se puede eliminar : El vehiculo no se ha encontrado con la ID :" + id));
        verify(vehiculoRepository, times(1)).existsById(id);
        verify(vehiculoRepository, never()).deleteById(any());
    }

    @Test
    void testBuscarPorIdExitoso()
    {
        // GIVEN
        Integer id = 1;
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(id);
        vehiculo.setPatente("ABC123");
        vehiculo.setMarca("Toyota");

        when(vehiculoRepository.findById(id)).thenReturn(Optional.of(vehiculo));

        // WHEN
        // Tu método se llama buscarPorId, no obtenerVehiculoPorId
        VehiculoDTO resultado = vehiculoService.buscarPorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals("ABC123", resultado.getPatente());
        assertEquals("Toyota", resultado.getMarca());
        verify(vehiculoRepository, times(1)).findById(id);
    }

    @Test
    void testActualizarVehiculoExistente()
    {
        // GIVEN
        Integer id = 1;
        Vehiculo vehiculoExistente = new Vehiculo();
        vehiculoExistente.setId(id);
        vehiculoExistente.setPatente("OLD123");

        Vehiculo nuevosDatos = new Vehiculo();
        nuevosDatos.setPatente("NEW999");
        nuevosDatos.setMarca("Nissan");
        nuevosDatos.setModelo("Sentra");

        when(vehiculoRepository.findById(id)).thenReturn(Optional.of(vehiculoExistente));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculoExistente);

        // WHEN
        Vehiculo resultado = vehiculoService.actualizarVehiculo(id, nuevosDatos);

        // THEN
        assertNotNull(resultado);
        // Comprobamos que el vehiculo haya actualizado su patente
        assertEquals("NEW999", resultado.getPatente()); 
        verify(vehiculoRepository, times(1)).findById(id);
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }
}