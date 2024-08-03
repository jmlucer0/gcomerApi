package com.api_gestor_comercial.gcomer.domain.cliente;

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
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<Page<Cliente>> listarClientes(@PageableDefault(size = 5)Pageable pageable){
        Pageable sortedByIdDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return ResponseEntity.ok(clienteService.findByActivoTrue(sortedByIdDesc));
    }

    @PostMapping
    public ResponseEntity<DatosCliente> registrarCliente(@Valid @RequestBody DatosCliente datosCliente, UriComponentsBuilder uriComponentsBuilder) {
        Cliente cliente = clienteService.save(new Cliente(datosCliente));
        DatosCliente nuevoCliente = new DatosCliente(cliente);
        URI url = uriComponentsBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(url).body(nuevoCliente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosCliente> actualizarCliente(@Valid @RequestBody DatosCliente datosCliente) {
        Cliente cliente = clienteService.update(datosCliente);
        return ResponseEntity.ok(new DatosCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        boolean isDeleted = clienteService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosCliente> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        if (cliente != null) {
            var datosCliente = new DatosCliente(cliente);
            return ResponseEntity.ok(datosCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
