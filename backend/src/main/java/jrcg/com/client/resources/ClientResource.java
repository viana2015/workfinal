package jrcg.com.client.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jrcg.com.client.dto.ClienteDTO;
import jrcg.com.client.services.ClientService;


@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<Page<ClienteDTO>>findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page ,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy ){
		
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction) , orderBy);
		
		return ResponseEntity.ok().body(service.findAllPaged(pageRequest));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Long id ) {
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO>create ( @RequestBody ClienteDTO objDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(service.created(objDTO));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Long id, @RequestBody ClienteDTO objDto) {
		return ResponseEntity.ok(service.update(id, objDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
