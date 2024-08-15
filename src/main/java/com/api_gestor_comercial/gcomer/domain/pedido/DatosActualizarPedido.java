package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public record DatosActualizarPedido(
        Long id,
        LocalDate fechaDePedido,
        Cliente cliente,
        Map<Producto, Integer> productos

) {
    public DatosActualizarPedido(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getFechaDePedido(),
                pedido.getCliente().getId(),
                pedido.getProductos().entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> e.
        );
            }
}
