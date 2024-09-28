package br.com.tiagocordeiro.minhasfinancas.model.repository;

import br.com.tiagocordeiro.minhasfinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
