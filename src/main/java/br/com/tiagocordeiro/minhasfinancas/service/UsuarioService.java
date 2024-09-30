package br.com.tiagocordeiro.minhasfinancas.service;

import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);
    Usuario salvar(Usuario usuario);
    Optional<Usuario> obterPorId(Long id);
}
