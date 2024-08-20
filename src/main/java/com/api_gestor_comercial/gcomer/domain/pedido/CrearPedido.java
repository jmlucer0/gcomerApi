package com.api_gestor_comercial.gcomer.domain.pedido;

import java.util.Map;

public record CrearPedido(
        Long clienteId,
        Map<Long, Integer> productos
) {
}
