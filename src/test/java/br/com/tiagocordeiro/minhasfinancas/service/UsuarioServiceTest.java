package br.com.tiagocordeiro.minhasfinancas.service;

import br.com.tiagocordeiro.minhasfinancas.exception.RegraNegocioException;
import br.com.tiagocordeiro.minhasfinancas.model.entity.Usuario;
import br.com.tiagocordeiro.minhasfinancas.model.repository.UsuarioRepository;
import br.com.tiagocordeiro.minhasfinancas.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @MockBean
    UsuarioRepository usuarioRepository;
    UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        //usuarioRepository = Mockito.mock(UsuarioRepository.class); o @MockBean supre isso
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }

    @Test
    public void deveValidarEmail() {
        // cenario
        // assim que ele retorna falso ele nao retorna true (exceção)
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //acao e verificacao
        Assertions.assertDoesNotThrow(() -> {
            usuarioService.validarEmail("emailqualquer@gmail.com");
        });
    }

    @Test
    public void deveLancarExcecaoDeEmailExistenteAoTentarSalvar() {
        //cenario
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //acao e verificacao
        Assertions.assertThrows(RegraNegocioException.class, () -> {
           usuarioService.validarEmail("qualquer@gmail.com");
        });
    }

}
