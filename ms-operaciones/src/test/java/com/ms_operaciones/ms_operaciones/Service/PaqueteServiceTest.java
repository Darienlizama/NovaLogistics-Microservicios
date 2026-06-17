package com.ms_operaciones.ms_operaciones.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.ms_operaciones.ms_operaciones.repository.PaqueteRepository;
import com.ms_operaciones.ms_operaciones.service.PaqueteService;



@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PaqueteServiceTest {

    @InjectMocks
    private PaqueteService paqueteService;


    @Mock
    private PaqueteRepository paqueteRepository;


    private Paquete createPaquete() {
        Paquete paquete = new Paquete();
        paquete.setId(1L);
        paquete.setDescripcion("paquete de prueba");
        paquete.setPeso_kg(4.0);
        return paquete;

} 

@Test
    public void testGuardarPaquete() {
        Paquete paquete = createPaquete();
        when(paqueteRepository.save(paquete)).thenReturn(paquete);
        Paquete saved = paqueteService.guardarPaquetes(paquete);
        assertNotNull(saved);

        Paquete savedPaquete = paqueteService.guardarPaquetes(paquete);

        assertNotNull(savedPaquete);
        assertEquals("paquete de prueba", savedPaquete.getDescripcion());
        assertEquals(4, savedPaquete.getPeso_kg());
    }

    @Test
    public void testListaPaquete() {
        when(paqueteRepository.findAll()).thenReturn(List.of(createPaquete()));

        List<Paquete> paquetes = paqueteService.totalPaquetes();

        assertNotNull(paquetes);
        assertEquals(1, paquetes.size());
    }

    @Test
    public void testBuscarPorId() {
        Paquete paquete = createPaquete();
        when(paqueteRepository.findById(1L)).thenReturn(Optional.of(paquete));

        var dto = paqueteService.buscarPorId(1L);

        assertNotNull(dto);
        assertEquals("paquete de prueba", dto.getDescripcion());
    }

    @Test
    public void testEliminarPaquete() {
        when(paqueteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(paqueteRepository).deleteById(1L);

        paqueteService.eliminarPaquetes(1L);

        verify(paqueteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testActualizarPaquete() {
        Paquete paqueteExistente = createPaquete();
        Paquete datosNuevos = createPaquete();
        datosNuevos.getPeso_kg();

        when(paqueteRepository.findById(1L)).thenReturn(Optional.of(paqueteExistente));
        when(paqueteRepository.save(any(Paquete.class))).thenReturn(paqueteExistente);

        Paquete actualizado = paqueteService.actualizarPaquete(1L, datosNuevos);

        assertNotNull(actualizado);
        assertEquals(4, actualizado.getPeso_kg());
    }
}



