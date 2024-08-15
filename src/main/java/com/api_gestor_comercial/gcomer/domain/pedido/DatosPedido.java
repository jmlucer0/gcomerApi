package com.api_gestor_comercial.gcomer.domain.pedido;


import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public record DatosPedido(
        Long id,
        LocalDate fechaDePedido,
        Cliente cliente,
        Map<Producto, Integer> productos
) {
    public DatosPedido(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getFechaDePedido(),
                pedido.getCliente(),
                pedido.getProductos().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ))
        );
    }


}
