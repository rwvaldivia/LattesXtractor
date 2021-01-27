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

import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.vo.ArtigoRevistaVO;
import br.xtractor.lattes.vo.ArtigoVO;
import br.xtractor.lattes.vo.CapitulosLivrosVO;
import br.xtractor.lattes.vo.DissertacaoVO;
import br.xtractor.lattes.vo.EnderecoVO;
import br.xtractor.lattes.vo.EventoVO;
import br.xtractor.lattes.vo.FormacaoVO;
import br.xtractor.lattes.vo.IdiomaVO;
import br.xtractor.lattes.vo.IniciacaoCientificaVO;
import br.xtractor.lattes.vo.MonografiaVO;
import br.xtractor.lattes.vo.OrientacaoOutrosTiposVO;
import br.xtractor.lattes.vo.Pesquisador;
import br.xtractor.lattes.vo.ProducaoVO;
import br.xtractor.lattes.vo.ProdutoVO;
import br.xtractor.lattes.vo.ProjetoVO;
import br.xtractor.lattes.vo.ResumoCongressoExpandidoVO;
import br.xtractor.lattes.vo.ResumoCongressoVO;
import br.xtractor.lattes.vo.SoftwareVO;
import br.xtractor.lattes.vo.SupervisaoVO;
import br.xtractor.lattes.vo.TccVO;
import br.xtractor.lattes.vo.TeseVO;
import br.xtractor.lattes.vo.TextoJornalVO;
import br.xtractor.lattes.vo.TrabalhoCompletoVO;
import br.xtractor.lattes.vo.TrabalhoTecnicoVO;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author gustavov
 */
public class RDFLattesGenerator {
    private String filePath;
    private CurriculoLattes cv;
    private Model model;
    private RDFResourcesMap resourcesMap;
    private String lattesStatusAndamento = "em andamento";
    private String lattesStatusConcluido = "conclued";
    
    
    public RDFLattesGenerator(CurriculoLattes cv, String filePath){
        
        if ("".equals(filePath) || cv == null) {
           throw new IllegalArgumentException("Nome de arquivo ou Instancia CurriculoLattes null");
        }
        this.cv = cv;
        this.filePath = filePath;
        this.resourcesMap = RDFResourcesMap.getSingletonInstance();
        
    }
    
    private void processRDF(Pesquisador pesquisador){
        
        Resource lattes = null;
        this.model = ModelFactory.createDefaultModel();
        String resourceURI = this.resourcesMap.getResourceURINoHash(RDFResourcesMap.LATTES_URI) + "/" + pesquisador.getId();
          
        lattes = this.model.createResource(resourceURI);
        
        this.createDadosPessoais(lattes, pesquisador);
        this.createEndereco(lattes, pesquisador);
        
        this.createIdiomas(lattes, pesquisador);
        this.createFormacaoAcademica(lattes, pesquisador);
        this.createProjetosPesquisa(lattes, pesquisador);
        this.createColaboradores(lattes, pesquisador);
        this.createProducaoCientificaArtigosPeriodico(lattes, pesquisador);
        this.createProducaoCientificaCapituloLivros(lattes, pesquisador);
        this.createProducaoCientificaTextoEmJornal(lattes, pesquisador);
        this.createProducaoCientificaTrabalhoCongresso(lattes, pesquisador);//Resumo / ResumoExpandido / TrabalhoCompleto
        this.createProducaoArtigosEmRevista(lattes, pesquisador);
        this.createProducaoBibliografica(lattes, pesquisador);
        this.createOrganizacaoDeEvento(lattes, pesquisador);
        this.createParticipacaoDeEvento(lattes, pesquisador);
        
        this.createSupervisaoPosdocAndamento(lattes, pesquisador);
        this.createSupervisaoPosdocConcluido(lattes, pesquisador);
        
        this.createOrientacaoDoutoradoAndamento(lattes, pesquisador);
        this.createOrientacaoDoutoradoConcluido(lattes, pesquisador);
        
        this.createOrientacaoMestradoAndamento(lattes, pesquisador);
        this.createOrientacaoMestradoConcluido(lattes, pesquisador);
        
        this.createOrientacaoMonografiaEspecializacaoAndamento(lattes, pesquisador);
        this.createOrientacaoMonografiaEspecializacaoConcluida(lattes, pesquisador);
        
        this.createOrientacaoTccAndamento(lattes, pesquisador);
        this.createOrientacaoTccConcluida(lattes, pesquisador);
        
        this.createOrientacaoIniciacaoCientificaEmAndamento(lattes, pesquisador);
        this.createOrientacaoIniciacaoCientificaConcluida(lattes, pesquisador);
        
        
        this.createOrientacaoOutrosTiposAndamento(lattes, pesquisador);
        this.createOrientacaoOutrosTipoConcluidos(lattes, pesquisador);
        
        this.createTrabalhoSoftware(lattes, pesquisador);
        
        this.createProducaoTecnica(lattes, pesquisador);
        //producao_artistica
        
    }
    
