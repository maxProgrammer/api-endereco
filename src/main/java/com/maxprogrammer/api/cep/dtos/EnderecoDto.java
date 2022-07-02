package com.maxprogrammer.api.cep.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maxprogrammer.api.cep.models.EnderecoModel;
import lombok.Data;

@Data
public class EnderecoDto {
    private String cep;
    @JsonProperty("logradouro")
    private String rua;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    public EnderecoModel novoEndereco() {
        return new EnderecoModel(cep, rua, bairro, localidade, uf, ibge, ddd, siafi);
    }
}
