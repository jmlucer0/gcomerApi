package com.api_gestor_comercial.gcomer.domain.producto;

import com.api_gestor_comercial.gcomer.domain.cliente.DatosCliente;
import jakarta.validation.Valid;
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

    private ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Producto>> listarProductos(@PageableDefault(size = 5)Pageable pageable){
        Pageable sortedByDateDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return ResponseEntity.ok(productoRepository.findAll(sortedByDateDesc));
    }

    @PostMapping
    public ResponseEntity<DatosProducto> registrarProducto(@Valid@RequestBody DatosProducto datosProducto, UriComponentsBuilder uriComponentsBuilder){
        Producto producto = productoRepository.save(new Producto(datosProducto));
        DatosProducto nuevoProducto = new DatosProducto(producto);
        URI url = uriComponentsBuilder.path("/producto{id}").buildAndExpand(producto.getId()).toUri();
        return ResponseEntity.created(url).body(nuevoProducto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarProducto(@Valid @RequestBody DatosProducto datosProducto){
        Producto producto = productoRepository.getReferenceById(datosProducto.id());
        producto.actualizarDatosProducto(datosProducto);
        return ResponseEntity.ok(new DatosProducto(producto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarProducto(@PathVariable Long id){
        if (productoRepository.getReferenceById(id) != null && productoRepository.existsById(id)){
            Producto producto = productoRepository.getReferenceById(id);
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosProducto> buscarProductoPorID(@Valid @PathVariable Long id){
        Producto producto = productoRepository.getReferenceById(id);
        var datosProducto = new DatosProducto(producto);
        return ResponseEntity.ok(datosProducto);
    }
}
