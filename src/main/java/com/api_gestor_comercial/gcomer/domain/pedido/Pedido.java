package com.api_gestor_comercial.gcomer.domain.pedido;

import com.api_gestor_comercial.gcomer.domain.cliente.Cliente;
import com.api_gestor_comercial.gcomer.domain.producto.Producto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaDePedido;
    private boolean enviado;
    @ManyToOne
    private Cliente cliente;
    @ManyToMany
    @JoinTable(
            name = "pedido_producto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> listaDeProductos;
    private double precioTotal;

    public Pedido(DatosPedido datosPedido) {
        this.fechaDePedido = LocalDate.now();
        this.listaDeProductos = datosPedido.listaDeProductos();
        this.cliente = datosPedido.cliente();
        this.enviado = false;
        this.precioTotal = sumaPrecioListaProductos(datosPedido.listaDeProductos());
    }

    private double sumaPrecioListaProductos(List<Producto> listaDeProductos){
        double precioTotal = listaDeProductos.stream().mapToDouble(Producto::getPrecio)
                .sum();
        return precioTotal;
    }

    public Pedido(Long id, LocalDate fechaDePedido, Cliente cliente, List<Producto> listaDeProductos, double precioTotal) {
        this.id = id;
        this.fechaDePedido = LocalDate.now();
        this.enviado = false;
        this.cliente = cliente;
        this.listaDeProductos = listaDeProductos;
        this.precioTotal = sumaPrecioListaProductos(listaDeProductos);
    }

    public void actualizarPedido(DatosActualizarPedido datosActualizarPedido) {
        if(datosActualizarPedido.enviado()){
            this.enviado = datosActualizarPedido.enviado();
        }
        if(datosActualizarPedido.cliente() !=null){
            this.cliente = datosActualizarPedido.cliente();
        }
        if(datosActualizarPedido.listaDeProductos() != null){
            this.listaDeProductos = datosActualizarPedido.listaDeProductos();
            this.precioTotal = sumaPrecioListaProductos(listaDeProductos);
        }

    }
}
