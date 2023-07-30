package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.factura;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.models.entities.Factura;
import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.repository.IFacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FacturaServiceImpl implements IFacturaService{

    @Autowired
    IFacturaRepository facturaRepository;

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturaRepository.save(factura);

    }

    @Override
    @Transactional(readOnly=true)
    public Factura findFacturaById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturaRepository.deleteById(id);
    }
}
