package br.com.tiagocordeiro.minhasfinancas.model.repository;

import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
