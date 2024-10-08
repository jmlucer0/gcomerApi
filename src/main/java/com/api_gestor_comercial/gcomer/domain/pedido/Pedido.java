package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;
import com.api_gestor_comercial.gcomer.domain.venta.FormaDePago;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaDePedido = LocalDate.now();
    private boolean enviado = false;

    @ManyToOne
    private Cliente cliente;

    @ElementCollection
    @CollectionTable(name = "pedido_producto", joinColumns = @JoinColumn(name = "pedido_id"))
    @MapKeyJoinColumn(name = "producto_id")
    @Column(name = "cantidad")
    private Map<Producto, Integer> productos = new HashMap<>();
    private double precioTotal;
    private FormaDePago formaDePago;

    public Pedido(DatosPedido datosPedido) {
    }

}
