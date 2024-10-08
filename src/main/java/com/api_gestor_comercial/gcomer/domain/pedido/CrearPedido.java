package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.venta.FormaDePago;

import java.util.Map;

public record CrearPedido(
        Long clienteId,
        FormaDePago formaDePago,
        Map<Long, Integer> productos
) {
}
