package com.carol.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carol.osworks.api.model.OrdemServicoInput;
import com.carol.osworks.api.model.OrdemServicoReprModel;
import com.carol.osworks.domain.model.OrdemServico;
import com.carol.osworks.domain.repository.OrdemServicoRepository;
import com.carol.osworks.domain.service.GestaoOrdemServicoServico;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoServico gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoReprModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		return toModel(gestaoOrdemServico.criar(ordemServico));
	}
	
	@GetMapping
	public List<OrdemServicoReprModel> listar(){
		return toCollectionModel(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoReprModel> buscar(@PathVariable Long ordemServicoId){
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if(ordemServico.isPresent()) {
			OrdemServicoReprModel ordemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.finalizar(ordemServicoId);
	}
	
	private OrdemServicoReprModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoReprModel.class );
	}
	
	private List<OrdemServicoReprModel> toCollectionModel(List<OrdemServico> ordensServico) {
		
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
		
	}
	
}
