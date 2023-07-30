package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.producto;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Producto;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.repository.IProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductoServiceImpl implements IProductoService{

    @Autowired
    private IProductoRepository productoRepository;

    @Override
    @Transactional(readOnly=true)
    public List<Producto> buscarPorProducto(String term) {
        return productoRepository.buscarPorNombre(term);
    }

    @Override
    @Transactional(readOnly=true)
    public Producto findProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
}
