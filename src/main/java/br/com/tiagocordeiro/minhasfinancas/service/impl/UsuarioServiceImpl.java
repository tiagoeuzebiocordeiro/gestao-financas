package br.com.tiagocordeiro.minhasfinancas.service.impl;

import br.com.tiagocordeiro.minhasfinancas.exception.ErroAutenticacaoException;
import br.com.tiagocordeiro.minhasfinancas.exception.RegraNegocioException;
import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import br.com.tiagocordeiro.minhasfinancas.model.repository.UsuarioRepository;
import br.com.tiagocordeiro.minhasfinancas.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (!usuario.isPresent()) {
            throw new ErroAutenticacaoException("Usuário não encontrado para o e-mail informado.");
        }
        if (!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacaoException("Senha inválida para o e-mail informado!");
        }
        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvar(Usuario usuario) {
        validarEmail(usuario);
        return usuarioRepository.save(usuario);
    }

    public void validarEmail(Usuario usuario) {
        Optional<Usuario> usuarioParaValidar = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioParaValidar.isPresent() && !usuarioParaValidar.get().equals(usuario.getId())) {
            throw new RegraNegocioException("E-mail já existente no sistema");
        }
    }


}
