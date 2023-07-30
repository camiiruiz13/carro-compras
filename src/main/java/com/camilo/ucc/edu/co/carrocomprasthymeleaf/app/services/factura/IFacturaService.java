package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.factura;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Factura;

public interface IFacturaService {

    void saveFactura(Factura factura);

    Factura findFacturaById(Long id);

    void deleteFactura(Long id);
}
