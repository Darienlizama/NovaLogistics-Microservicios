package com.ms_operaciones.ms_operaciones.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ms_operaciones.ms_operaciones.DTO.EnvioDTO;
import com.ms_operaciones.ms_operaciones.client.UsuarioClient;
import com.ms_operaciones.ms_operaciones.model.Envio;
import com.ms_operaciones.ms_operaciones.model.Paquete;
import com.ms_operaciones.ms_operaciones.repository.EnvioRepository;
import com.ms_operaciones.ms_operaciones.repository.PaqueteRepository;
import com.ms_operaciones.ms_operaciones.service.EnvioService;

@ActiveProfiles("test")

@ExtendWith(MockitoExtension.class)
public class EnvioServiceTest {

    @InjectMocks
    private EnvioService envioService;

    @Mock
    private EnvioRepository envioRepository;

    @Mock
    private PaqueteRepository paqueteRepository;

    @Mock
    private UsuarioClient usuarioClient;

    private Envio createEnvio() {
        Paquete paquete = new Paquete();
        paquete.setId(1L);
        paquete.setDescripcion("Paquete de prueba");
        paquete.setPeso_kg(2.5);

        Envio envio = new Envio();
        envio.setId(1L);
        envio.setNumeroGuia("NVL-12345");
        envio.setIdcliente(1L);
        envio.setPaquete(paquete);
        envio.setDireccionDestino("Av. Principal 123");
        envio.setCiudadDestino("Santiago");
        envio.setPrecio(5000.0);
        return envio;
    }

   @Test
public void testGuardarEnvio() {
    Paquete paquete = new Paquete();
    paquete.setId(1L);
    paquete.setDescripcion("Paquete de prueba");
    paquete.setPeso_kg(2.5);

    EnvioDTO envioDTO = new EnvioDTO();
    envioDTO.setId(1L);
    envioDTO.setNumeroGuia("NVL-12345");
    envioDTO.setIdcliente(1L);
    envioDTO.setPaquete(paquete);
    envioDTO.setDireccionDestino("Av. Principal 123");
    envioDTO.setCiudadDestino("Santiago");
    envioDTO.setPrecio(5000.0);

    Envio envio = new Envio();
    envio.setId(1L);
    envio.setNumeroGuia("NVL-12345");
    envio.setIdcliente(1L);
    envio.setPaquete(paquete);
    envio.setDireccionDestino("Av. Principal 123");
    envio.setCiudadDestino("Santiago");
    envio.setPrecio(5000.0);

    // Mockear paqueteRepository, no envioRepository
    when(paqueteRepository.existsById(paquete.getId())).thenReturn(true);

    // Mockear guardado de envío
    when(envioRepository.save(any(Envio.class))).thenReturn(envio);

    Envio savedEnvio = envioService.guardarEnvio(envioDTO);

    assertNotNull(savedEnvio);
    assertEquals("NVL-12345", savedEnvio.getNumeroGuia());
    assertEquals(5000.0, savedEnvio.getPrecio());
}


    @Test
    public void testListaEnvios() {
        when(envioRepository.findAll()).thenReturn(List.of(createEnvio()));

        List<Envio> envios = envioService.listaEnvios();

        assertNotNull(envios);
        assertEquals(1, envios.size());
    }

    @Test
    public void testBuscarPorId() {
        Envio envio = createEnvio();
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        var dto = envioService.buscarPorId(1L);

        assertNotNull(dto);
        assertEquals("NVL-12345", dto.getNumeroGuia());
    }

    @Test
    public void testEliminarEnvio() {
        when(envioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(envioRepository).deleteById(1L);

        envioService.eliminarEnvio(1L);

        verify(envioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testActualizarEnvio() {

        Paquete paquete = new Paquete();
        paquete.setId(1L);
        paquete.setDescripcion("Paquete de prueba");
        paquete.setPeso_kg(2.5);

        Envio envioExistente = createEnvio();
        EnvioDTO datosNuevos = new EnvioDTO();
        datosNuevos.setId(1L);
        datosNuevos.setNumeroGuia("NVL-12345");
        datosNuevos.setIdcliente(1L);
        datosNuevos.setPaquete(paquete);
        datosNuevos.setDireccionDestino("Av. Principal 123");
        datosNuevos.setCiudadDestino("Santiago");
        datosNuevos.setPrecio(8000.0);

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envioExistente));
        when(envioRepository.save(any(Envio.class))).thenReturn(envioExistente);

        Envio actualizado = envioService.actualizarEnvio(1L, datosNuevos);

        assertNotNull(actualizado);
        assertEquals(8000.0, actualizado.getPrecio());
    }
}
