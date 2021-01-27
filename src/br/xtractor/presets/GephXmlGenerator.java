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

import br.xtractor.grapho.Edge;
import br.xtractor.grapho.Graph;
import br.xtractor.grapho.Node;
import br.xtractor.helper.XtractorHelper;
import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.vo.Pesquisador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author richardv
 */
public class GephXmlGenerator {

    
    public void createXml(CurriculoLattes cv, String path, boolean abbreviateName) {
        
        Graph grapho    = null;
        Node nodeSource = null;
        Node nodeColaborador = null;
        Edge edge       = null;
        List <String> colaboradores = null;
        
       
            grapho = new Graph();

            String totalRelacionamentosPesquisador = "";
            for (int i = 0; i < cv.getPesquisador().size(); i++){
                Pesquisador p = cv.getPesquisador().get(i);
                nodeSource = new Node();
                
                //######################################
                // Codigo Lattes
                nodeSource.setId(p.getId());
                String nomePesquisador = GephXmlGenerator.abbreviate(
                        p.getIdentificacao().getNomeInicial()
                        , abbreviateName
                );

                nodeSource.setLabel(nomePesquisador);
                grapho.addNode(nodeSource);
                totalRelacionamentosPesquisador += nomePesquisador + "\n";
                int calculaRelacionamento = 0;
                
                // Recupera as relacoes
                if (p.getColaboradores() != null) {
                    colaboradores = p.getColaboradores().getColaboradores();
                    for (int k = 0; k < colaboradores.size(); k++){

                        nodeColaborador = new Node();
                        nodeColaborador.setLabel("");
                        nodeColaborador.setId(colaboradores.get(k));
                        
                        //##################################################################
                        //Verifica se o pesquisador esta na lista de curriculuns recuperados
                        for (int y = 0 ; y < cv.getPesquisador().size(); y++){ 
                            if (cv.getPesquisador().get(y).getId().equals(nodeColaborador.getId())){
                                
                                 String nomeColaborador = GephXmlGenerator.abbreviate(
                                         cv.getPesquisador().get(y).getIdentificacao().getNomeInicial()
                                         ,abbreviateName
                                 );
                                 
                                nodeColaborador.setLabel(nomeColaborador);
                                edge = new Edge(nodeSource, nodeColaborador);
                                grapho.addEdge(edge);
                                grapho.addNode(nodeColaborador);
                                calculaRelacionamento ++;
                                break;
                            }
                        }
                    }
                }
                
                //System.out.println(totalRelacionamentosPesquisador + "; " + calculaRelacionamento);
                calculaRelacionamento--;
                calculaRelacionamento = calculaRelacionamento <= 0 ? 0 : calculaRelacionamento;
                System.out.println(calculaRelacionamento);
                
            }
            
            System.out.println(totalRelacionamentosPesquisador);
            grapho.generateGrauRelacionamento(path);
            grapho.generateGephiGraphXml(path);
    }
    
    private static String abbreviate(String name, boolean abbreviateName){
        if (!abbreviateName) {
            name = XtractorHelper.firstLetterName(name);
        }
        return name;
    }
}
