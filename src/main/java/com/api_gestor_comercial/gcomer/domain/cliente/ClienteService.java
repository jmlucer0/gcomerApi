package com.api_gestor_comercial.gcomer.domain.cliente;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private ClienteRepository  clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> findByActivoTrue(Pageable pageable) {
        return clienteRepository.findByActivoTrue(pageable);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente update(DatosCliente datosCliente) {
        Cliente cliente = clienteRepository.findById(datosCliente.id())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        cliente.actualizarDatosCliente(datosCliente);
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
