package br.com.tiagocordeiro.minhasfinancas.model.repository;

import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void deveVerificarExistenciaDeEmail() {
        // cenário
        Usuario usuario = new Usuario(null, "Tiago", "tiago@gmail.com", null);
        usuarioRepository.save(usuario);
        // ação ou execução
        boolean resultado = usuarioRepository.existsByEmail("tiago@gmail.com");
        // verificação
        Assertions.assertThat(resultado).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoVerificarNaoExistenciaDeEmail() {
        // garantir que a base esteja limpa
        // cenario
        usuarioRepository.deleteAll();

        // ação
        boolean resultado = usuarioRepository.existsByEmail("emailqualquer@gmail.com");

        // verificação
        Assertions.assertThat(resultado).isFalse();
    }

}
