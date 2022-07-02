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
        if (novoEndereco.getEndereco() != null) {
            if (possivelExistenciaNoDataBase == null) {
                enderecoRepository.save(novoEndereco);
                System.out.println("Novo endereço Salvo!");
            } else {
                atualizarEndereco(possivelExistenciaNoDataBase, enderecoDto);
                System.out.println("Endereço atualizado!");
            }

        } else {
            System.out.println("CEP não Encontrado ou já existente na base de dados.\n" +
                    "Favor informar um CEP válido.");
        }

    }

    @Transactional
    public void atualizarEndereco(EnderecoModel enderecoModelEncontrado, EnderecoDto enderecoDtoConsultado) {
        enderecoModelEncontrado.updateEndereco(enderecoDtoConsultado);
        enderecoRepository.save(enderecoModelEncontrado);
    }

    @Transactional
    public void excluirEndereco(String cep){
        EnderecoModel possivelendereco = getEnderecoModel(cep);
        if(possivelendereco != null){
            enderecoRepository.delete(possivelendereco);
        }
        else{
            System.out.println("CEP informado não existe no banco de dados.\n" +
                    "Favor informar um CEP válido!");
        }
    }


}
