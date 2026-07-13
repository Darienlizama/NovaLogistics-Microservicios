package com.example.ms_usuarios.service;
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
import com.example.ms_usuarios.DTO.ClienteDTO;
import com.example.ms_usuarios.model.Cliente;
import com.example.ms_usuarios.repository.ClienteRepository;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarClienteExitoso() {
        // GIVEN
        Cliente clienteIn = new Cliente();
        clienteIn.setRut("12345678K");
        clienteIn.setNombre("Juan");
        clienteIn.setApellido("Perez");
        clienteIn.setCorreo("juan@mail.com");
        clienteIn.setTelefono("912345678");

        Cliente clienteOut = new Cliente();
        clienteOut.setId(1L);
        clienteOut.setRut("12345678K");
        clienteOut.setNombre("Juan");
        clienteOut.setApellido("Perez");
        clienteOut.setCorreo("juan@mail.com");
        clienteOut.setTelefono("912345678");

        when(clienteRepository.existsByRut("12345678K")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteOut);

        // WHEN
        Cliente resultado = clienteService.guardarCliente(clienteIn);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("12345678K", resultado.getRut());
        verify(clienteRepository, times(1)).existsByRut("12345678K");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testGuardarClienteRutDuplicado() {
        // GIVEN
        Cliente clienteIn = new Cliente();
        clienteIn.setRut("12345678K");

        when(clienteRepository.existsByRut("12345678K")).thenReturn(true);

        // WHEN & THEN
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            clienteService.guardarCliente(clienteIn);
        });
        
        assertEquals("RUT duplicado", thrown.getMessage());
        verify(clienteRepository, times(1)).existsByRut("12345678K");
        verify(clienteRepository, times(0)).save(any());
    }

    @Test
    void testObtenerTodos() {
        // GIVEN
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(new Cliente(), new Cliente()));

        // WHEN
        List<Cliente> resultado = clienteService.obtenerTodos();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testEliminarClienteExitoso() {
        // GIVEN
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(id);

        // WHEN
        assertDoesNotThrow(() -> clienteService.eliminarCliente(id));

        // THEN
        verify(clienteRepository, times(1)).existsById(id);
        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminarClienteNoEncontrado() {
        // GIVEN
        Long id = 99L;
        when(clienteRepository.existsById(id)).thenReturn(false);

        // WHEN & THEN
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            clienteService.eliminarCliente(id);
        });
        
        assertTrue(thrown.getMessage().contains("Cliente no encontrado con el ID"));
        verify(clienteRepository, times(1)).existsById(id);
        verify(clienteRepository, times(0)).deleteById(any());
    }

    @Test
    void testObtenerClientePorIdExitoso() {
        // GIVEN
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setRut("12345678K");
        cliente.setNombre("Ana");
        cliente.setApellido("Gomez");
        cliente.setCorreo("ana@mail.com");
        cliente.setTelefono("987654321");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        // WHEN
        ClienteDTO resultado = clienteService.obtenerClientePorId(id);

        // THEN
        assertNotNull(resultado);
        assertEquals("12345678K", resultado.getRut());
        assertEquals("Ana", resultado.getNombre());
        verify(clienteRepository, times(1)).findById(id);
    }
}