package br.com.tiagocordeiro.minhasfinancas.api.controller;

import br.com.tiagocordeiro.minhasfinancas.api.controller.dto.UsuarioAutenticarRequestDTO;
import br.com.tiagocordeiro.minhasfinancas.api.controller.dto.UsuarioDTO;
import br.com.tiagocordeiro.minhasfinancas.exception.ErroAutenticacaoException;
import br.com.tiagocordeiro.minhasfinancas.exception.RegraNegocioException;
import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import br.com.tiagocordeiro.minhasfinancas.service.LancamentoService;
import br.com.tiagocordeiro.minhasfinancas.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    // Container da Inj. procura uma implementação automaticamente
    private UsuarioService service;
    private LancamentoService lancamentoService;

    public UsuarioController(UsuarioService service, LancamentoService lancamentoService) {
        this.service = service;
        this.lancamentoService = lancamentoService;
    }

    @GetMapping("status")
    public String status() {
        return "OK";
    }

    @PostMapping("autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioAutenticarRequestDTO dto) {
        try {
            Usuario usuarioAuth = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok().body(usuarioAuth);
        } catch (ErroAutenticacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("salvar")
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = new Usuario(null, dto.getNome(), dto.getEmail(), dto.getSenha());
        try {
            Usuario usuarioSalvo = service.salvar(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("obter-saldo/{id}")
    public ResponseEntity obterSaldoPorId(@PathVariable Long id) {
        Optional<Usuario> usuario =  service.obterPorId(id);
        if (!usuario.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok().body(saldo);
    }

}
