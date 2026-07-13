package com.example.ms_usuarios.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ms_usuarios.model.Personal;

@Repository
public interface PersonalRepository extends JpaRepository<Personal,Long>{  
} 