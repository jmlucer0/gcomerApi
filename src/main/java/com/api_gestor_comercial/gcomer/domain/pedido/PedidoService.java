package com.api_gestor_comercial.gcomer.domain.pedido;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Page<Pedido> findByEnviadoFalse(Pageable pageable){
        return pedidoRepository.findByEnviadoFalse(pageable);
    }

    public Page<Pedido> findByEnviadoTrue(Pageable pageable){
        return pedidoRepository.findByEnviadoTrue(pageable);
    }

    public Page<Pedido> findByEnviadoDia(Pageable pageable){
        LocalDate today = LocalDate.now();
        return pedidoRepository.findByEnviadoTrueAndFechaDePedido(today, pageable);
    }
    public Page<Pedido> findByNoEnviadoDia(Pageable pageable){
        LocalDate today = LocalDate.now();
        return pedidoRepository.findByEnviadoFalseAndFechaDePedido(today, pageable);
    }

    public Page<Pedido> findEnviadosByMonth(int year, int month, Pageable pageable) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return pedidoRepository.findByEnviadoTrueAndFechaDePedidoBetween(startDate, endDate, pageable);
    }

    public Page<Pedido> findEnviadosByYear(int year, Pageable pageable) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return pedidoRepository.findByEnviadoTrueAndFechaDePedidoBetween(startDate, endDate, pageable);
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
