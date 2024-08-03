package com.api_gestor_comercial.gcomer.domain.producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Producto>> listarProductos(@PageableDefault(size = 5)Pageable pageable){
        return ResponseEntity.ok(productoRepository.findAll(pageable));
    }

//    @PostMapping
//    @Transactional
//    public ResponseEntity<DatosProducto> registrarProducto(@RequestBody DatosProducto datosProducto, UriComponentsBuilder uriComponentsBuilder){
//        Producto producto = productoRepository.save(new Producto(datosProducto));
//    }
}
