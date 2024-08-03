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

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Cliente>> listarClientes(@PageableDefault(size = 5)Pageable pageable){
        Pageable sortedByDateDesc = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return ResponseEntity.ok(clienteRepository.findByActivoTrue(sortedByDateDesc));
    }

    @PostMapping
    public ResponseEntity<DatosCliente> registrarCliente (@RequestBody  DatosCliente datosCliente, UriComponentsBuilder uriComponentsBuilder){
        Cliente cliente = clienteRepository.save(new Cliente(datosCliente));
        DatosCliente nuevoCliente = new DatosCliente(cliente);
        URI url = uriComponentsBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(url).body(nuevoCliente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarCliente(@RequestBody DatosCliente datosCliente){
        Cliente cliente = clienteRepository.getReferenceById(datosCliente.id());
        cliente.actualizarDatosCliente(datosCliente);
        return ResponseEntity.ok(new DatosCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarCliente(@PathVariable Long id){
        if (clienteRepository.getReferenceById(id) != null && clienteRepository.existsById(id)){
            Cliente cliente = clienteRepository.getReferenceById(id);
            cliente.eliminarCliente(cliente);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosCliente> buscarClientePorId(@PathVariable Long id){
        Cliente cliente = clienteRepository.getReferenceById(id);
        var datosCliente = new DatosCliente(cliente);
        return ResponseEntity.ok(datosCliente);
    }

}
