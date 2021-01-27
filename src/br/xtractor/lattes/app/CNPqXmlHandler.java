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
package br.xtractor.lattes.app;

import br.xtractor.helper.XtractorHelper;
import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.core.FormacaoAcademica;
import br.xtractor.lattes.vo.EnderecoVO;
import br.xtractor.lattes.vo.FormacaoVO;
import br.xtractor.lattes.vo.IdentificacaoVO;
import br.xtractor.lattes.vo.Pesquisador;
import java.util.Calendar;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author richardv
 */


public class CNPqXmlHandler extends DefaultHandler {
    private String tmpValue = null;
    private CurriculoLattes curriculoLattes     = null;
    private Pesquisador pesquisador             = null;
    
    public CurriculoLattes getCurriculoLattes() {
        return curriculoLattes;
    }
    
    public CNPqXmlHandler() {
        curriculoLattes = new CurriculoLattes();
    }
    
    private String nvl(String s){
        return XtractorHelper.nullToEmpty(s);
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        if (qName.equalsIgnoreCase("CURRICULO-VITAE")){
            this.curriculoLattes = new CurriculoLattes();
            this.pesquisador = new Pesquisador();
            this.curriculoLattes.addPesquisador(this.pesquisador);
            Calendar c = Calendar.getInstance();
            String dtProcessamento =  c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
            this.curriculoLattes.setData_processamento(dtProcessamento);
        } else if (qName.equalsIgnoreCase("DADOS-GERAIS")){
            
            IdentificacaoVO vo = this.recuperaObjetoIdentificacaoPesquisador(this.pesquisador);
            vo.setNomeCompleto(this.nvl(attributes.getValue("NOME-COMPLETO")));
            vo.setNomeCitacaoBibliografica(this.nvl(attributes.getValue("NOME-NOME-EM-CITACOES-BIBLIOGRAFICAS")));
            vo.setNacionalidade(this.nvl(attributes.getValue("NACIONALIDADE")));
            vo.setPais(this.nvl(attributes.getValue("PAIS-DE-NASCIMENTO")));
            vo.setUf(this.nvl(attributes.getValue("UF-NASCIMENTO")));
            vo.setCidade(this.nvl(attributes.getValue("CIDADE-NASCIMENTO")));
            vo.setSexo(this.nvl(attributes.getValue("SEXO")));
            vo.setDataFalecimento(this.nvl(attributes.getValue("DATA-FALECIMENTO")));
            this.pesquisador.setIdentificacao(vo);
        
        } else if (qName.equalsIgnoreCase("RESUMO-CV")){
            
            IdentificacaoVO vo = this.recuperaObjetoIdentificacaoPesquisador(this.pesquisador);
            vo.setResumoCVLattes(this.nvl(attributes.getValue("TEXTO-RESUMO-CV-RH")));
            
        } else if (qName.equalsIgnoreCase("OUTRAS-INFORMACOES-RELEVANTES")){
            
            IdentificacaoVO vo = this.recuperaObjetoIdentificacaoPesquisador(this.pesquisador);
            vo.setOutrasInformacoesRelevantes(this.nvl(attributes.getValue("OUTRAS-INFORMACOES-RELEVANTES")));            
            
        } else if (qName.equalsIgnoreCase("ENDERECO-PROFISSIONAL")){
            
            EnderecoVO vo = new EnderecoVO();
            
            vo.setNomeInstituicaoEmpresa(this.nvl(attributes.getValue("NOME-INSTITUICAO-EMPRESA")));
            vo.setPais(this.nvl(attributes.getValue("PAIS")));
            vo.setUf(this.nvl(attributes.getValue("UF")));
            vo.setLogradouro(this.nvl(attributes.getValue("LOGRADOURO-COMPLEMENTO")));
            vo.setBairro(this.nvl(attributes.getValue("BAIRRO")));
            vo.setCidade(this.nvl(attributes.getValue("CIDADE")));
            vo.setCep(this.nvl(attributes.getValue("CEP")));
            vo.setEnderecoProfissional(null);
            pesquisador.setEndereco(vo);
            
        } else if (qName.equalsIgnoreCase("GRADUACAO") || 
                   qName.equalsIgnoreCase("MESTRADO")  || 
                   qName.equalsIgnoreCase("DOUTORADO") ||
                   qName.equalsIgnoreCase("ESPECIALIZACAO")){
            FormacaoAcademica fa = this.recuperaObjetoFormacaoAcademicaPesquisador(pesquisador);
            FormacaoVO vo = new FormacaoVO();
            
            vo.setAnoConclusao(attributes.getValue("ANO-DE-CONCLUSAO"));
            vo.setAnoInicio(attributes.getValue("ANO-DE-INICIO"));
            vo.setNomeInstituicao(attributes.getValue("NOME-INSTITUICAO"));
            
            if (qName.equalsIgnoreCase("GRADUACAO")){
                vo.setTipo("Graduação");
            } else if (qName.equalsIgnoreCase("MESTRADO")){
                vo.setTipo("Mestrado");
            } else if (qName.equalsIgnoreCase("DOUTORADO")){
                vo.setTipo("Doutorado");
            } else if (qName.equalsIgnoreCase("ESPECIALIZACAO")){
                vo.setTipo("Especialização");
            } else if (qName.equalsIgnoreCase("POS-DOUTORADO")){
                vo.setTipo("Especialização");
            }
            
        } else if (qName.equalsIgnoreCase("DISCIPLINA")){
            XtractorHelper.debug(this.tmpValue + " --- " + attributes.getValue("SEQUENCIA-ESPECIFICACAO"));
        }
    }
    
    
    private IdentificacaoVO recuperaObjetoIdentificacaoPesquisador(Pesquisador p){
        IdentificacaoVO vo;
        if (pesquisador.getIdentificacao() == null){
            vo = new IdentificacaoVO();
            pesquisador.setIdentificacao(vo);
        } else {
            vo = pesquisador.getIdentificacao();
        }
        return vo;
    }
    
    private FormacaoAcademica recuperaObjetoFormacaoAcademicaPesquisador(Pesquisador p){
        FormacaoAcademica fa;
        if (pesquisador.getFormacaoAcademica() == null){
            fa = new FormacaoAcademica();
            pesquisador.setFormacaoAcademica(fa);
        } else {
            fa = pesquisador.getFormacaoAcademica();
        }
        return fa;
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.tmpValue = new String(ch, start, length);
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("DISCIPLINA")){
            
            XtractorHelper.debug(this.tmpValue + " --- " );
        }
    }
}
