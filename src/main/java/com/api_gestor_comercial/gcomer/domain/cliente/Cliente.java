package com.api_gestor_comercial.gcomer.domain.cliente;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;
    @NotNull
    @Pattern(regexp = "\\d{6,10}")
    private String telefono;
    private String direccion;
    private boolean activo = true;

    public Cliente(DatosCliente datosCliente) {
    }

    public void eliminarCliente(Cliente cliente) {
        this.activo = false;
    }
}
