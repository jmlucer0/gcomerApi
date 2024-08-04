package com.api_gestor_comercial.gcomer.domain.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findByActivoTrue(Pageable pageable);

    Page<Cliente> findByNombreContaining(String nombre, Pageable pageable);
}
