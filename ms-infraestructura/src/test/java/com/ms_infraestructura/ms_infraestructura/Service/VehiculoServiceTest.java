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

import com.ms_infraestructura.ms_infraestructura.model.Vehiculo;
import com.ms_infraestructura.ms_infraestructura.DTO.VehiculoDTO;
import com.ms_infraestructura.ms_infraestructura.repository.VehiculoRepository;



public class VehiculoServiceTest 
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
        Vehiculo vehiculoIn = new Vehiculo();
        vehiculoIn.setMarca("Toyota");
        vehiculoIn.setModelo("Corolla");
        vehiculoIn.setPatente("ABC123");



        Vehiculo vehiculoOut = new Vehiculo();
        vehiculoOut.setId(1L);
        vehiculoOut.setMarca("Toyota");
        vehiculoOut.setModelo("Corolla");
        vehiculoOut.setPatente("ABC123");

        when(vehiculoRepository.existsByPatente("ABC123")).thenReturn(false);
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculoOut);

        Vehiculo resultado = vehiculoService.guardarVehiculo(vehiculoIn);


        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        asserttEquals("ABC123", resultado.getPatente());
        verify(vehiculoRepository, times(1)).existsByPatente("ABC123");
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }
    @Test
    void testGuardarVehiculoPatenteExistente()
    {
        Vehiculo vehiculoIn=new Vehiculo();
        vehiculoIn.setPatente("ABC123");

        when(vehiculoRepository.existsByPatente("ABC123")).thenReturn(true);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> 
        {
            vehiculoService.guardarVehiculo(vehiculoIn);
        });

        assertEquals("No se puede guardar : El vehiculo ya existe con la patente: ABC123", thrown.getMessage());
        verify(vehiculoRepository, times(1)).existsByPatente("ABC123");
        verify(vehiculoRepository, times(0)).save(any());
      }

      @Test
        void testObtenerVehiculo() 
        {


            when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(new Vehiculo(), new Vehiculo()));


            List<Vehiculo> resultado = vehiculoService.obtenerVehiculo();

            assertNotNull(resultado);
            assertEquals(2, resultado.size());
            verify(vehiculoRepository, times(1)).findAll();

   
        }

        @Test
        void testEliminarVehiculoExistente()
        {
            Long id=1L;
            when(vehiculoRepository.existsById(id)).thenReturn(true);
            doNothing().when(vehiculoRepository).deleteById(id);

            assertDoesNotThrow(() -> vehiculoService.eliminarVehiculo(id));

            verify(vehiculoRepository, times(1)).existsById(id);
            verify(vehiculoRepository, times(1)).deleteById(id);
        }
        @Test
        void testEliminarVehiculoNoExistente()
        {
            Long id=99L;
            when(vehiculoRepository.existsById(id)).thenReturn(false);

            RuntimeException thrown = assertThrows(RuntimeException.class, () -> 
            {
                vehiculoService.eliminarVehiculo(id);
            });

            assertTrue(thrown.getMessage().contains("No se puede eliminar : El vehiculo no se ha encontrado con la ID :" + id));
            verify(vehiculoRepository, times(1)).existsById(id);
            verify(vehiculoRepository, times(0)).deleteById(any());

        }

        @Test
        void testActualizarVehiculoExistente()
        {
            Long id=1L;
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setPatente("ABC123");
            vehiculo.setMarca("Toyota");
            vehiculo.setModelo("Corolla");

            when(vehiculoRepository.findById(id)).thenReturn(Optional.of(vehiculo));

            VehiculoDTO resultado = vehiculoService.obtenerVehiculoPorId(id);

            assertNotNull(resultado);
            assertEquals("ABC123", resultado.getPatente());
            assertEquals("Toyota", resultado.getMarca());
            verify(vehiculoRepository, times(1)).findById(id);
        }
}
