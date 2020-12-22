package br.com.challenge.plataform.builders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.challenge.plataform.builders.model.Client;

@Repository
public interface PlatformBuildersRepository extends JpaRepository<Client, Long>{
    List<Client> findByNomeIgnoreCaseContaining(String nome);
    Client findByCpf(String cpf);
}
