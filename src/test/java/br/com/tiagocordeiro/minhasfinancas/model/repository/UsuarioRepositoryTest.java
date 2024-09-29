package br.com.tiagocordeiro.minhasfinancas.model.repository;

import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        // cenario
        Usuario usuario = new Usuario(null, "Tiago", "tiago@gmail.com", "123");
        entityManager.persist(usuario);

        // acao
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail() {
        // cenario
        Usuario usuario = new Usuario(null, "Tiago", "tiago@gmail.com", "123");
        entityManager.persist(usuario);
        // acao
        Optional<Usuario> resultado = usuarioRepository.findByEmail("tiago@gmail.com");

        // verificacao
        Assertions.assertThat(resultado.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBaseDeDados() {
       // cenario
       Optional<Usuario> usuario = usuarioRepository.findByEmail("tiago@gmail.com");

       // verificar
       Assertions.assertThat(usuario.isPresent()).isFalse();
    }

}
