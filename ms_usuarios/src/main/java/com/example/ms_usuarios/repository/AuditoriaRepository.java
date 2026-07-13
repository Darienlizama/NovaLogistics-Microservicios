package com.example.ms_usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ms_usuarios.model.Auditoria;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria,Long > {

    
} 
