package com.example.algamoney.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.lancamento.LancamentosRepositoryQuery;

@Repository
public interface LancamentosRepository extends JpaRepository<Lancamento, Long>, LancamentosRepositoryQuery {

	Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

	
	
}
