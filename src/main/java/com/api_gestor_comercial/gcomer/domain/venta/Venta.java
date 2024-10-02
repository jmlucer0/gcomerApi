package com.api_gestor_comercial.gcomer.domain.venta;

import com.api_gestor_comercial.gcomer.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Pedido pedido;
    private FormaDePago formaDePago;

}
