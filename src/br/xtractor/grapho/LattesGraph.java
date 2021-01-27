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
package br.xtractor.grapho;

import br.xtractor.lattes.app.LattesXtractor;
import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.vo.Pesquisador;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author richardv
 */
public class LattesGraph {
    
    public void generateXmlGephiGraph(){
        
        LattesXtractor extractor = new LattesXtractor();
        Graph grapho = null;
        Node nodeSource = null;
        Node nodeTarget = null;
        Edge edge = null;
        List <String> colaboradores = null;
        
        try {
            CurriculoLattes cv = extractor.extractCurriculoLattes("c:\\java\\script_lattes.xml");
            grapho = new Graph();
            
            /*
             * Recupera os nos e arestas
             */
            for (int i = 0; i < cv.getPesquisador().size(); i++){
                Pesquisador p = cv.getPesquisador().get(i);
                //System.out.println("Nome: " + p.getIdentificacao().getNomeCompleto());
                nodeSource = new Node();
                
                // Codigo Lattes
                nodeSource.setId(p.getId());
                nodeSource.setLabel(p.getIdentificacao().getNomeCompleto());
                grapho.addNode(nodeSource);
                
                // Recupera as relacoes
                if (p.getColaboradores() != null) {
                    colaboradores = p.getColaboradores().getColaboradores();
                    for (int k = 0; k < colaboradores.size(); k++){

                        nodeTarget = new Node();
                        nodeTarget.setLabel("");
                        nodeTarget.setId(colaboradores.get(k));
                        
                        //##################################################################
                        //Verifica se o pesquisador esta na lista de curriculuns recuperados
                        for (int y = 0 ; y < cv.getPesquisador().size(); y++){
                            if (cv.getPesquisador().get(y).getId().equals(nodeTarget.getId())){
                                nodeTarget.setLabel(cv.getPesquisador().get(y).getIdentificacao().getNomeCompleto());
                                //System.out.println("                " +nodeTarget.getId() + " : " + nodeTarget.getLabel() );
                                edge = new Edge(nodeSource, nodeTarget);
                                grapho.addEdge(edge);
                                grapho.addNode(nodeTarget);
                                break;
                            }
                        }
                        

                    }
                } else {
                    grapho.addNode(nodeSource);
                }
            }
            
            grapho.generateGephiGraphXml("c:\\java\\lattes_gephi.gefx");
            
        } catch (IOException io){
            io.printStackTrace();
        }
    
    }
}
