package com.api_gestor_comercial.gcomer.domain.pedido;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Page<Pedido> findByEnviadoTrue(Pageable pageable){
        return pedidoRepository.findByEnviadoTrue(pageable);
    }

    public Page<Pedido> findByEnviadoFalse(Pageable pageable){
        return pedidoRepository.findByEnviadoFalse(pageable);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido update(DatosActualizarPedido datosActualizarPedido) {
        Pedido pedido = pedidoRepository.findById(datosActualizarPedido.id())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
        pedido.actualizarPedido(datosActualizarPedido);
        return pedido;
    }

    @Transactional
    public boolean delete(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }
}
