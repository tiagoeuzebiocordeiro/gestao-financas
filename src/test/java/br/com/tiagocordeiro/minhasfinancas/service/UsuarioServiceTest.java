package br.com.tiagocordeiro.minhasfinancas.service;

import br.com.tiagocordeiro.minhasfinancas.exception.ErroAutenticacaoException;
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

import java.util.Optional;

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

    @Test
    public void deveAutenticarUsuarioComSucesso() {
        // cenario
        String email = "email@mail.com";
        String senha = "123";
        Usuario usuario = new Usuario(1L, "Jose", email, senha);
        Mockito.when((usuarioRepository.findByEmail(email))).thenReturn(Optional.of(usuario));

        //acao
        Usuario result = usuarioService.autenticar(email, senha);

        Assertions.assertDoesNotThrow(() -> {
            org.assertj.core.api.Assertions.assertThat(result).isNotNull();
        });
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComEmailInformado() {
        //cenario
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Throwable exception = org.assertj.core.api.Assertions.catchThrowable(() ->
                usuarioService.autenticar("email@gmail.com", "123"));

        Assertions.assertEquals(ErroAutenticacaoException.class, exception.getClass());
        Assertions.assertEquals("Usuário não encontrado para o e-mail informado.", exception.getMessage());
    }

    @Test
    public void deveLancarErroQuandoSenhaNaoBater() {
        //cenario
        String senha = "T123";
        String email = "tiago@mail.com";
        Usuario usuario = new Usuario(null, "Tiago", email, senha);
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //acao e verificacao
        Throwable exception = org.assertj.core.api.Assertions.catchThrowable(
                () -> usuarioService.autenticar(email, "X"));
        Assertions.assertEquals(ErroAutenticacaoException.class, exception.getClass());
        Assertions.assertEquals("Senha inválida para o e-mail informado!", exception.getMessage());
    }

}
