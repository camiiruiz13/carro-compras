package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.repository;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
}