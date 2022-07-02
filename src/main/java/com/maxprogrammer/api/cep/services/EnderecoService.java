package com.maxprogrammer.api.cep.services;

import com.maxprogrammer.api.cep.dtos.EnderecoDto;
import com.maxprogrammer.api.cep.models.EnderecoModel;
import com.maxprogrammer.api.cep.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    public EnderecoDto consultaEnderecoPelaAPIExterna(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String urlResource = "https://viacep.com.br/ws/" + cep + "/json";
        EnderecoDto possivelEndereco = restTemplate.getForObject(urlResource, EnderecoDto.class);
        return possivelEndereco;
    }

    public EnderecoModel getEnderecoModel(String cep) {
        return enderecoRepository.getEnderecoBycep(cep);
    }

    public Page<EnderecoModel> retornaTodosEnderecos(Pageable page) {
        return enderecoRepository.findAll(page);
    }

    @Transactional
    public void salvarEndereco(String cep) {
        EnderecoDto enderecoDto = consultaEnderecoPelaAPIExterna(cep);
        EnderecoModel novoEndereco = enderecoDto.novoEndereco();
        EnderecoModel possivelExistenciaNoDataBase = enderecoRepository.getEnderecoBycep(novoEndereco.getCep());
        if (novoEndereco.getRua() != null) {
            if (possivelExistenciaNoDataBase == null) {
                enderecoRepository.save(novoEndereco);
                System.out.println("\nEndereço adicionado:\n" + novoEndereco);
            } else {
                atualizarEndereco(possivelExistenciaNoDataBase, enderecoDto);
                System.out.println("\nEndereço atualizado:\n" + novoEndereco);
            }

        } else {
            System.out.println("\nCEP não Encontrado.\n" +
                    "Favor informar um CEP válido.");
        }

    }

    @Transactional
    public void atualizarEndereco(EnderecoModel enderecoModelEncontrado, EnderecoDto enderecoDtoConsultado) {
        enderecoModelEncontrado.updateEndereco(enderecoDtoConsultado);
        enderecoRepository.save(enderecoModelEncontrado);
    }

    @Transactional
    public void excluirEndereco(String cep) {
        EnderecoModel possivelendereco = getEnderecoModel(cep);
        if (possivelendereco != null) {
            System.out.println("\nEndereço selecionado para deleção:\n" + possivelendereco + "\n");
            enderecoRepository.delete(possivelendereco);
        } else {
            System.out.println("\nCEP informado não existe no banco de dados.\n" +
                    "Favor informar um CEP válido!");
        }
    }

}
