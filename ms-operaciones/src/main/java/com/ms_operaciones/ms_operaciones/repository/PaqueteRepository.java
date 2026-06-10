package com.ms_operaciones.ms_operaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms_operaciones.ms_operaciones.model.Paquete;
@Repository
public interface PaqueteRepository extends JpaRepository<Paquete,Long>{

}