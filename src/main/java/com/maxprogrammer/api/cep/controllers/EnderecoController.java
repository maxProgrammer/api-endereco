package com.maxprogrammer.api.cep.controllers;

import com.maxprogrammer.api.cep.dtos.EnderecoDto;
import com.maxprogrammer.api.cep.models.EnderecoModel;
import com.maxprogrammer.api.cep.services.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @GetMapping(value = "/api/consultaExterna/cep")
    @Operation(summary = "Consultar endereco pela API  viacep")
    public EnderecoDto consultaEnderecoPelaAPIViaCEP(String cep) {
        return enderecoService.consultaEnderecoPelaAPIExterna(cep);
    }

    @GetMapping(value = "/api/endereco/{cep}")
    @Operation(summary = "Consulta endereço na base de dados pelo CEP")
    public EnderecoModel retornaEndereco(@PathVariable("cep") String cep) {
        return enderecoService.getEnderecoModel(cep);
    }

    @GetMapping(value = "/api/lista/enderecos")
    @Operation(summary = "Retorna todos os enderecos")
    public Page<EnderecoModel> retornaTodosEnderecos(@PageableDefault(page = 0,
            size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page) {
        return enderecoService.retornaTodosEnderecos(page);
    }

    @PostMapping(value = "/api/salva/endereco/{cep}")
    @Operation(summary = "Importa/atualiza um endereco")
    public void salvaEndereco(@RequestBody String cep) {
        enderecoService.salvarEndereco(cep);
    }


    @DeleteMapping(value = "/api/exclui/endereco/{cep}")
    @Operation(summary = "Importa/atualiza um endereco")
    public void excluirEndereco(@RequestBody String cep) {
        enderecoService.excluirEndereco(cep);
    }
}
