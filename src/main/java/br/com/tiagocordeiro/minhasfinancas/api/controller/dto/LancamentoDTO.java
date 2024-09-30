package br.com.tiagocordeiro.minhasfinancas.api.controller.dto;

import java.math.BigDecimal;

public class LancamentoDTO {

    private Long id;
    private String descricao;
    private Integer mes;
    private Integer ano;
    private Long idUsuario;
    private BigDecimal valor;
    private String tipoLancamento;
    private String statusLancamento;

    public LancamentoDTO(Long id, String descricao, Integer mes, Integer ano, Long idUsuario, BigDecimal valor, String tipoLancamento, String statusLancamento) {
        this.id = id;
        this.descricao = descricao;
        this.mes = mes;
        this.ano = ano;
        this.idUsuario = idUsuario;
        this.valor = valor;
        this.tipoLancamento = tipoLancamento;
        this.statusLancamento = statusLancamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public String getStatusLancamento() {
        return statusLancamento;
    }

    public void setStatusLancamento(String statusLancamento) {
        this.statusLancamento = statusLancamento;
    }
}
