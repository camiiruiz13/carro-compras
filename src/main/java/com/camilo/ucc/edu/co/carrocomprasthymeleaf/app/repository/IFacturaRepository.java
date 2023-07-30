package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.repository;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaRepository extends JpaRepository<Factura, Long> {
}