package br.com.tiagocordeiro.minhasfinancas.service;

import br.com.tiagocordeiro.minhasfinancas.model.entity.Lancamento;
import br.com.tiagocordeiro.minhasfinancas.model.entity.StatusLancamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LancamentoService {
    Lancamento salvar(Lancamento lancamento);
    Lancamento atualizar(Lancamento lancamento);
    void deletar(Lancamento lancamento);
    List<Lancamento> buscar(Lancamento lancamentoFiltro);
    void atualizarLancamento(Lancamento lancamento, StatusLancamento statusLancamento);
    Optional<Lancamento> obterPorId(Long id);
    BigDecimal obterSaldoPorUsuario(Long id);

}
