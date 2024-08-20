package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;

import java.util.Map;
import java.util.stream.Collectors;

public record ActualizarPedidoDTO(
        Long id,
        Cliente cliente,
        boolean enviado,
        Map<Producto, Integer> productos

) {
    public ActualizarPedidoDTO(Pedido pedido) {
        this(pedido.getId(),
                pedido.getCliente(),
                pedido.isEnviado(),
                pedido.getProductos().entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ))
                );

    }

}
