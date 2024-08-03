package com.api_gestor_comercial.gcomer.domain.cliente;

public record DatosCliente(
        Long id,
        String nombre,
        String telefono,
        String direccion
) {
    public DatosCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNombre(), cliente.getTelefono(), cliente.getDireccion());
    }
}
