package br.com.tiagocordeiro.minhasfinancas.exception;

public class ErroAutenticacaoException extends RuntimeException{
    public ErroAutenticacaoException(String message) {
        super(message);
    }
}
