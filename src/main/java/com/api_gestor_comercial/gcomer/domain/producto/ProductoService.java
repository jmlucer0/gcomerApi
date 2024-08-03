package com.api_gestor_comercial.gcomer.domain.producto;

import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }
}
