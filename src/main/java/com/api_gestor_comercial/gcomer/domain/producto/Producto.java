package com.api_gestor_comercial.gcomer.domain.producto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double precio;


    public Producto(DatosProducto datosProducto) {
        this.nombre = datosProducto.nombre();
        this.precio = datosProducto.precio();
    }

    public void actualizarDatosProducto(DatosProducto datosProducto) {
        if(datosProducto.nombre() != null){
            this.nombre = datosProducto.nombre();
        }
        if(datosProducto.precio() == -1){
            this.precio = datosProducto.precio();
        }
    }
}
