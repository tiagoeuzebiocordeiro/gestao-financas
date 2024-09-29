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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;
    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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

    @Test
    public void deveSalvarUsuarioComSucesso() {
        // Cenario
        Usuario usuario = new Usuario(1L, "Tiago", "tiago@mail.com", "123");

        Mockito.when(usuarioRepository.save(Mockito.any())).thenReturn(usuario);


        // Acao
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        // Verificacao
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertEquals(usuarioSalvo.getClass(), Usuario.class);
        Assertions.assertEquals(1L, usuarioSalvo.getId());
        Assertions.assertEquals("Tiago", usuarioSalvo.getNome());
        Assertions.assertEquals("tiago@mail.com", usuarioSalvo.getEmail());
        Assertions.assertEquals("123", usuarioSalvo.getSenha());
    }

    @Test
    public void deveLancarExcecaoDeEmailAoTentarSalvarUsuarioComEmailJaCadastrado() {
        //Cenario
        Usuario usuario = new Usuario(1L, "Tiago", "tiago@mail.com", "123");
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        try {
            usuario.setId(2L); //Forçar diferença, ou seja, é outro cara com o mesmo e-mail
            usuarioService.salvar(usuario);
        } catch (Exception ex) {
            Assertions.assertEquals(RegraNegocioException.class, ex.getClass());
            Assertions.assertEquals("E-mail já existente no sistema", ex.getMessage());
        }

    }

}
