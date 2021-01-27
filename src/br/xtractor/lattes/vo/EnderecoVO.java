/*
#  LattesXtractor V1.1
#  Copyright 2019: Richard William Valdivia
#  E-mail: rwvaldivia@yahoo.com.br
#
#  Link do Repostório de Pesquisa
#  http://repositorio.unifesp.br/handle/11600/57106
#  
#  Download da Dissertação Mestrado
#  http://repositorio.unifesp.br/bitstream/handle/11600/57106/Dissertacao_Richard%20William%20Valdivia.pdf?sequence=1&isAllowed=y
#
#  Este programa é um software livre; você pode redistribui-lo e/ou 
#  modifica-lo dentro dos termos da Licença Pública Geral GNU como 
#  publicada pela Fundação do Software Livre (FSF); na versão 2 da 
#  Licença, ou (na sua opinião) qualquer versão.
#
#  Este programa é distribuído na esperança que possa ser util, 
#  mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUAÇÂO a qualquer
#  MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a
#  Licença Pública Geral GNU para maiores detalhes.
#
#  Você deve ter recebido uma cópia da Licença Pública Geral GNU
#  junto com este programa, se não, escreva para a Fundação do Software
#  Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
#
*/
package br.xtractor.lattes.vo;

import br.xtractor.helper.XtractorHelper;
import java.io.Serializable;

/**
 *
 * @author richardv
 */
public class EnderecoVO implements Serializable {
    /*
    private String endereco_profissional;
    private String endereco_profissional_lat;
    private String endereco_profissional_long;
    */
    private String enderecoProfissional;
    private String enderecoProfissionalLat;
    private String enderecoProfissionalLong;
    private String nomeInstituicaoEmpresa;
    private String pais;
    private String uf;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String cep;

    public String getNomeInstituicaoEmpresa() {
        return nomeInstituicaoEmpresa;
    }

    public void setNomeInstituicaoEmpresa(String nomeInstituicaoEmpresa) {
        this.nomeInstituicaoEmpresa = nomeInstituicaoEmpresa;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
    private String nvl(String s){
        return XtractorHelper.nullToEmpty(s);
    }
    public String getEnderecoProfissional() {
        
        if (this.enderecoProfissional == null){
            StringBuilder builder = new StringBuilder();
            builder.append("Nome: ").append(this.nvl(this.getNomeInstituicaoEmpresa())).append(" -*- ");
            builder.append("Pais: ").append(this.nvl(this.getPais())).append(" -*- ");
            builder.append("UF: ").append(this.getUf()).append(" -*- ");
            builder.append("LOGRADOURO-COMPLEMENTO: ").append(this.nvl(this.getLogradouro())).append(" -*- ");
            builder.append("BAIRRO: ").append(this.nvl(this.getBairro())).append(" -*- ");
            builder.append("CIDADE: ").append(this.nvl(this.getCidade())).append(" -*- ");
            builder.append("CEP: ").append(this.nvl(this.getCep())).append(" -*- ");
            return builder.toString();
        }
        
        return enderecoProfissional;
    }

    public void setEnderecoProfissional(String enderecoProfissional) {
        this.enderecoProfissional = enderecoProfissional;
    }

    public String getEnderecoProfissionalLat() {
        return enderecoProfissionalLat;
    }

    public void setEnderecoProfissionalLat(String enderecoProfissionalLat) {
        this.enderecoProfissionalLat = enderecoProfissionalLat;
    }

    public String getEnderecoProfissionalLong() {
        return enderecoProfissionalLong;
    }

    public void setEnderecoProfissionalLong(String enderecoProfissionalLong) {
        this.enderecoProfissionalLong = enderecoProfissionalLong;
    }
    
}
