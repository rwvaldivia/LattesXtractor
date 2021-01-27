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
import br.xtractor.lattes.vo.FormacaoVO;
import br.xtractor.lattes.vo.IdentificacaoVO;
import br.xtractor.lattes.vo.Pesquisador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gustavov
 */
public class RDFLattesGeneratorManual {
    
    private CurriculoLattes cv      = null;
    private String filePath         = null;
    private StringBuilder builder   = null;
    private List<NameSpaceResources> resources = null;
    
    public RDFLattesGeneratorManual(String filePath, CurriculoLattes cv){
        
        if ("".equals(filePath) || cv == null) {
            throw new IllegalArgumentException("Nome de arquivo ou Instancia CurriculoLattes null");
        }
        
        this.filePath = filePath;
        this.cv = cv;
        this.prepareRDF();
    }
    
    private void createRDFResources(){
        this.resources = new ArrayList<NameSpaceResources>();
        this.resources.add(new NameSpaceResources("xmlns:bio=\"http://purl.org/ontology/bibo/", "Bibliographic Ontology"));
        this.resources.add(new NameSpaceResources("xmlns:time=\"http://www.w3.org/TR/owl-time/\"", "Time Ontology"));
        this.resources.add(new NameSpaceResources("xmlns:aiiso=\"http://vocab.org/aiiso/schema\"", "Academic Institution Internal Structure Ontology (AIISO)"));
        this.resources.add(new NameSpaceResources("xmlns:bibo=\"http://xmlns.com/foaf/spec/\" ", "Friend of a Friend"));
        
    }
    
    private String breakLine(){
        return "\n";
    }
    
    private boolean isEmpty(String s){
        return XtractorHelper.nullToEmpty(s).trim().equals("")? true: false;
    }
    
    private void prepareRDF(){
        
        builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\"?>").append(this.breakLine());
        builder.append("<rdf:RDF").append(this.breakLine());
        
        
        builder.append("    xmlns:time=\"http://www.w3.org/TR/owl-time/\"").append(this.breakLine());//- Time Ontology;
        builder.append("    xmlns:aiiso=\"http://vocab.org/aiiso/schema\"").append(this.breakLine()); //- Academic Institution Internal Structure Ontology (AIISO)
        builder.append("    xmlns:bibo=\"http://xmlns.com/foaf/spec/\" ").append(this.breakLine());// - Friend of a Friend
        builder.append("    xmlns:bibo=\"http://purl.org/ontology/bibo/").append(this.breakLine());// - Bibliographic Ontology
        builder.append("    xmlns:geo=\"http://www.geonames.org/ontology#\"").append(this.breakLine());// - Geografical Names
        builder.append("    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">").append(this.breakLine());//
        
        builder.append(this.breakLine());
        
        Pesquisador p = null;
        for (int i = 0; i < cv.getPesquisador().size(); i++) {
            
            p = cv.getPesquisador().get(i);
            if (p == null) {
                continue;
            }
            builder.append("   <rdf:Description rdf:about=\"http://lattes.cnpq.br/\">").append(this.breakLine());
            this.createDadosPessoais(builder, p);
            this.createDadosAcademicos(builder, p);
            
            builder.append("   </rdf:Description>").append(this.breakLine()).append(this.breakLine());
            
        }
        builder.append("</rdf:RDF>").append(this.breakLine());;
        
        
    }
    
    private void createDadosAcademicos(StringBuilder builder, Pesquisador p){
        FormacaoAcademica fa = p.getFormacaoAcademica();
        FormacaoVO formacao = null;
        if (fa == null) {
            return;
        }
        
        List<FormacaoVO> list = fa.getFormacao();
        if (list == null) {
            return;
        }
        
        builder.append(this.breakLine());
        for (int i = 0; i < list.size(); i++){
            formacao = list.get(i);
            if (formacao == null){
                continue;
            }
            //builder.append("   <rdf:foaf foaf:name=\"").append().append("\">").append(this.breakLine());
            builder.append("        <aiiso:Organization>").append(this.breakLine());
            
            if (!this.isEmpty(formacao.getNomeInstituicao())){
                builder.append("            <aiiso:Institution>").append(this.breakLine());
                builder.append("                ").append(formacao.getNomeInstituicao()).append(this.breakLine());
                builder.append("            </aiiso:Institution>").append(this.breakLine());
            }
            
            if (!this.isEmpty(formacao.getDescricao())){
                builder.append("            <aiiso:Course time:hasBeginning=\"").append(formacao.getAnoInicio()).append("\" time:hasEnd=\"").append(formacao.getAnoConclusao()).append("\" >").append(this.breakLine());
                builder.append("                ").append(formacao.getTipo()).append(this.breakLine());
                builder.append("            </aiiso:Course>").append(this.breakLine());
            }
            
            builder.append("        </aiiso:Organization>").append(this.breakLine());
            
        }
        builder.append(this.breakLine());
    }
    
    private void createDadosPessoais(StringBuilder builder, Pesquisador p){
        
        IdentificacaoVO identificacao = p.getIdentificacao();
        if (identificacao == null){
            return;
        }
        
        builder.append("        <foaf:homepage>").append(this.breakLine());
        builder.append("            <rdf:type rdf:resource=\"http://lattes.cnpq.br/").append(p.getId()).append("\" />").append(this.breakLine());
        builder.append("        </foaf:homepage>").append(this.breakLine());
        builder.append("        <foaf:name>").append(identificacao.getNomeCompleto()).append("</foaf:name>").append(this.breakLine());
        builder.append("        <bibo:sufixName>").append(identificacao.getNomeCitacaoBibliografica()).append("</bibo:sufixName>").append(this.breakLine());
    }
    
    public void RDF_toTerminal(){
        System.out.println(builder.toString());
    }
    
    public void RDF_toFile() {
        
    }
    
    private class NameSpaceResources {
        String resource     = "";
        String description  = "";
        
        public NameSpaceResources(String resource, String description){
            this.resource = resource;
            this.description = description;
        }
        
        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        
    }
    
}
