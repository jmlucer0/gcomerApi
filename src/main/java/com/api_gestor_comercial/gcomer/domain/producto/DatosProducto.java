package com.api_gestor_comercial.gcomer.domain.producto;

public record DatosProducto(
        Long id,
        String nombre,
        double precio
) {
    public DatosProducto(Producto producto) {
        this(producto.getId(), producto.getNombre(), producto.getPrecio());
    }
}
