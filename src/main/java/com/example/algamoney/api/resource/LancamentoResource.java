package com.example.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentosRepository;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentosRepository lancamentosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/pesquisa")
	public Page<Lancamento> pesquisaFiltrada(LancamentoFilter lancamentoFilter, Pageable pageable){
			return lancamentosRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping
	public ResponseEntity<Optional<Lancamento>> buscarPeloCodigo(@PathVariable Long codigo){
		Optional<Lancamento> lancamento = lancamentosRepository.findById(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
		
    @PostMapping("/CriarLançamento")
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo = lancamentosRepository.save(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
    }
    
    @ExceptionHandler({ PessoaInexistenteOuInativaException.class })
    private ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }
    
    @DeleteMapping(value = "deletarLancamento")
    @ResponseBody
    public ResponseEntity<String> deletarLancamento(@RequestParam(name = "codigo") Long codigo){
    	if (isLancamentoExistente(codigo)) {
			lancamentosRepository.deleteById(codigo);
			return ResponseEntity.ok("Lancamento foi deletado com sucesso");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O lancamento nao foi encontrado");
		}
    }
    
    private Boolean isLancamentoExistente(Long codigo){
        return lancamentosRepository.existsById(codigo);
    }
}
