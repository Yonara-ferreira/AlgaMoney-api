package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentosRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentosRepository lancamentosRepository;
	
	
	@GetMapping
	public List<Lancamento> Listar(){
			return lancamentosRepository.findAll();
	}
		
		
	  @GetMapping("{codigo}")
	    public ResponseEntity<Lancamento> buscarPorId(@PathVariable Long codigo) {
	        return lancamentosRepository.findById(codigo)
	                .map(lancamento -> ResponseEntity.ok(lancamento))
	                .orElse(ResponseEntity.notFound().build());
	    }
	
	
}
