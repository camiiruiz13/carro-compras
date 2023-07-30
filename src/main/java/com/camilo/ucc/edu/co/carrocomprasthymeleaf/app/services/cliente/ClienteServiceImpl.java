package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.cliente;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Cliente;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.repository.IClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private IClienteRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        List<Cliente> buscarClientes = repository.findAll();
        return buscarClientes;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        repository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
