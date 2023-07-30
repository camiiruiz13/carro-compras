package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.repository;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto, Long> {

    @Query("select p from Producto p where p.nombre like %:term%")
    List<Producto> buscarPorNombre(@Param("term") String term);
}