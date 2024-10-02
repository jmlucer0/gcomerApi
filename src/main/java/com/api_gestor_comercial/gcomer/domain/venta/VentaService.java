package com.api_gestor_comercial.gcomer.domain.venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    @Autowired
    public VentaService(VentaRepository ventaRepository){
        this.ventaRepository = ventaRepository;
    }


}
