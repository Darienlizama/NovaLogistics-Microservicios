package com.example.ms_usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ms_usuarios.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Para buscar si un RUT ya existe antes de registrar
    boolean existsByRut(String rut);
}
