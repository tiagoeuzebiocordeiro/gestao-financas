package br.com.tiagocordeiro.minhasfinancas.model.entity.mapper;

import br.com.tiagocordeiro.minhasfinancas.api.controller.dto.LancamentoDTO;
import br.com.tiagocordeiro.minhasfinancas.exception.RegraNegocioException;
import br.com.tiagocordeiro.minhasfinancas.model.entity.Lancamento;
import br.com.tiagocordeiro.minhasfinancas.model.entity.StatusLancamento;
import br.com.tiagocordeiro.minhasfinancas.model.entity.TipoLancamento;
import br.com.tiagocordeiro.minhasfinancas.service.UsuarioService;
import org.springframework.stereotype.Component;

@Component
public class LancamentoMapper {

    private UsuarioService service;

    public LancamentoMapper(UsuarioService service) {
        this.service = service;
    }

    public Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setMes(dto.getMes());
        lancamento.setAno(dto.getAno());

        //Id do usuario
        lancamento.setUsuario(service.obterPorId(dto.getIdUsuario())
                  .orElseThrow(() -> new RegraNegocioException("Não foi possível encontrar o usuário pelo Id informado.")));

        if (dto.getTipoLancamento() != null) {
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipoLancamento()));
        }

        if (dto.getStatusLancamento() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatusLancamento()));
        }

        lancamento.setValor(dto.getValor());
        return lancamento;
    }

}
