package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;

import java.time.LocalDate;
import java.util.List;

public record DatosPedido(
        Long id,
        LocalDate fechaDePedido,
        Cliente cliente,
        List<Producto> listaDeProductos
) {
    public DatosPedido(Pedido pedido) {
        this(pedido.getId(), pedido.getFechaDePedido(), pedido.getCliente(), pedido.getListaDeProductos());
    }
}
