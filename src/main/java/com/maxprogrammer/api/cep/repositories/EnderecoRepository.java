package com.maxprogrammer.api.cep.repositories;

import com.maxprogrammer.api.cep.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {

    public EnderecoModel getEnderecoBycep(String cep);

}
