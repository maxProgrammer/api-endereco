package com.maxprogrammer.api.cep.models;

import com.maxprogrammer.api.cep.dtos.EnderecoDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String endereco;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;

    private String ddd;
    private String siafi;

    public EnderecoModel() {
    }

    public EnderecoModel(String cep, String endereco, String bairro, String localidade, String uf, String ibge, String ddd, String siafi) {
        this.cep = cep;
        this.endereco = endereco;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.ibge = ibge;
        this.ddd = ddd;
        this.siafi = siafi;
    }

    public void updateEndereco(EnderecoDto enderecoDto) {
        this.cep = enderecoDto.getCep();
        this.endereco = enderecoDto.getEndereco();
        this.bairro = enderecoDto.getBairro();
        this.localidade = enderecoDto.getLocalidade();
        this.uf = enderecoDto.getUf();
        this.ibge = enderecoDto.getIbge();
        this.ddd = enderecoDto.getDdd();
        this.siafi = enderecoDto.getSiafi();
    }

    @Override
    public String toString() {
        return "EnderecoModel{" +
                "id=" + id +
                ", cep='" + cep + '\'' +
                ", endereco='" + endereco + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                ", ibge='" + ibge + '\'' +
                ", ddd='" + ddd + '\'' +
                ", siafi='" + siafi + '\'' +
                '}';
    }
}
