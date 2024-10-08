package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.venta.FormaDePago;

import java.util.Map;
import java.util.stream.Collectors;

public record DatosActualizarPedido (
        Long id,
        Long clienteId,
        boolean enviado,
        FormaDePago formaDePago,
        Map<Long, Integer> productos
) {

    public DatosActualizarPedido(ActualizarPedidoDTO actualizarPedidoDTO) {
        this(
                actualizarPedidoDTO.id(),
                actualizarPedidoDTO.cliente().getId(),
                actualizarPedidoDTO.enviado(),
                actualizarPedidoDTO.formaDePago(),
                actualizarPedidoDTO.productos().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().getId(),
                                Map.Entry::getValue
                        ))
                );
    }
}
