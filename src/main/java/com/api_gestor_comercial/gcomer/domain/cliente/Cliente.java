package com.api_gestor_comercial.gcomer.domain.cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer telefono;
    private String direccion;
    private boolean activo;

    public Cliente(DatosCliente datosCliente) {
        this.nombre = datosCliente.nombre();
        this.telefono = datosCliente.telefono();
        this.direccion = datosCliente.direccion();
        this.activo = true;
    }


    public void actualizarDatosCliente(DatosCliente datosCliente) {
        if(datosCliente.nombre() != null){
            this.nombre = datosCliente.nombre();
        }
        if(datosCliente.telefono()!=null){
            this.telefono = datosCliente.telefono();
        }
        if(datosCliente.direccion() != null){
            this.direccion = datosCliente.direccion();
        }
    }

    public void eliminarCliente(Cliente cliente) {
        this.activo = false;
    }
}
