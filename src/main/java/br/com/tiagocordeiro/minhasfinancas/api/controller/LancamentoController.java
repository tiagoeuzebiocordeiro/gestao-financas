package br.com.tiagocordeiro.minhasfinancas.api.controller;

import br.com.tiagocordeiro.minhasfinancas.api.controller.dto.AtualizaStatusDTO;
import br.com.tiagocordeiro.minhasfinancas.api.controller.dto.LancamentoDTO;
import br.com.tiagocordeiro.minhasfinancas.exception.RegraNegocioException;
import br.com.tiagocordeiro.minhasfinancas.model.entity.Lancamento;
import br.com.tiagocordeiro.minhasfinancas.model.entity.StatusLancamento;
import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import br.com.tiagocordeiro.minhasfinancas.model.entity.mapper.LancamentoMapper;
import br.com.tiagocordeiro.minhasfinancas.service.LancamentoService;
import br.com.tiagocordeiro.minhasfinancas.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/lancamentos")
public class LancamentoController {

    private LancamentoService lancamentoService;
    private UsuarioService usuarioService;
    private LancamentoMapper mapper;

    public LancamentoController(LancamentoService lancamentoService,UsuarioService usuarioService ,LancamentoMapper mapper) {
        this.lancamentoService = lancamentoService;
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @PostMapping("salvar")
    public ResponseEntity salvar(@RequestBody LancamentoDTO objDTO) {
        try {
            Lancamento lancamento = mapper.converter(objDTO);
            lancamentoService.salvar(lancamento);
            return ResponseEntity.ok(lancamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("buscar")
    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "usuario") Long idUsuario
    ) {
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);
        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if (!usuario.isPresent()) {
            return ResponseEntity.badRequest().body(
                    "Não foi possivel realizar a consulta. Motivo: Usuário não encontrado para o Id informado");
        } else {
            lancamentoFiltro.setUsuario(usuario.get());
        }
        List<Lancamento> lancamentoList = lancamentoService.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentoList);
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody LancamentoDTO objDTO) {

            return lancamentoService.obterPorId(id).map(entity -> {
                try {
                    Lancamento lancamento = mapper.converter(objDTO);
                    lancamento.setId(entity.getId());
                    lancamento.setStatus(entity.getStatus());
                    lancamentoService.atualizar(lancamento);
                    return ResponseEntity.ok(lancamento);
                } catch (RegraNegocioException e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        return lancamentoService.obterPorId(id).map(entidade -> {
            lancamentoService.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity
                ("Lançamento não encontrado na base de dados", HttpStatus.BAD_REQUEST));

    }

    @PatchMapping("atualizar-status/{id}")
    public ResponseEntity atualizarStatus(@PathVariable Long id, @RequestBody AtualizaStatusDTO dto) {
        return lancamentoService.obterPorId(id).map(entidade -> {
            StatusLancamento statusLancamento = StatusLancamento.valueOf(dto.getStatus());
            if (statusLancamento == null) {
                return ResponseEntity.badRequest().body("Não foi possível atualizar o STATUS do lançamento. " +
                        "Envie um STATUS válido");
            }
            try {
                lancamentoService.atualizarLancamento(entidade, statusLancamento);
                return ResponseEntity.ok(entidade);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () -> new ResponseEntity("Lançamento não encontrado na Base de Dados", HttpStatus.BAD_REQUEST));
    }

}
