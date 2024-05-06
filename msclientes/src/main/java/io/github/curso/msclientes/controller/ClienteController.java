package io.github.curso.msclientes.controller;

import io.github.curso.msclientes.domain.Cliente;
import io.github.curso.msclientes.representation.ClienteSaveRequest;
import io.github.curso.msclientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public String status(){
        log.info("status mscliente");
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        Cliente cliente = request.toModel();
        service.salvar(cliente);
        URI headerLocation = ServletUriComponentsBuilder.fromCurrentRequest().query("cpf={cpf}").buildAndExpand(cliente.getCpf()).toUri();

        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<Cliente> dadosCliente(@RequestParam("cpf") String cpf){
        var cliente = service.getByCPF(cpf);

        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }


}
