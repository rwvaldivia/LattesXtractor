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
package br.xtractor.presets;

import br.xtractor.helper.XtractorHelper;
import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.core.FormacaoAcademica;
import br.xtractor.lattes.vo.ArtigoRevistaVO;
import br.xtractor.lattes.vo.ArtigoVO;
import br.xtractor.lattes.vo.FormacaoVO;
import br.xtractor.lattes.vo.IdentificacaoVO;
import br.xtractor.lattes.vo.IdiomaVO;
import br.xtractor.lattes.vo.Pesquisador;
import br.xtractor.lattes.vo.ResumoCongressoExpandidoVO;
import br.xtractor.lattes.vo.ResumoCongressoVO;
import br.xtractor.lattes.vo.TextoJornalVO;
import br.xtractor.lattes.vo.TrabalhoApresentadoVO;
import br.xtractor.lattes.vo.TrabalhoCompletoVO;
import java.util.List;

/**
 * 
 * @author gustavov
 */
public class PrefuseTreeViewXMLCreator implements PresetsProtocol {
    
    /**
     * Lança IllegalArgumentException se CurriculoLattes for null<br />
     * 
     * @param cv Classe que representa pool de CurriculoLattes
     * @return xml String
     */
    public String createXml(CurriculoLattes cv){
        StringBuilder xml = new StringBuilder();
        if (cv == null){
            throw new IllegalArgumentException("Object CurriculoLattes null");
        }
        
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("\n");
        xml.append("<tree>").append("\n");
        xml.append("    <declarations>").append("\n");
        xml.append("        <attributeDecl name=\"name\" type=\"String\"/>").append("\n");
        xml.append("    </declarations>").append("\n");
        xml.append("    <branch>").append("\n");
        xml.append("        <attribute name=\"name\" value=\"Curriculo Lattes\"/>").append("\n");
       
        
        List<Pesquisador> lista = cv.getPesquisador();
        
        if (lista != null){
            for (int i =0; i < lista.size(); i++){
                Pesquisador pesquisador = lista.get(i);
                if (pesquisador != null){
                    xml.append("        <branch>").append("\n");
                    xml.append("            <attribute name=\"name\" value=\"Pesquisador [").append(pesquisador.getIdentificacao().getNomeCompleto()).append("]   \"/>").append("\n");
                    xml.append(this.getXMLIdentificacao(pesquisador));
                    xml.append((this.getXMLIdiomas(pesquisador)));
                    xml.append((this.getXMLFormacaoAcademica(pesquisador)));
                    xml.append((this.getXMLProducaoCientifica(pesquisador)));
                    
                    xml.append("        </branch>").append("\n");
                }
            }
        }
        xml.append("    </branch>");
        xml.append("</tree>");
        return xml.toString();
    }
    
    private String getXMLIdiomas(Pesquisador pesquisador){ 
        IdiomaVO [] idiomas = pesquisador.getIdiomasVO();
        
        StringBuilder xml = new StringBuilder();
        
        String temp = null;
        
        
        //################################################
        //INICIO DA TAG DE ABERTURA
        xml.append("    <branch>").append("\n"); 
        if (idiomas != null) {
            for (int i = 0; i < idiomas.length; i++){   
                xml.append("        <attribute name=\"name\" value=\"").append("Idiomas [").append(idiomas.length).append("]").append("\"/>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                   <attribute name=\"name\" value=\"Idioma: ").append(this.safeString(idiomas[i].getNome()).replaceAll("\"", "")).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                   <attribute name=\"name\" value=\"Proeficiencia: ").append(this.safeString(idiomas[i].getProficiencia()).replaceAll("\"", "")).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
            }
        }
        xml.append("    </branch>").append("\n"); 
        
