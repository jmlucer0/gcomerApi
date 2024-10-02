package com.api_gestor_comercial.gcomer.domain.pedido;

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
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<Page<Pedido>> listarTodosLosPedidos(@PageableDefault(size = 8)Pageable pageable){
        Pageable sortedByIdDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "fechaDePedido")
        );
        return ResponseEntity.ok(pedidoService.findAllByDay(sortedByIdDesc));
    }

    @GetMapping("/no-enviados")
    public ResponseEntity<Page<Pedido>> listarPedidosNoEnviados(@PageableDefault(size = 8)Pageable pageable){
        Pageable sortedByIdDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "fechaDePedido")
        );
        return ResponseEntity.ok(pedidoService.findByNoEnviadoDia(sortedByIdDesc));
    }

    @GetMapping("/enviados")
    public ResponseEntity<Page<Pedido>> listarPedidosEnviados(@PageableDefault(size = 8)Pageable pageable){
        Pageable sortedByIdDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "fechaDePedido")
        );
        return ResponseEntity.ok(pedidoService.findByEnviadoDia(sortedByIdDesc));
    }

    @GetMapping("/enviados?{month}-{year}")
    public ResponseEntity<Page<Pedido>> listarPedidosEnviadosByMes(
            @PathVariable int year,
            @PathVariable int month,
            @PageableDefault(size = 8) Pageable pageable){
        Pageable sortedByIdDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "fechaDePedido")
        );
        return ResponseEntity.ok(pedidoService.findEnviadosByMonth(year, month, sortedByIdDesc));
    }
    @GetMapping("/enviados?{year}")
    public ResponseEntity<Page<Pedido>> listarPedidosEnviadosByYear(
            @PathVariable int year,
            @PageableDefault(size = 8) Pageable pageable){
        Pageable sortedByIdDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "fechaDePedido")
        );
        return ResponseEntity.ok(pedidoService.findEnviadosByYear(year, sortedByIdDesc));
    }

    @PostMapping
    public ResponseEntity<DatosPedido> registrarPedido(@RequestBody CrearPedido datosPedido, UriComponentsBuilder uriComponentsBuilder){
        Pedido pedido = pedidoService.crearPedido(datosPedido);
        DatosPedido nuevoPedido = new DatosPedido(pedido);
        URI url = uriComponentsBuilder.path("/pedido{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(url).body(nuevoPedido);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarPedido(@Valid @RequestBody DatosActualizarPedido datosActualizarPedido){
        Pedido pedido = pedidoService.pedidoUpdate(datosActualizarPedido);
        return ResponseEntity.ok(new ActualizarPedidoDTO(pedido));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPedido(@Valid @PathVariable Long id){
        boolean isDeleted = pedidoService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosPedido> buscarPedidoPorId(@Valid @PathVariable Long id){
        Pedido pedido = pedidoService.findById(id);
        if (pedido != null) {
            var datosPedido = new DatosPedido(pedido);
            return ResponseEntity.ok(datosPedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
