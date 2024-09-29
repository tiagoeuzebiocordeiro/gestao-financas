package br.com.tiagocordeiro.minhasfinancas.api.controller.dto;

public class UsuarioAutenticarRequestDTO {

    private String email;
    private String senha;

    public UsuarioAutenticarRequestDTO() {

    }

    public UsuarioAutenticarRequestDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
