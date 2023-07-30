package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.producto;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Factura;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Producto;

import java.util.List;

public interface IProductoService {

    List<Producto> buscarPorProducto(String term);

    Producto findProductoById(Long id);
}
