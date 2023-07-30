package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.cliente;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    List<Cliente> findAll();

    Page<Cliente> findAll(Pageable pageable);

    void save(Cliente cliente);

    Cliente findById(Long id);

    void  delete(Long id);
}
