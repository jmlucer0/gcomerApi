package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;

import java.time.LocalDate;
import java.util.List;

public record DatosActualizarPedido(
        Long id,
        Cliente cliente,
        List<Producto> listaDeProductos,
        boolean enviado
) {
    public DatosActualizarPedido(Pedido pedido) {
        this(pedido.getId(), pedido.getCliente(), pedido.getListaDeProductos(), pedido.isEnviado());
    }
}
