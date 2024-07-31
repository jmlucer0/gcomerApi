package com.api_gestor_comercial.gcomer.domain.venta;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double monto;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "Venta_Producto",
            joinColumns = @JoinColumn(name = "venta_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;
    private FormaDePago formaDePago;
    private LocalDate fechaDeEmision;

}
