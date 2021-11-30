package br.com.gaspar.APIcliente.http.controller;

import br.com.gaspar.APIcliente.entity.Cliente;
import br.com.gaspar.APIcliente.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ControllerCliente {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Cliente criarCliente(@RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> listarTodosClientes() {
        return clienteService.listarTodosClientes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente buscarClientePorId(@PathVariable Long id){
        return clienteService.buscarClientePorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado."));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCliente(@PathVariable Long id){
        clienteService.buscarClientePorId(id)
                .map(cliente -> {
                    clienteService.removerClientePorId(cliente.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado."));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente){
        clienteService.buscarClientePorId(id)
                .map(clienteBase -> {
                    modelMapper.map(cliente, clienteBase);
                    clienteService.salvar(clienteBase);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado."));
    }
}
