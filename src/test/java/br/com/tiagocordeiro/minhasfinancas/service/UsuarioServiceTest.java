package br.com.tiagocordeiro.minhasfinancas.service;

import br.com.tiagocordeiro.minhasfinancas.exception.RegraNegocioException;
import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import br.com.tiagocordeiro.minhasfinancas.model.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Test
    public void deveValidarEmail() {
        // cenario
        usuarioRepository.deleteAll();

        //acao e verificacao
        Assertions.assertDoesNotThrow(() -> {
            usuarioService.validarEmail("emailqualquer@gmail.com");
        });
    }

    @Test
    public void deveLancarExcecaoDeEmailExistenteAoTentarSalvar() {
        //cenario
        Usuario usuario = new Usuario(null, "qualquer", "qualquer@gmail.com", null);
        usuarioRepository.save(usuario);

        //acao e verificacao
        Assertions.assertThrows(RegraNegocioException.class, () -> {
           usuarioService.validarEmail("qualquer@gmail.com");
        });
    }

}
