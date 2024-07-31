package com.api_gestor_comercial.gcomer.domain.cliente;

import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }


}