        return xml.toString();
    }
    
    private int getListSize(List list){
        if (list != null){
            return list.size();
        }
        return 0;
    }
    
    private String getXMLProducaoCientifica(Pesquisador pesquisador){ 
        StringBuilder xml = new StringBuilder();
        int totalProducao = 0;
        
        
        ArtigoVO [] artigosEmPeriodicos                             = pesquisador.getArtigosEmPeriodicosVO();
        ArtigoRevistaVO [] artigosEmRevistasVO                      = pesquisador.getArtigosEmRevistaVO();
        TextoJornalVO [] jornal                                     = pesquisador.getTextosEmJornalVO();
        ResumoCongressoVO [] resumoCongressoVO                      = pesquisador.getResumoCongressoVO();
        TrabalhoCompletoVO [] trabalhoCompletoCongresso             = pesquisador.getTrabalhoCompletoCongressoVO();
        ResumoCongressoExpandidoVO [] resumoExpandidoCongressoVO    = pesquisador.getResumoCongressoExpandidoVO();
        TrabalhoApresentadoVO [] trabalhoApresentadoVO              = pesquisador.getTrabalhosApresentadosVO();
        
        
        totalProducao += artigosEmPeriodicos != null ? artigosEmPeriodicos.length : 0;
        totalProducao += artigosEmRevistasVO != null ? artigosEmRevistasVO.length : 0;
        totalProducao += jornal != null ? jornal.length : 0;
        totalProducao += trabalhoCompletoCongresso != null ? trabalhoCompletoCongresso.length : 0;
        totalProducao += resumoCongressoVO != null ? resumoCongressoVO.length : 0;
        totalProducao += resumoExpandidoCongressoVO != null ? resumoExpandidoCongressoVO.length : 0;
        totalProducao += trabalhoApresentadoVO != null ? trabalhoApresentadoVO.length : 0;
        
        //################################################
        //INICIO DA TAG DE ABERTURA
        xml.append("    <branch>").append("\n"); 
        xml.append("        <attribute name=\"name\" value=\"Producao Acadêmica [").append(totalProducao).append("]\"/>").append("\n");
        if (artigosEmPeriodicos != null) {
            if (artigosEmPeriodicos != null){
                xml.append("        <branch>").append("\n"); 
                xml.append("            <attribute name=\"name\" value=\"Artigos em Periodicos [").append(artigosEmPeriodicos.length).append("]\"/>").append("\n");
                for (int i = 0; i < artigosEmPeriodicos.length; i++){
                    
                    ArtigoVO artigoVo= artigosEmPeriodicos[i];
                    xml.append("        <branch>").append("\n"); 
                    xml.append("            <attribute name=\"name\" value=\"").append(artigoVo.getRevista()).append("\"/>").append("\n");
                    xml.append("                <leaf>").append("\n");
                    xml.append("                   <attribute name=\"name\" value=\"Titulo: ").append(this.safeString(artigoVo.getTitulo()).replaceAll("\"", "")).append("\"/>").append("\n");
                    xml.append("                </leaf>").append("\n");
                    xml.append("                <leaf>").append("\n");
                    xml.append("                <attribute name=\"name\" value=\"Autores: ").append(this.safeString(artigoVo.getAutores())).append("\"/>").append("\n");
                    xml.append("                </leaf>").append("\n");
                    xml.append("                <leaf>").append("\n");
                    xml.append("                    <attribute name=\"name\" value=\"Volume: ").append(this.safeString(artigoVo.getVolume())).append("\"/>").append("\n");
                    xml.append("                </leaf>").append("\n");
                    xml.append("                <leaf>").append("\n");
                    xml.append("                    <attribute name=\"name\" value=\"DOI: ").append(this.safeString(artigoVo.getDoi())).append("\"/>").append("\n");
                    xml.append("                </leaf>").append("\n");
                    xml.append("                <leaf>").append("\n");
                    xml.append("                    <attribute name=\"name\" value=\"Paginas: ").append(this.safeString(artigoVo.getPaginas())).append("\"/>").append("\n");
                    xml.append("                </leaf>").append("\n");
                    xml.append("                <leaf>").append("\n");
                    xml.append("                    <attribute name=\"name\" value=\"Numero: ").append(this.safeString(artigoVo.getNumero())).append("\"/>").append("\n");
                    xml.append("                </leaf>").append("\n");
                    xml.append("        </branch>").append("\n");
                }
                xml.append("        </branch>").append("\n");
            }
        }
        
        if (artigosEmRevistasVO != null){
            
            xml.append("    <branch>").append("\n"); 
            xml.append("        <attribute name=\"name\" value=\"Artigos em Revistas\"/>").append("\n");
            for (int i = 0; i < artigosEmRevistasVO.length; i++){

                ArtigoRevistaVO vo = artigosEmRevistasVO[i];
                xml.append("    <branch>").append("\n"); 
                xml.append("        <attribute name=\"name\" value=\"").append(this.safeString(vo.getRevista())).append("\"/>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("            <attribute name=\"name\" value=\"Titulo: ").append(this.safeString(vo.getTitulo()).replaceAll("\"", "")).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\"Autores: ").append(this.safeString(vo.getAutores())).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\"Volume: ").append(this.safeString(vo.getVolume())).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\"DOI: ").append(this.safeString(vo.getDoi())).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\"Paginas: ").append(this.safeString(vo.getPaginas())).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\"Numero: ").append(this.safeString(vo.getNumero())).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("    </branch>").append("\n");

            }
            xml.append("    </branch>").append("\n");
            
        }
        
        if (jornal != null){
            
            TextoJornalVO vo = null;
            xml.append("    <branch>").append("\n"); 
            xml.append("        <attribute name=\"name\" value=\"Textos Em Jornal [").append(jornal.length).append("]\"/>").append("\n");
            for (int i = 0; i < jornal.length; i++){
                vo = jornal[i];
                
                xml.append("        <branch>").append("\n"); 
                xml.append("                <attribute name=\"name\" value=\"").append(this.safeString(vo.getNome_jornal())).append("\"/>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Titulo: ").append(this.safeString(vo.getTitulo()).replaceAll("\"", "")).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Autores: ").append(this.safeString(vo.getAutores())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Pagina: ").append(this.safeString(vo.getPaginas())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Volume: ").append(this.safeString(vo.getVolume())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Data: ").append(this.safeString(vo.getData())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Ano: ").append(this.safeString(vo.getAno())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("        </branch>").append("\n");
                
            }
            xml.append("    </branch>").append("\n"); 
             
        }
        
        if (trabalhoCompletoCongresso != null){
            TrabalhoCompletoVO vo = null;
            xml.append("    <branch>").append("\n"); 
            xml.append("        <attribute name=\"name\" value=\"Trabalho Completo Congresso [").append(trabalhoCompletoCongresso.length).append("]\"/>").append("\n");
            for (int i = 0; i < trabalhoCompletoCongresso.length; i++){
                
                vo = trabalhoCompletoCongresso[i];
                
                
                xml.append("        <branch>").append("\n"); 
                xml.append("                <attribute name=\"name\" value=\"").append(this.safeString(vo.getNomeEvento())).append("\"/>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Titulo: ").append(this.safeString(vo.getTitulo()).replaceAll("\"", "")).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Autores: ").append(this.safeString(vo.getAutores())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Ano: ").append(this.safeString(vo.getAno())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("        </branch>").append("\n");
                
            }
            xml.append("    </branch>").append("\n"); 
             
        }
        
        
        
        if (resumoCongressoVO != null){
            ResumoCongressoVO vo = null;
            xml.append("    <branch>").append("\n"); 
            xml.append("        <attribute name=\"name\" value=\"Resumo Completo Congresso [").append(resumoCongressoVO.length).append("]\"/>").append("\n");
            for (int i = 0; i < resumoCongressoVO.length; i++){
                
                vo = resumoCongressoVO[i];
                
                xml.append("        <branch>").append("\n"); 
                xml.append("                <attribute name=\"name\" value=\"").append(this.safeString(vo.getNomeEvento())).append("\"/>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Titulo: ").append(this.safeString(vo.getTitulo()).replaceAll("\"", "")).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Autores: ").append(this.safeString(vo.getAutores())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Ano: ").append(this.safeString(vo.getAno())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("        </branch>").append("\n");
                
            }
            xml.append("    </branch>").append("\n"); 
             
        }
        
        
        if (resumoExpandidoCongressoVO != null){
            ResumoCongressoExpandidoVO vo = null;
            xml.append("    <branch>").append("\n"); 
            xml.append("        <attribute name=\"name\" value=\"Resumo Congresso Expandido [").append(resumoExpandidoCongressoVO.length).append("]\"/>").append("\n");
            for (int i = 0; i < resumoExpandidoCongressoVO.length; i++){
                
                vo = resumoExpandidoCongressoVO[i];
                
                xml.append("        <branch>").append("\n"); 
                xml.append("                <attribute name=\"name\" value=\"").append(this.safeString(vo.getNomeEvento())).append("\"/>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Titulo: ").append(this.safeString(vo.getTitulo()).replaceAll("\"", "")).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Autores: ").append(this.safeString(vo.getAutores())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Ano: ").append(this.safeString(vo.getAno())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("        </branch>").append("\n");
                
            }
            xml.append("    </branch>").append("\n"); 
             
        }
        
        if (trabalhoApresentadoVO != null){
            TrabalhoApresentadoVO vo = null;
            xml.append("    <branch>").append("\n"); 
            xml.append("        <attribute name=\"name\" value=\"Trabalhos Apresentados [").append(trabalhoApresentadoVO.length).append("]\"/>").append("\n");
            for (int i = 0; i < trabalhoApresentadoVO.length; i++){
                
                vo = trabalhoApresentadoVO[i];
                
                xml.append("        <branch>").append("\n"); 
                xml.append("                <attribute name=\"name\" value=\"").append(this.safeString(vo.getTitulo())).append("\"/>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Autores: ").append(this.safeString(vo.getAutores())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("                <leaf>").append("\n");
                xml.append("                    <attribute name=\"name\" value=\"Ano: ").append(this.safeString(vo.getAno())).append("\"/>").append("\n");
                xml.append("                </leaf>").append("\n");
                xml.append("        </branch>").append("\n");
                
            }
            xml.append("    </branch>").append("\n"); 
             
        }
        
        //################################################
        //FIM DA TAG DE ABERTURA
        xml.append("    </branch>").append("\n");
        
        return xml.toString();
    }
    
    
    private String getXMLFormacaoAcademica(Pesquisador pesquisador){
        StringBuilder xml = new StringBuilder();

        FormacaoAcademica academica = pesquisador.getFormacaoAcademica();
        FormacaoVO vo = null;
        
        if (academica != null){
            List<FormacaoVO> list = academica.getFormacao();
            xml.append("    <branch>").append("\n");
            xml.append("        <attribute name=\"name\" value=\"Formacao Academica [").append(list.size()).append("]\"/>").append("\n");
            for (int i = 0; i < list.size(); i++) {
                vo = list.get(i);
                
                xml.append("        <branch>").append("\n"); 
                xml.append("            <attribute name=\"name\" value=\"").append(vo.getNomeInstituicao()).append("\"/>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\"Titulacao: ").append(this.safeString(vo.getTipo())).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\" ").append(this.safeString(vo.getDescricao())).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\"Ano Inicio: ").append(vo.getAnoInicio()).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("            <leaf>").append("\n");
                xml.append("                <attribute name=\"name\" value=\"Ano Conclusao: ").append(vo.getAnoConclusao()).append("\"/>").append("\n");
                xml.append("            </leaf>").append("\n");
                xml.append("        </branch>").append("\n"); 
            }
            xml.append("    </branch>").append("\n");; 
            
        }
        
        return xml.toString();
    }
    
    private String getXMLIdentificacao(Pesquisador p){
            
        StringBuilder xml = new StringBuilder();

        IdentificacaoVO identificacao = p.getIdentificacao();
        xml.append("    <branch>").append("\n");
        xml.append("        <attribute name=\"name\" value=\"Identificacao\"/>").append("\n");
        if (identificacao != null){
            
            xml.append("        <leaf>").append("\n");;
            xml.append("            <attribute name=\"name\" value=\"ID Lattes:").append(this.safeString(p.getId())).append("\"/>").append("\n");
            xml.append("        </leaf>").append("\n");
            xml.append("        <leaf>").append("\n");;
            xml.append("            <attribute name=\"name\" value=\"Nome:").append(this.safeString(identificacao.getNomeCompleto())).append("\"/>").append("\n");
            xml.append("        </leaf>").append("\n");
            xml.append("        <leaf>").append("\n");
            xml.append("            <attribute name=\"name\" value=\"Nome Citacao:").append(this.safeString(identificacao.getNomeCitacaoBibliografica())).append("\"/>").append("\n");
            xml.append("        </leaf>").append("\n");
            xml.append("        <leaf>").append("\n");
            xml.append("            <attribute name=\"name\" value=\"Sexo:").append(this.safeString(identificacao.getSexo())).append("\"/>").append("\n");
            xml.append("        </leaf>").append("\n");

        }
        xml.append("    </branch>").append("\n");;
        
        return xml.toString();
    }
    
    private String safeString(String s){
        if (s == null){
            return "";
        }
        
        return XtractorHelper.nullToEmpty(s).replace("\"", "");
    }
    
}
