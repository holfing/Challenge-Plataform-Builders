package br.com.challenge.plataform.builders.endpoints;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.com.challenge.plataform.builders.endpoints.dto.ClientDTO;
import br.com.challenge.plataform.builders.errors.ResourceNotFoundException;
import br.com.challenge.plataform.builders.service.PlatformBuildersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.plataform.builders.model.Client;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = "/v2/clients",produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)

public class PlatformBuildersController {

	private final PlatformBuildersService service;

	@Autowired
	public PlatformBuildersController(PlatformBuildersService service) {
		this.service = service;
	}

	@GetMapping
	@Cacheable(value = "listAllClient")
	public ResponseEntity<?> listAll(@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.DESC) Pageable paginacao) {

		Page<Client> clientes = service.findAll(paginacao);
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> findClientById(@PathVariable("id") Long id) {
		verifyIfClientExists(id);
		Optional<Client> cliente = service.findById(id);
		return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
	}

	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "listAllClient", allEntries = true)
	public ResponseEntity<?> saveClient(@Valid @RequestBody Client cliente) {

		Client clienteCreate = service.save(cliente);
		return new ResponseEntity<>(clienteCreate, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}")
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "listAllClient", allEntries = true)
	public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody Client clienteDto) {
		verifyIfClientExists(id);
		Client newClient = service.save(clienteDto);

		return ResponseEntity.ok(newClient);
	}

	@PatchMapping(path = "partialChange/{id}")
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "listAllClient", allEntries = true)
	public ResponseEntity<?> changeClient(@RequestBody ClientDTO clienteDTO, @PathVariable("id") Long id) {
		verifyIfClientExists(id);
		Client change = service.getOne(id);
		change.setCpf(clienteDTO.getCpf());
		change.setNome(clienteDTO.getNome());
		change.setDataNascimento(clienteDTO.getDataNascimento());
		return new ResponseEntity<>(change, HttpStatus.OK);
	}

	@GetMapping(path = "/findClientByName/{nome}")
	public ResponseEntity<?> findClientByNameIgnoreCaseContaining(@PathVariable("nome") String nome) {

		List<Client> clientes = service.findByNomeIgnoreCaseContaining(nome);
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}

	@GetMapping(path = "/findByCpf/{cpf}")
	public ResponseEntity<?> findByCpf(@PathVariable String cpf) {
		Client cliente = service.findByCpf(cpf);
		return new ResponseEntity<>(cliente, HttpStatus.OK);

	}

	@DeleteMapping(path = "/{id}")
	@CacheEvict(value = "listAllClient", allEntries = true)
	public ResponseEntity<?> deleteClient(@PathVariable("id") Long id) {
		verifyIfClientExists(id);
		service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private void verifyIfClientExists(Long id) {
		Optional<Client> clientExists = service.findById(id);
		if (!clientExists.isPresent())
			throw new ResourceNotFoundException("Client not found for ID " + id);
	}

}
