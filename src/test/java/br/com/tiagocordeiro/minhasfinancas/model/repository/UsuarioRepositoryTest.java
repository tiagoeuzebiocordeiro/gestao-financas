package br.com.tiagocordeiro.minhasfinancas.model.repository;

import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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

}
