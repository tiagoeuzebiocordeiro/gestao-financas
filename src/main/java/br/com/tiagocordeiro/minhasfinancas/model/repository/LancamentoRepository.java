package br.com.tiagocordeiro.minhasfinancas.model.repository;

import br.com.tiagocordeiro.minhasfinancas.model.entity.Lancamento;
import br.com.tiagocordeiro.minhasfinancas.model.entity.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query(value =
            "select sum(l.valor) from Lancamento l join l.usuario u " +
            "where u.id =:idUsuario and l.tipo =:tipoLancamento " +
                    "group by u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario, @Param("tipoLancamento") TipoLancamento tipoLancamento);

}
