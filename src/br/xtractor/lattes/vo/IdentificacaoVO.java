/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.xtractor.lattes.vo;

import java.io.Serializable;

/**
 *
 * @author richardv
 */
public class IdentificacaoVO implements Serializable {
    
    private String identificador10;
    private String nomeInicial;
    private String nomeCompleto;
    private String nomeCitacaoBibliografica;
    private String sexo;
    //Dados somente do curriculo lattes
    private String nacionalidade;
    private String pais;
    private String uf;
    private String cidade;
    private String dataFalecimento;
    private String resumoCVLattes;
    private String outrasInformacoesRelevantes;

    public String getOutrasInformacoesRelevantes() {
        return outrasInformacoesRelevantes;
    }

    public void setOutrasInformacoesRelevantes(String outrasInformacoesRelevantes) {
        this.outrasInformacoesRelevantes = outrasInformacoesRelevantes;
    }
    
    public String getResumoCVLattes() {
        return resumoCVLattes;
    }

    public void setResumoCVLattes(String resumoCVLattes) {
        this.resumoCVLattes = resumoCVLattes;
    }
    
    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDataFalecimento() {
        return dataFalecimento;
    }

    public void setDataFalecimento(String dataFalecimento) {
        this.dataFalecimento = dataFalecimento;
    }
    
    
    public String getIdentificador10() {
        return identificador10;
    }

    public void setIdentificador10(String identificador10) {
        this.identificador10 = identificador10;
    }

    public String getNomeInicial() {
        return nomeInicial;
    }

    public void setNomeInicial(String nomeInicial) {
        this.nomeInicial = nomeInicial;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeCitacaoBibliografica() {
        return nomeCitacaoBibliografica;
    }

    public void setNomeCitacaoBibliografica(String nomeCitacaoBibliografica) {
        this.nomeCitacaoBibliografica = nomeCitacaoBibliografica;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
}
