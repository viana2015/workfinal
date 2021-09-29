package jrcg.com.client.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jrcg.com.client.dto.ClienteDTO;
import jrcg.com.client.entities.Client;
import jrcg.com.client.repositories.ClienteRepository;
import jrcg.com.client.services.exception.DatabaseException;
import jrcg.com.client.services.exception.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClienteRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAllPaged(PageRequest pageRequest){
		Page<Client>list = repository.findAll(pageRequest);
		return list.map(x -> new ClienteDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado para o id: " ));
		return new ClienteDTO(entity);
	}
	
	@Transactional
	public ClienteDTO created(ClienteDTO objDTO) {
		Client entity = new Client();
		copyDtoToEntity(objDTO, entity);
		entity = repository.save(entity);
		return new ClienteDTO(entity);
		
	}
	
	@Transactional
	public ClienteDTO update (Long id, ClienteDTO objDTO) {
		try {
			Client entity = repository.getOne(id);
			copyDtoToEntity(objDTO, entity);
			entity = repository.save(entity);
			return new ClienteDTO(entity);
			
		} catch (EntityNotFoundException ex) {
			 throw new ResourceNotFoundException("Id informádo não consta no banco de dados " + id);
		}
	}
	
	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id informádo não consta no banco de dados" + id);
		}
		// Se por acaso algum dia esta método for associado a alguma coisa já deixei a exeção criada !
		catch (DataIntegrityViolationException e) { 
			throw new DatabaseException("Não foi possivél deletear pois já existe cliente associados!");
		}
	}
	
	private void copyDtoToEntity(ClienteDTO objDTO, Client entity) {
		
		entity.setName(objDTO.getName());
		entity.setCpf(objDTO.getCpf());
		entity.setIncome(objDTO.getIncome());
		entity.setBirthDate(objDTO.getBirthDate());
		entity.setChildren(objDTO.getChildren());
		
		
	}
}
