package com.api_gestor_comercial.gcomer.domain.producto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<Page<Producto>> listarProductos(@PageableDefault(size = 5) Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return ResponseEntity.ok(productoService.findAll(sortedByIdDesc));
    }

    @PostMapping
    public ResponseEntity<DatosProducto> registrarProducto(@Valid @RequestBody DatosProducto datosProducto, UriComponentsBuilder uriComponentsBuilder) {
        Producto producto = productoService.save(new Producto(datosProducto));
        DatosProducto nuevoProducto = new DatosProducto(producto);
        URI url = uriComponentsBuilder.path("/producto/{id}").buildAndExpand(producto.getId()).toUri();
        return ResponseEntity.created(url).body(nuevoProducto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosProducto> actualizarProducto(@Valid @RequestBody DatosProducto datosProducto) {
        Producto producto = productoService.update(datosProducto);
        return ResponseEntity.ok(new DatosProducto(producto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        boolean isDeleted = productoService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosProducto> buscarProductoPorID(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto != null) {
            var datosProducto = new DatosProducto(producto);
            return ResponseEntity.ok(datosProducto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}