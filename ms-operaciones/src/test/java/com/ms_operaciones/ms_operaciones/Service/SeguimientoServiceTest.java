package com.ms_operaciones.ms_operaciones.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.ms_operaciones.ms_operaciones.model.Envio;
import com.ms_operaciones.ms_operaciones.model.Paquete;
import com.ms_operaciones.ms_operaciones.model.Seguimiento;
import com.ms_operaciones.ms_operaciones.repository.EnvioRepository;
import com.ms_operaciones.ms_operaciones.repository.PaqueteRepository;
import com.ms_operaciones.ms_operaciones.repository.SeguimientoRepository;
import com.ms_operaciones.ms_operaciones.service.PaqueteService;
import com.ms_operaciones.ms_operaciones.service.SeguimientoService;



@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SeguimientoServiceTest {

    @InjectMocks
    private SeguimientoService seguimientoService;

    @Mock
    private SeguimientoRepository seguimientoRepository;

    @Mock
    private EnvioRepository envioRepository;

    @Mock
    private PaqueteRepository paqueteRepository;


    private Seguimiento createSeguimiento() {
    // Crear paquete
   
        Paquete paquete = new Paquete();
        paquete.setId(1L);
        paquete.setDescripcion("paquete de prueba");
        paquete.setPeso_kg(4.0);
        

    // Crear envío
    Envio envio = new Envio();
    envio.setId(1L);
    envio.setNumeroGuia("NVL-12345");
    envio.setPaquete(paquete);
    envio.setDireccionDestino("Av. Principal 123");
    envio.setCiudadDestino("Santiago");
    envio.setPrecio(5000.0);

    // Crear seguimiento
    Seguimiento seguimiento = new Seguimiento();
    seguimiento.setId(1L);
    seguimiento.setEnvio(envio);
    seguimiento.setEstado("bien");
    seguimiento.setUbicacion("Peñaflor");
    seguimiento.setFecha_hora(LocalDateTime.now()); // mejor usar fecha/hora real

    return seguimiento;
}

 

@Test
public void testGuardarseguimiento() {
    Seguimiento seguimientos = createSeguimiento();

    // Mockear que el envío existe
    when(envioRepository.existsById(seguimientos.getEnvio().getId())).thenReturn(true);

    // Mockear guardado de seguimiento
    when(seguimientoRepository.save(seguimientos)).thenReturn(seguimientos);

    Seguimiento savedSeguimiento = seguimientoService.agregarSeguimiento(seguimientos);

    assertNotNull(savedSeguimiento);
    assertEquals(seguimientos.getEnvio().getNumeroGuia(), savedSeguimiento.getEnvio().getNumeroGuia());
    assertEquals("Peñaflor", savedSeguimiento.getUbicacion());
}

    @Test
    public void testListaPaquete() {
        when(seguimientoRepository.findAll()).thenReturn(List.of(createSeguimiento()));

        List<Seguimiento> seguimientos = seguimientoService.listarSeguimiento();

        assertNotNull(seguimientos);
        assertEquals(1, seguimientos.size());
    }

    @Test
    public void testBuscarPorId() {
        Seguimiento seguimientos = createSeguimiento();
        when(seguimientoRepository.findById(1L)).thenReturn(Optional.of(seguimientos));

        var dto = seguimientoService.buscarPorId(1L);

        assertNotNull(dto);
        assertEquals("Peñaflor", dto.getUbicacion());
    }

    @Test
    public void testEliminarPaquete() {
        when(seguimientoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(seguimientoRepository).deleteById(1L);

        seguimientoService.eliminarSeguimiento(1L);

        verify(seguimientoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testActualizarPaquete() {
        Seguimiento seguimientoExistente = createSeguimiento();
        Seguimiento datosNuevos = createSeguimiento();
        datosNuevos.setUbicacion("peñaflor");

        when(seguimientoRepository.findById(1L)).thenReturn(Optional.of(seguimientoExistente));
        when(seguimientoRepository.save(any(Seguimiento.class))).thenReturn(seguimientoExistente);

        Seguimiento actualizado = seguimientoService.actualizarSeguimiento(1L, datosNuevos);

        assertNotNull(actualizado);
        assertEquals("peñaflor", actualizado.getUbicacion());
    }
}

