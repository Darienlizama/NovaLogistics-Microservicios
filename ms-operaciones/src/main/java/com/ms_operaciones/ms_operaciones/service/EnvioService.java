package com.ms_operaciones.ms_operaciones.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import com.ms_operaciones.ms_operaciones.DTO.EnvioDTO;
import com.ms_operaciones.ms_operaciones.DTO.ClienteexternoDTO;
import com.ms_operaciones.ms_operaciones.model.Envio;
import com.ms_operaciones.ms_operaciones.repository.EnvioRepository;
import com.ms_operaciones.ms_operaciones.repository.PaqueteRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private PaqueteRepository paqueteRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Transactional
    public Envio guardarEnvio(EnvioDTO envioDTO) {
        // Llamada al microservicio de clientes
        ClienteexternoDTO cliente = webClientBuilder.build()
                .get()
                .uri("http://ms-usuarios/api/v1/clientes/{id}", envioDTO.getIdcliente())
                .retrieve()
                .bodyToMono(ClienteexternoDTO.class)
                .block();

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        // Convertir DTO a entidad y guardar
        Envio envio = new Envio();
        envio.setIdcliente(envioDTO.getIdcliente());
        envio.setPaquete(envioDTO.getPaquete());
        envio.setDireccionDestino(envioDTO.getDireccionDestino());
        envio.setCiudadDestino(envioDTO.getCiudadDestino());
        envio.setPrecio(envioDTO.getPrecio());
        envio.setEstadoEnvio(true);

        return envioRepository.save(envio);
    }

    @Transactional(readOnly = true)
    public List<Envio> listaEnvios() {
        return envioRepository.findAll();
    }

    @Transactional
    public void eliminarEnvio(Long id) {
        if (!envioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Envio no encontrado con el ID: " + id);
        }
        envioRepository.deleteById(id);
        log.info("Envio eliminado con exito");
    }

    public EnvioDTO convertirDTO(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setId(envio.getId());
        dto.setNumeroGuia(envio.getNumeroGuia());
        // dto.setNombreCliente("Cliente ID: " + envio.getIdcliente());
        if (envio.getPaquete() != null) {
            dto.setDescripcionPaquete(envio.getPaquete().getDescripcion());
            dto.setPesoPaquete(envio.getPaquete().getPeso_kg());
        }
        dto.setDireccionDestino(envio.getDireccionDestino());
        dto.setCiudadDestino(envio.getCiudadDestino());
        dto.setPrecio(envio.getPrecio());
        dto.setFecha(envio.getFecha());
        try {
            ClienteexternoDTO clienterecuperado = webClientBuilder.build()
                    .get()
                    .uri("http://ms-usuarios/api/v1/clientes/{id}", envio.getIdcliente())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(ClienteexternoDTO.class)
                    .block();
            dto.setCliente(clienterecuperado);
        } catch (Exception e) {
            dto.setCliente(null);
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public Envio buscarPorId(Long id) {
        log.info("Buscando Envio por id...");
        return envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envio no encontrado con ID: " + id));
    }

    @Transactional
    public Envio actualizarEnvio(Long id, EnvioDTO datosNuevos) {
        log.info("Actualizando envio con ID: {}", id);
        Envio envioNuevo = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Envio no encontrado con ID: " + id));
        // Mapeamos el nuevo idcliente
        envioNuevo.setCiudadDestino(datosNuevos.getCiudadDestino());
        envioNuevo.setDireccionDestino(datosNuevos.getDireccionDestino());
        envioNuevo.setFecha(datosNuevos.getFecha());
        envioNuevo.setNumeroGuia(datosNuevos.getNumeroGuia());
        envioNuevo.setPrecio(datosNuevos.getPrecio());
        envioNuevo.setIdcliente(datosNuevos.getIdcliente());
        return envioRepository.save(envioNuevo);
    }

    @Transactional
    public Envio actualizarestado(Long id, EnvioDTO datonuevo) {
        log.info("Actualizando el estado con el envio con ID: {}", id);
        Envio envioNuevo = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Estado no encontrado con ID: " + id));
        envioNuevo.setEstadoEnvio(datonuevo.isEstadoEnvio());
        return envioRepository.save(envioNuevo);
    }
}