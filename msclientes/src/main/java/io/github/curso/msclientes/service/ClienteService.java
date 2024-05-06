package io.github.curso.msclientes.service;

import io.github.curso.msclientes.domain.Cliente;
import io.github.curso.msclientes.infra.repository.ClientesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {

    private ClientesRepository repository;

    public ClienteService(ClientesRepository repository){
        this.repository = repository;
    }

    @Transactional
    public Cliente salvar(Cliente cliente){
        return repository.save(cliente) ;
    }

    public Optional<Cliente> getByCPF(String cpf){
        return repository.findByCpf(cpf);
    }


}