    private void createProducaoTecnica(Resource lattes, Pesquisador p){
        
        ProdutoVO           [] processoTecnica      = p.getProcessoTecnicaVO();
        ProdutoVO           [] produtoTecnologico   = p.getProdutoTecnologicoVO();
        ProducaoVO          [] producaoTecnica      = p.getProducaoTecnicaVO();
        TrabalhoTecnicoVO   [] trabalhoTecnico      = p.getTrabalhosTecnicosVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        
        if (processoTecnica != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < processoTecnica.length; i++) {
                
                internalResource = this.model.createResource();
                try {
                    internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(processoTecnica[i].getAno())));
                } catch (Exception e){}
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), processoTecnica[i].getAutores());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), processoTecnica[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), processoTecnica[i].getNatureza());
                
                bag.addProperty(this.newProperty(urilLattes, "Product"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "TecnicalProduct"), bag);
        }
        
        if (produtoTecnologico != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < produtoTecnologico.length; i++) {
                
                internalResource = this.model.createResource();
                try {
                    internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(produtoTecnologico[i].getAno())));
                } catch (Exception e){}
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), produtoTecnologico[i].getAutores());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), produtoTecnologico[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), produtoTecnologico[i].getNatureza());
                
                bag.addProperty(this.newProperty(urilLattes, "Product"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "TecnicalProduct"), bag);
        }
        
        if (producaoTecnica != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < producaoTecnica.length; i++) {
                
                internalResource = this.model.createResource();
                try {
                    internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(producaoTecnica[i].getAno())));
                } catch (Exception e){}
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), producaoTecnica[i].getAutores());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), producaoTecnica[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), producaoTecnica[i].getNatureza());
                
                bag.addProperty(this.newProperty(urilLattes, "AcademicProduct"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "TecnicalProduct"), bag);
        }
        
        if (trabalhoTecnico != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < trabalhoTecnico.length; i++) {
                
                internalResource = this.model.createResource();
                try {
                    internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(trabalhoTecnico[i].getAno())));
                } catch (Exception e){}
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), trabalhoTecnico[i].getAutores());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), trabalhoTecnico[i].getTitulo());
                bag.addProperty(this.newProperty(urilLattes, "AcademicProduct"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "TecnicalProduct"), bag);
        }
        
    }
    
    private void createTrabalhoSoftware(Resource lattes, Pesquisador p){
        
        SoftwareVO [] swcp        = p.getSoftwareComPatenteVO();
        SoftwareVO [] swsp        = p.getSoftwareSemPatenteVO();
        
        if (swcp == null && swsp == null){
            return;
        }
        
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String uriDbPedia               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsDebpediaPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsDebpediaPrefix, uriDbPedia);
        
        if (swcp != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < swcp.length; i++) {
                internalResource = this.model.createResource();
                try {
                    internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(swcp[i].getAno())));
                } catch (Exception e){}
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), swcp[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), swcp[i].getAutores());
                bag.addProperty(this.newProperty(urilLattes, "Software"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "SoftwareComPatent"), bag);
        }
        
        if (swsp != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < swsp.length; i++) {
                internalResource = this.model.createResource();
                try {
                    internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(swcp[i].getAno())));
                } catch (Exception e){}
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), swsp[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), swsp[i].getAutores());
                bag.addProperty(this.newProperty(urilLattes, "Software"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "SoftwareSemPatent"), bag);
        }
        
    }
    
    private void createOrientacaoOutrosTipoConcluidos(Resource lattes, Pesquisador p){
        
        OrientacaoOutrosTiposVO [] vo     = p.getOrientacoesOutrosTiposConcluidaVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        
        if (vo != null){
            
            Resource internalResource   = null;
            Resource financialAgency    = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoOrientacao());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusConcluido);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoOutrosConcluido"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createOrientacaoOutrosTiposAndamento(Resource lattes, Pesquisador p){
        
        OrientacaoOutrosTiposVO [] vo     = p.getOrientacaoOutrosTiposAndamentoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Resource financialAgency = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoOrientacao());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusConcluido);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoOutros"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createOrientacaoIniciacaoCientificaConcluida(Resource lattes, Pesquisador p){
        
        IniciacaoCientificaVO [] vo     = p.getOrientacoesIniciacaoCientificaConcluidaVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Resource financialAgency = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoOrientacao());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusConcluido);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoIniciacaoCientifica"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    
    private void createOrientacaoIniciacaoCientificaEmAndamento(Resource lattes, Pesquisador p){
        
        IniciacaoCientificaVO [] vo = p.getOrientacoesIniciacaoCientificaAndamentoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource   = null;
            Resource financialAgency    = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoOrientacao());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusAndamento);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoIniciacaoCientifica"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createOrientacaoTccConcluida(Resource lattes, Pesquisador p){
        
        TccVO [] vo = p.getOrientacoesTccConcluidasVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Resource financialAgency = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusConcluido);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoTcc"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    
    
    
    private void createOrientacaoTccAndamento(Resource lattes, Pesquisador p){
        
        TccVO [] vo = p.getOrientacoesTccAndamentoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Resource financialAgency = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusAndamento);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoTcc"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createOrientacaoMonografiaEspecializacaoConcluida(Resource lattes, Pesquisador p){
        
        MonografiaVO [] vo = p.getOrientacoesEspecializacaoConcluidosVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoDeOrientacao());
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusConcluido);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoEspecializacao"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createOrientacaoMonografiaEspecializacaoAndamento(Resource lattes, Pesquisador p){
        
        MonografiaVO [] vo = p.getOrientacaoMonograiaEspecializacaoAndamentoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoDeOrientacao());
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusAndamento);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoMonografiaEspecializacao"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createOrientacaoMestradoConcluido(Resource lattes, Pesquisador p){
        
        DissertacaoVO [] vo = p.getOrientacoesMestradoConcluidosVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Resource financialAgency = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoDeOrientacao());
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaDeFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusConcluido);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoMestrado"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    
    
    
    private void createOrientacaoMestradoAndamento(Resource lattes, Pesquisador p){
        
        DissertacaoVO [] vo = p.getOrientacaoMestradoEmAndamentoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Resource financialAgency = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoDeOrientacao());
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaDeFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusAndamento);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoMestrado"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createOrientacaoDoutoradoConcluido(Resource lattes, Pesquisador p){
        
        TeseVO [] vo = p.getOrientacoesDoutoradoConcluidosVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Resource financialAgency = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoDeOrientacao());
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaDeFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusConcluido);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoDoutorado"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createSupervisaoPosdocConcluido(Resource lattes, Pesquisador p){
        
        SupervisaoVO [] vo = p.getSupervisaoPosDoutConcluidoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoOrientacao());
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusConcluido);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "SupervisaoPosDoc"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Supervisao"), bag);
        }
    }
    
    
    private void createSupervisaoPosdocAndamento(Resource lattes, Pesquisador p){
        
        SupervisaoVO [] vo = p.getSupervisoesDoutAndamentoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        this.model.setNsPrefix(nsFoafPrefix, uriFoaf);
        
        if (vo != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoOrientacao());
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getInstituicao());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusAndamento);
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                bag.addProperty(this.newProperty(urilLattes, "SupervisaoPosDoc"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "SupervisaoAcademica"), bag);
        }
    }
    
    
    private void createOrientacaoDoutoradoAndamento(Resource lattes, Pesquisador p){
        
        TeseVO [] vo = p.getOrientacoesDoutoradoEmAndamentoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String urilAcademic             = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriFoaf                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_FOAF);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsAcademPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsFoafPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        this.model.setNsPrefix(nsAcademPrefix, urilAcademic);
        
        if (vo != null){
            
            Resource internalResource = null;
            Resource financialAgency = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTipoDeOrientacao());
                internalResource.addLiteral(this.newProperty(urilAcademic, "Institution"), vo[i].getTituloTrabalho());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTituloTrabalho());
                
                financialAgency = this.model.createResource();
                financialAgency.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getAgenciaDeFomento());
                internalResource.addProperty(this.newProperty(urilLattes, "FinancialAgency"), financialAgency);
                
                
                internalResource.addLiteral(this.newProperty(uriFoaf, "name"), vo[i].getNomeAluno());
                internalResource.addLiteral(this.newProperty(urilLattes, "status"), this.lattesStatusAndamento);
                bag.addProperty(this.newProperty(urilLattes, "OrientacaoDoutorado"), internalResource);
            }
            lattes.addProperty(this.newProperty(urilLattes, "Orientacao"), bag);
        }
    }
    
    private void createParticipacaoDeEvento(Resource lattes, Pesquisador p){
        EventoVO [] vo = p.getOrganizacaoEventosVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        
        if (vo != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getNatureza());
                bag.addProperty(this.newProperty(urilLattes, "EventParticipant"), internalResource);
            }
            lattes.addProperty(this.newProperty(uriBibo, "Event"), bag);
        }
    }
    
    private void createOrganizacaoDeEvento(Resource lattes, Pesquisador p){
        EventoVO [] vo = p.getOrganizacaoEventosVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String urilLattes               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsLattesPrefix           = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsLattesPrefix, urilLattes);
        
        if (vo != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getNatureza());
                bag.addProperty(this.newProperty(urilLattes, "EventOrganizer"), internalResource);
            }
            lattes.addProperty(this.newProperty(uriBibo, "Event"), bag);
        }
    }
    
    private void createProducaoBibliografica(Resource lattes, Pesquisador pesquisador){
        
        ProducaoVO [] vo = pesquisador.getProducoesBibliograficasVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        
        if (vo != null){
            
            Resource internalResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < vo.length; i++) {
                
                internalResource = this.model.createResource();
                
                internalResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(vo[i].getAno())));
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), vo[i].getAutores());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getNatureza());
                bag.addProperty(this.newProperty(uriBibo, "Workshop"), internalResource);
            }
            
            lattes.addProperty(this.newProperty(uriBibo, "BibliographicResource"), bag);
        }
    }
    
    private void createProducaoArtigosEmRevista(Resource lattes, Pesquisador p){
        ArtigoRevistaVO [] vo = p.getArtigosEmRevistaVO();
        
        if (vo == null){
            return;
        }
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String uriDbPedia               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String uriPrism                 = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_PUBLISHING);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBIBOPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsDebpediaPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String nsPrismPrefix            = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_PUBLISHING);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBIBOPrefix, uriBibo);
        this.model.setNsPrefix(nsDebpediaPrefix, uriDbPedia);
        this.model.setNsPrefix(nsPrismPrefix, uriPrism);
        
        
        Resource internalResource = null;
        Bag bag = this.model.createBag();
        
        for (int i = 0; i < vo.length; i++){
            internalResource = this.model.createResource();
            
            vo[i].getNumero();
                        
            internalResource.addLiteral(this.newProperty(uriTime, "year"), vo[i].getAno());
            internalResource.addLiteral(this.newProperty(uriBibo, "doi"), vo[i].getDoi());
            internalResource.addLiteral(this.newProperty(uriBibo, "number"), vo[i].getNumero());
            internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTitulo());
            internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), vo[i].getAutores());
            internalResource.addLiteral(this.newProperty(uriBibo, "pages"), vo[i].getPaginas());
            internalResource.addLiteral(this.newProperty(uriPrism, "volume"), vo[i].getVolume());
            internalResource.addLiteral(this.newProperty(uriBibo, "distributor"), vo[i].getRevista());
            
            bag.addProperty(this.newProperty(uriBibo, "Document"), internalResource);
            
        }
        
        lattes.addProperty(this.newProperty(uriBibo, "Periodical"), bag);
        
    }
    
    private void createProducaoCientificaTrabalhoCongresso(Resource lattes, Pesquisador p){
        
        TrabalhoCompletoVO          [] tc = p.getTrabalhoCompletoCongressoVO();
        ResumoCongressoVO           [] rc = p.getResumoCongressoVO();
        ResumoCongressoExpandidoVO  [] re = p.getResumoCongressoExpandidoVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String uriDbPedia               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String uriPrism                 = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_PUBLISHING);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBIBOPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsDebpediaPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String nsPrismPrefix            = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_PUBLISHING);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBIBOPrefix, uriBibo);
        this.model.setNsPrefix(nsDebpediaPrefix, uriDbPedia);
        this.model.setNsPrefix(nsPrismPrefix, uriPrism);
        
        if (tc == null && rc == null && re == null){
            return;
        }
        
        Resource internalResource = null;
        Bag bag = this.model.createBag();
        
        if (tc != null) {
            for (int i = 0; i < tc.length; i++){

                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), tc[i].getAno());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), tc[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), tc[i].getNomeEvento());
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), tc[i].getAutores());
                internalResource.addLiteral(this.newProperty(uriBibo, "doi"), tc[i].getDoi());
                internalResource.addLiteral(this.newProperty(uriBibo, "pages"), tc[i].getPaginas());
                internalResource.addLiteral(this.newProperty(uriPrism, "volume"), tc[i].getVolume());
                bag.addProperty(this.newProperty(uriBibo, "Document"), internalResource);
            }
        }
        
        if (rc != null) {
            for (int i = 0; i < rc.length; i++){

                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), rc[i].getAno());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), rc[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), rc[i].getNomeEvento());
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), rc[i].getAutores());
                internalResource.addLiteral(this.newProperty(uriBibo, "doi"), rc[i].getDoi());
                internalResource.addLiteral(this.newProperty(uriBibo, "pages"), rc[i].getPaginas());
                internalResource.addLiteral(this.newProperty(uriPrism, "volume"), rc[i].getVolume());
                bag.addProperty(this.newProperty(uriBibo, "Document"), internalResource);
            }
        }
        
        if (re != null) {
            for (int i = 0; i < re.length; i++){

                internalResource = this.model.createResource();
                internalResource.addLiteral(this.newProperty(uriTime, "year"), re[i].getAno());
                internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), re[i].getTitulo());
                internalResource.addLiteral(this.newProperty(uriBibo, "description"), re[i].getNomeEvento());
                internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), re[i].getAutores());
                internalResource.addLiteral(this.newProperty(uriBibo, "doi"), re[i].getDoi());
                internalResource.addLiteral(this.newProperty(uriBibo, "pages"), re[i].getPaginas());
                internalResource.addLiteral(this.newProperty(uriPrism, "volume"), re[i].getVolume());
                bag.addProperty(this.newProperty(uriBibo, "Document"), internalResource);
            }
        }
        
        lattes.addProperty(this.newProperty(uriDbPedia, "Conference"), bag);
    }
    
    
    private void createProducaoCientificaTextoEmJornal(Resource lattes, Pesquisador p){
        
        TextoJornalVO [] vo = p.getTextosEmJornalVO();
        
        if (vo == null){
            return;
        }
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String uriDbPedia               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String uriPrism                 = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_PUBLISHING);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBIBOPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsDebpediaPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String nsPrismPrefix            = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_PUBLISHING);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBIBOPrefix, uriBibo);
        this.model.setNsPrefix(nsDebpediaPrefix, uriDbPedia);
        this.model.setNsPrefix(nsPrismPrefix, uriPrism);
        
        Resource internalResource = null;
        Bag bag = this.model.createBag();
        
        for (int i = 0; i < vo.length; i++){
            internalResource = this.model.createResource();
            
            internalResource.addLiteral(this.newProperty(uriTime, "year"), vo[i].getAno());
            internalResource.addLiteral(this.newProperty(uriTime, "inDateTime"), vo[i].getData());
            internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getTitulo());
            internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), vo[i].getAutores());
            internalResource.addLiteral(this.newProperty(uriBibo, "pages"), vo[i].getPaginas());
            internalResource.addLiteral(this.newProperty(uriPrism, "volume"), vo[i].getVolume());
            internalResource.addLiteral(this.newProperty(uriBibo, "distributor"), vo[i].getNome_jornal());
            
            bag.addProperty(this.newProperty(uriBibo, "Document"), internalResource);
            
        }
        
        lattes.addProperty(this.newProperty(uriBibo, "Newspaper"), bag);
        
    }
    
    private void createProducaoCientificaCapituloLivros(Resource lattes, Pesquisador p){
        CapitulosLivrosVO [] vo = p.getCapitulosLivrosVO();
            
        if (vo == null){
            return;
        }
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String uriDbPedia               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String uriPrism                 = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_PUBLISHING);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBIBOPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsDebpediaPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String nsPrismPrefix            = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_PUBLISHING);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBIBOPrefix, uriBibo);
        this.model.setNsPrefix(nsDebpediaPrefix, uriDbPedia);
        this.model.setNsPrefix(nsPrismPrefix, uriPrism);
        
        
        Resource internalResource = null;
        Bag bag = this.model.createBag();
        
        for (int i = 0; i < vo.length; i++){
            internalResource = this.model.createResource();
            
            internalResource.addLiteral(this.newProperty(uriTime, "year"), vo[i].getAno());
            internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getLivro());
            internalResource.addLiteral(this.newProperty(uriBibo, "description"), vo[i].getTitulo());
            internalResource.addLiteral(this.newProperty(uriBibo, "authorList"), vo[i].getAutores());
            
            internalResource.addLiteral(this.newProperty(uriBibo, "edition"), vo[i].getEdicao());
            internalResource.addLiteral(this.newProperty(uriBibo, "pages"), vo[i].getPaginas());
            internalResource.addLiteral(this.newProperty(uriPrism, "volume"), vo[i].getVolume());
            internalResource.addLiteral(this.newProperty(uriBibo, "distributor"), vo[i].getEditora());
            
            bag.addProperty(this.newProperty(uriBibo, "BookSection"), internalResource);
            
        }
        
        lattes.addProperty(this.newProperty(uriDbPedia, "Book"), bag);
        
    }
    
    private void createColaboradores(Resource lattes, Pesquisador p) {
        
        String [] colaboradores = p.getColaboradoresStringArray();
        
        if (colaboradores == null) {
            return;
        }
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_TERMS);
        String uriDbPedia               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsDebpediaPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsDebpediaPrefix, uriDbPedia);
        
        Resource internalResource = null;
        Resource colabResource = null;
        Bag bag = this.model.createBag();
        
        String lattesURI = this.resourcesMap.getResourceURINoHash(RDFResourcesMap.LATTES_URI) + "/";
        
        for (int i = 0; i < colaboradores.length; i++){
            internalResource = this.model.createResource();
            
            colabResource = this.model.createResource(lattesURI + "" +colaboradores[i]);
            
            internalResource.addProperty(this.newProperty(uriDublinCore, "homepage"), colabResource);
            bag.addProperty(this.newProperty(uriDbPedia, "contributor"), internalResource);
        }
        
        lattes.addProperty(this.newProperty(uriDbPedia, "contributorList"), bag);
        
    }
    
    private void createProjetosPesquisa(Resource lattes, Pesquisador p){
        
        ProjetoVO [] vo = p.getProjetosPesquisaVO();
        
        if (vo == null){
            return;
        }
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriAcademic              = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        String uriDbPedia               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsAcademicPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        String nsDebpediaPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsAcademicPrefix, uriAcademic);
        this.model.setNsPrefix(nsDebpediaPrefix, uriDbPedia);
        
        
        Resource internalResource = null;
        Bag bag = this.model.createBag();
        
        for (int i = 0; i < vo.length; i++){
            internalResource = this.model.createResource();
            
            internalResource.addLiteral(this.newProperty(uriDublinCore, "title"), vo[i].getDescricao());
            internalResource.addLiteral(this.newProperty(uriAcademic, "description"), vo[i].getDescricao());
            internalResource.addLiteral(this.newProperty(uriTime, "hasBeginning"), vo[i].getAnoInicio());
            internalResource.addLiteral(this.newProperty(uriTime, "hasEnd"), vo[i].getAnoConclusao());
                    
            bag.addProperty(this.newProperty(uriDbPedia, "ResearchProject"), internalResource);

        }
        
        lattes.addProperty(this.newProperty(uriDbPedia, "Project"), bag);
        
    }
    
    private void createEndereco(Resource lattes, Pesquisador p) {
        
        EnderecoVO vo = p.getEndereco();
        if (vo == null){
            return;
        }
        
        String uriSpatial       = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_SPATIAL_THINGS);
        String uriAddress       = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ADDRESS_TALIS);;
        
        String nsSpatialThingsPrefix        = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_SPATIAL_THINGS);
        String nsAddressPrefix              = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ADDRESS_TALIS);
        
        this.model.setNsPrefix(nsSpatialThingsPrefix, uriSpatial);
        this.model.setNsPrefix(nsAddressPrefix, uriAddress);
        
        //################################################
        //Para manter a compatibilidade com o scriptlattes
        if (vo.getLogradouro() == null) {
            lattes.addLiteral(this.newProperty(uriSpatial, "lat"), vo.getEnderecoProfissionalLat());
            lattes.addLiteral(this.newProperty(uriSpatial, "long"), vo.getEnderecoProfissionalLong());
            lattes.addLiteral(this.newProperty(uriAddress, "localityName"), vo.getEnderecoProfissional());
        } else {
            lattes.addLiteral(this.newProperty(uriAddress, "streetAddress"), vo.getLogradouro());
            lattes.addLiteral(this.newProperty(uriAddress, "postalCode"), vo.getCep());
            lattes.addLiteral(this.newProperty(uriAddress, "localityName"), vo.getBairro() + " - " + vo.getCidade());
            lattes.addLiteral(this.newProperty(uriSpatial, "lat"), vo.getEnderecoProfissionalLat());
            lattes.addLiteral(this.newProperty(uriSpatial, "long"), vo.getEnderecoProfissionalLong());
            lattes.addLiteral(this.newProperty(uriAddress, "countryName"), vo.getPais());
            lattes.addLiteral(this.newProperty(uriAddress, "regionName"), vo.getUf());
        }
        
    }
    
    private void createFormacaoAcademica(Resource lattes, Pesquisador p){
        FormacaoVO [] vo = p.getFormacaoAcademicaVO();
        
        if (vo == null) {
            return;
        }
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String uriAcademic              = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_ACADEMIC);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        String nsAcademicPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_ACADEMIC);
        
        String uriDbPedia               = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String nsDebpediaPrefix         = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        this.model.setNsPrefix(nsAcademicPrefix, uriAcademic);
        this.model.setNsPrefix(nsDebpediaPrefix, uriDbPedia);
        
        Bag bag = this.model.createBag();
        Resource formacao = null;
        
        for (int i = 0; i < vo.length; i++){
            
            formacao = this.model.createResource();
            
            formacao.addLiteral(this.newProperty(uriAcademic, "Faculty"), vo[i].getNomeInstituicao());
            formacao.addLiteral(this.newProperty(uriAcademic, "description"), vo[i].getDescricao());
            formacao.addLiteral(this.newProperty(uriTime, "hasBeginning"), vo[i].getAnoInicio());
            formacao.addLiteral(this.newProperty(uriTime, "hasEnd"), vo[i].getAnoConclusao());
            formacao.addLiteral(this.newProperty(uriAcademic, "degree"), vo[i].getTipo());
            
            bag.addProperty(this.newProperty(uriBibo, "Institution"), formacao);
        }
        
        lattes.addProperty(this.newProperty(uriDbPedia, "EducationalInstitution"), bag);
    }
    
    private void createProducaoCientificaArtigosPeriodico(Resource lattes, Pesquisador pesquisador){
        
        ArtigoVO [] artigosPeriodicos   = pesquisador.getArtigosEmPeriodicosVO();
        
        String uriDublinCore            = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String uriTime                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_TIME);
        String uriBibo                  = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        
        String nsTimePrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_TIME);
        String nsDublinCorePrefix       = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS);
        String nsBiboPrefix             = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL);
        
        this.model.setNsPrefix(nsTimePrefix, uriTime);
        this.model.setNsPrefix(nsDublinCorePrefix, uriDublinCore);
        this.model.setNsPrefix(nsBiboPrefix, uriBibo);
        
        if (artigosPeriodicos != null){
            
            Resource publicationResource = null;
            Bag bag = this.model.createBag();
            
            for (int i = 0; i < artigosPeriodicos.length; i++) {
                
                publicationResource = this.model.createResource();
                
                publicationResource.addLiteral(this.newProperty(uriTime, "year"), this.model.createTypedLiteral(new Integer(artigosPeriodicos[i].getAno())));
                publicationResource.addLiteral(this.newProperty(uriDublinCore, "title"), artigosPeriodicos[i].getTitulo());
                publicationResource.addLiteral(this.newProperty(uriBibo, "authorList"), artigosPeriodicos[i].getAutores());
                publicationResource.addLiteral(this.newProperty(uriBibo, "doi"), artigosPeriodicos[i].getDoi());
                publicationResource.addLiteral(this.newProperty(uriBibo, "number"), artigosPeriodicos[i].getNumero());
                publicationResource.addLiteral(this.newProperty(uriBibo, "pages"), artigosPeriodicos[i].getPaginas());
                publicationResource.addLiteral(this.newProperty(uriBibo, "volume"), artigosPeriodicos[i].getVolume());
                publicationResource.addLiteral(this.newProperty(uriBibo, "publisher"), artigosPeriodicos[i].getRevista());
                
                bag.addProperty(this.newProperty(uriBibo, "AcademicArticle"), publicationResource);
            }
            
            lattes.addProperty(this.newProperty(uriBibo, "BibliographicResource"), bag);
        }
    }
   
    
    private void createDadosPessoais(Resource lattes, Pesquisador pesquisador){
        
        if (pesquisador.getIdentificacao() == null){
            return;
        }
        
        String uri      = this.resourcesMap.getResourceURINoHash(RDFResourcesMap.RESOURCE_FOAF);
        String nsPrefix = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_FOAF);
        
        Resource r      = this.model.createResource();
        String temp     = null;
        
        this.model.setNsPrefix(nsPrefix, uri);
        
        temp = this.getGender(pesquisador.getIdentificacao().getSexo());
        
        if (!"".equals(temp)){
            r.addLiteral(this.newProperty(uri, "gender"), temp);
        }
        
        r.addLiteral(this.newProperty(uri, "name"), pesquisador.getIdentificacao().getNomeCompleto());
        r.addLiteral(this.newProperty(uri, "nick"), pesquisador.getIdentificacao().getNomeCitacaoBibliografica());
        
        lattes.addProperty(this.newProperty(uri, "Person"), r);
    }
    
     private void createIdiomas(Resource lattes, Pesquisador pesquisador){
        IdiomaVO [] idiomas = pesquisador.getIdiomasVO();
        
        if (idiomas == null) {
            return;
        }
        
        String uri      = this.resourcesMap.getResourceURI(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        String nsPrefix = this.resourcesMap.getResourceNameSpacePrefix(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY);
        
        this.model.setNsPrefix(nsPrefix, uri);
        
        Bag bag = this.model.createBag();
        
        for (int i = 0; i < idiomas.length; i++){
            bag.addLiteral(this.newProperty(uri, "language"), idiomas[i].getNome());
        }
        
        lattes.addProperty(this.newProperty(uri, "Language"), bag);
    }
    
    private String getGender(String str){
        if (str != null) {
            str = str.trim();
            if (str.toUpperCase().equals("MASCULINO") || str.toUpperCase().equals("M")){
                return "male";
            }
            return "female";
        }
        return "";
    }
   
    private Property newProperty(String uri, String tagName){
        return this.model.createProperty(uri, tagName);
    }
    
    private void lixo3(Resource lattes, Pesquisador pesquisador){
        String uri = this.resourcesMap.getResourceURINoHash(RDFResourcesMap.RESOURCE_FOAF);
        Resource r = this.model.createResource();
        
        this.model.setNsPrefix("foaf", uri);
        
        r.addLiteral(this.newProperty(uri, "name"), pesquisador.getIdentificacao().getNomeCompleto());
        r.addProperty(this.newProperty(uri, "nick"), "L");
        r.addLiteral(this.newProperty(uri, "phone"), this.model.createTypedLiteral(new Integer(123)));
        
        lattes.addProperty(this.newProperty(uri, "Person"), r);
    }
    
    public void lixo2(){
    
        Model m = ModelFactory.createDefaultModel();
        String NS = "http://xmlns.com/foaf/0.1/";
        Property name = m.createProperty(NS + "name");
        Property holdsAccount = m.createProperty(NS + "holdsAccount");
        Property accountServiceHomepage = m.createProperty(NS + "accountServiceHomepage");
        Property accountName = m.createProperty(NS + "accountName");
        Resource Person = m.createResource(NS + "Person");
        Resource OnlineAccount = m.createResource(NS + "OnlineAccount");
        Resource OnlineChatAccount = m.createResource(NS + "OnlineChatAccount");

        Resource theAccount = m.createResource();
        theAccount.addProperty(RDF.type, OnlineAccount);
        theAccount.addProperty(RDF.type, OnlineChatAccount);
        theAccount.addProperty(accountServiceHomepage,
        m.createResource("http://www.freenode.net/irc_servers.shtml"));
        theAccount.addProperty(accountName, "danbri");

        Resource dan = m.createResource();
        dan.addProperty(RDF.type, Person);
        dan.addProperty(name, "Dan Brickley");
        dan.addProperty(holdsAccount, theAccount);

        //m.write(System.out, "RDF/XML");
        // or this, which produces different XML but is semantically
        // equivalent and looks nicer:
        m.write(System.out, "RDF/XML-ABBREV");
    
    }
    
    private void lixo() {
        System.out.println( "########################################" );
        Model m = ModelFactory.createDefaultModel();
        String nsA = "http://somewhere/else#";
        String nsB = "http://nowhere/else#";
        Resource root = m.createResource( nsA + "root" );
        
        Property P1 = m.createProperty( nsA + "P" );
        Property P2 = m.createProperty( nsB + "Q" );
        
        Resource resourceX = m.createResource( nsA + "x" );
        Resource resourceY = m.createResource( nsA + "y" );
        Resource resourceZ = m.createResource( nsA + "z" );
        
        m.add( root, P1, resourceX ).add( root, P1, resourceY ).add( resourceY, P2, resourceZ );
        System.out.println( "# -- no special prefixes defined" );
        m.write( System.out );
        System.out.println( "# -- nsA defined" );
        m.setNsPrefix( "nsA", nsA );
        m.write( System.out );
        System.out.println( "# -- nsA and cat defined" );
        m.setNsPrefix( "cat", nsB );
        m.write( System.out );
    
    
    }
    
    /*
    private void addNewRDFTagResource(Resource resource, String stringURI, String value,  String uriNameSpace, String tagName){
        
        Property internalProperty = this.model.createProperty(stringURI + tagName);
        this.model.add(resource, internalProperty, this.model.createResource(stringURI + value));
        this.model.setNsPrefix(uriNameSpace, stringURI);
    }
    
    private void addNewRDFTagLiteral(Resource resource, String stringURI, String value,  String uriNameSpace, String tagName){
        Property internalProperty = this.model.createProperty(stringURI + tagName);
        resource.addProperty(internalProperty, value);
    }
   
   
    private void processRDF_old(){
       
        List<Pesquisador> list = cv.getPesquisador();
        Pesquisador pesquisador = null;
        
        if (list == null){
            return;
        }
        
        Resource lattes;
        this.model = ModelFactory.createDefaultModel();
        String resourceURI;
        for (int i = 0; i < list.size(); i++){
            
            pesquisador = list.get(i);
            
            if (pesquisador != null) {
                
                resourceURI  = this.resourcesMap.getResourceURINoHash(RDFResourcesMap.LATTES_URI) + "/" + pesquisador.getId();
                lattes = this.model.createResource(resourceURI);
                this.createDadosPessoais(lattes, pesquisador);
                this.createEndereco(lattes, pesquisador);
                this.createProducaoCientificaArtigosPeriodico(lattes, pesquisador);
                this.createIdiomas(lattes, pesquisador);
                this.createFormacaoAcademica(lattes, pesquisador);
                this.createProjetosPesquisa(lattes, pesquisador);
                this.createColaboradores(lattes, pesquisador);
            }
        }
    }
    */
    
    public void RDF_toTerminalTripleForm(){
        this.RDF_toTerminalF("N-TRIPLE");
    }
    
    public void RDF_toTerminalAbrev(){
        this.RDF_toTerminalF("RDF/XML-ABBREV");
    }
    
    public void RDF_toTerminal(){
        this.RDF_toTerminalF(null);
    }
    
    private void RDF_toTerminalF(String type){
        
        List<Pesquisador> list = cv.getPesquisador();
        Pesquisador pesquisador = null;
        
        if (list == null){
            return;
        }
        
        for (int i = 0; i < list.size(); i++){
            pesquisador = list.get(i);
            String s = pesquisador.getIdentificacao().getNomeCompleto();
            System.out.println("Pesquisador: " + s);
            if ( s != null && s.trim().equals("Ivan Torres Pisa"))
            this.processRDF(pesquisador);
            else
                continue;

            if (type != null){
                this.model.write(System.out, type);
                continue;
            }
            
            this.model.write(System.out);
            
        }
    }
    
    public void RDF_toFile() throws IOException{
        this.model.write(new FileWriter(new File(this.filePath)));
    }
    
}

