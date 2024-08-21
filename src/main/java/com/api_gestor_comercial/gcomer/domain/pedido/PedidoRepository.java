package com.api_gestor_comercial.gcomer.domain.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByEnviadoTrue(Pageable pageable);

    Page<Pedido> findByEnviadoFalse(Pageable pageable);

    Page<Pedido> findByEnviadoTrueAndFechaDePedido(LocalDate today, Pageable pageable);

    Page<Pedido> findByEnviadoFalseAndFechaDePedido(LocalDate today, Pageable pageable);

    Page<Pedido> findByEnviadoTrueAndFechaDePedidoBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Pedido> findAllByFechaDePedido(LocalDate today, Pageable pageable);
}
