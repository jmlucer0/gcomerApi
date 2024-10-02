package com.api_gestor_comercial.gcomer.domain.cliente;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository  clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> findByActivoTrue(Pageable pageable) {
        return clienteRepository.findByActivoTrue(pageable);
    }

    public Cliente save(DatosCliente cliente) {
        Cliente nuevoCliente = new Cliente(cliente);
        if (cliente != null){
            nuevoCliente.setNombre(cliente.nombre());
            nuevoCliente.setTelefono(cliente.telefono());
            nuevoCliente.setDireccion(cliente.direccion());
        }
        clienteRepository.save(nuevoCliente);
        return nuevoCliente;
    }

    public Page<DatosCliente> findByNombre(Pageable pageable, String nombre){
        if (nombre != null && !nombre.isEmpty()) {
            Page<Cliente> clientes = clienteRepository.findByNombreContaining(nombre, pageable);
            return clientes.map(DatosCliente::new);
        } else {
            return Page.empty(pageable);
        }
    }

    @Transactional
    public Cliente update(DatosCliente datosCliente) {
        Cliente cliente = clienteRepository.findById(datosCliente.id())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        if(datosCliente.nombre() !=null){
            cliente.setNombre(datosCliente.nombre());
        }
        if (datosCliente.direccion() != null){
            cliente.setDireccion(datosCliente.direccion());
        }
        if (datosCliente.telefono() != null){
            cliente.setTelefono(datosCliente.telefono());
        }
        return cliente;
    }

    @Transactional
    public boolean delete(Long id) {
        if (clienteRepository.existsById(id)) {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            cliente.eliminarCliente(cliente);
            return true;
        } else {
            return false;
        }
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }


}
