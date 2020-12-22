package br.com.challenge.plataform.builders.service;

import java.util.List;
import java.util.Optional;

import br.com.challenge.plataform.builders.repository.PlatformBuildersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.challenge.plataform.builders.model.Client;

@Service
public class PlatformBuildersService {
	private final PlatformBuildersRepository _repository;

	@Autowired
	public PlatformBuildersService(PlatformBuildersRepository _repository) {
		this._repository = _repository;
	}

	public Page<Client> findAll(Pageable paginacao) {
		Page<Client> page = _repository.findAll(paginacao);
		return page;
	}

	public Optional<Client> findById(Long id) {
		Optional<Client>optionalCliente = _repository.findById(id) ;
		return optionalCliente;
	}

	public Client save(Client cliente) {
		return _repository.save(cliente);
	}

	public Client getOne(Long id) {
	    Client cliente = _repository.getOne(id);
	    return cliente;
	}

	public List<Client> findByNomeIgnoreCaseContaining(String nome) {
		List<Client> clientes = _repository.findByNomeIgnoreCaseContaining(nome);
		return clientes;
	}

	public Client findByCpf(String cpf) {
		Client cliente = _repository.findByCpf(cpf);
		return cliente;
	}

	public void deleteById(Long id) {
		_repository.deleteById(id);		
	}

}
