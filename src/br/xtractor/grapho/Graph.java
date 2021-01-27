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

import br.xtractor.helper.XtractorHelper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author richardv
 */
public class Graph {
    
    public static final char DIRECT_GRAPH = '1';
    public static final char UNDIRECT_GRAPH = '2';
    
    private List<Edge> edges = new ArrayList<>();
    private List<Node> nodes = new ArrayList<>();
    private char graphType;
    
    public Graph(){
        this. graphType = Graph.UNDIRECT_GRAPH;
    }
    
    public Graph(char graphType) {
        
        switch(graphType){
            case Graph.DIRECT_GRAPH:
            case Graph.UNDIRECT_GRAPH:
                break;
            default:
                graphType = Graph.UNDIRECT_GRAPH;
        }
        
        this.graphType = graphType;
    
    }
    
    public void addNode(Node node){
        
        if (node == null || XtractorHelper.isEmptyOrNull(node.getId())) {
            return;
        }
        
        boolean contains    = false;
        String noAtualId    = "";
        String nodeId       = node.getId().trim();;
        for (int i = 0; i < this.nodes.size(); i++){
            Node noAtual    = this.nodes.get(i);
            noAtualId       = noAtual.getId().trim();
            
            if (noAtualId.equals(nodeId)){
                contains = true;
                break;
            }
        }
        
        if (!contains) {
            this.nodes.add(node);
        }
        
    }
    
    public void addEdge(Edge edge){
        if (edge == null){
            return;
        }
        if (!this.edges.contains(edge)){
            this.edges.add(edge);
        } 
    }
    
    public void generateGrauRelacionamento(String filepath){
        int grade;
        StringBuilder builder = new StringBuilder();
        builder.append("Pesquisador").append(";").append("Grau de relacionamento").append("\n");
        
        
        /*
        Remove as duplicidades se houver
        */
        
        
        List<Node> listNodes = new ArrayList<>();
        int count = 0;
        for (Node node: this.nodes){
            if (!listNodes.contains(node)){
                listNodes.add(node);
            } else {
                count++;
            }
        }
        
        //XtractorHelper.debug("Duplicidades: " + this.getClass().getName()  + " - "+  count, 10);
        for ( Node node: listNodes){
            
            grade = 0;
            
            for( Edge edge: this.edges){
                if (edge.getSource().getId().equals(node.getId())){
                    grade++;
                }   
            }
            grade--;
            if (grade < 0){
                grade = 0;
            }
            builder.append(node.getLabel()).append(";").append(grade).append("\n");
            
        }
        
        FileWriter writer = null;
        
        try {
            filepath = this.removeExtension(filepath);
            writer = new FileWriter(new File(filepath + "-gr.csv"));
            writer.write(builder.toString());
            writer.close();
        } catch (IOException io){
            io.printStackTrace();
        }
        
    }
    
    private String removeExtension(String str){
        
        if (str == null){
            return "";
        }
        
        int pos = str.lastIndexOf(".");;
        
        if (pos >= 0){
            str = str.substring(0, (pos));
        }
        
        return str;
    }
    
    public void generateGephiGraphXml(String filepath){
        
        Calendar cal = Calendar.getInstance();
        
        
        String date =   cal.get(Calendar.YEAR) + 
                        "-" + (cal.get(Calendar.MONTH) + 1) + 
                        "-" + cal.get(Calendar.DATE) +
                        "+" + cal.get(Calendar.HOUR_OF_DAY) +
                        ":" + cal.get(Calendar.MINUTE);
        
        
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        buffer.append("<gexf xmlns:viz=\"http:///www.gexf.net/1.1draft/viz\" version=\"1.1\" xmlns=\"http://www.gexf.net/1.1draft\">\n");
        //buffer.append("    <meta lastmodifieddate=\"2010-03-03+23:44\">\n");
        buffer.append("    <meta lastmodifieddate=\"").append(date).append("\">\n");
        buffer.append("        <creator>Gephi 0.7</creator>\n");
        buffer.append("    </meta>\n");
        buffer.append("    <graph defaultedgetype=\"undirected\" idtype=\"string\" type=\"static\">\n");
        
        buffer.append("         <nodes count=\"").append(this.nodes.size()).append("\">\n");
        for ( int i = 0; i < this.nodes.size(); i++) {
            buffer.append("             <node id=\"").append(this.nodes.get(i).getId()).append("\" label=\"").append(this.nodes.get(i).getLabel()).append("\"/>\n");
        }
        buffer.append("         </nodes>\n");
        
        
        buffer.append("         <edges count=\"").append(this.edges.size()).append("\">\n");
        String idTarget = null;
        String idSource = null;
        for ( int i = 0; i < this.edges.size(); i++) {
            
            idTarget = this.edges.get(i).getTarget().getId();
            idSource = this.edges.get(i).getSource().getId();
            if (Graph.isValidEdge(idSource, idTarget)) {
                buffer.append("             <edge id=\"").append(this.edges.get(i).getId()).append("\" source=\"").append(idSource).append("\" target=\"").append(idTarget).append("\" />\n");
            }
        }
        buffer.append("         </edges>\n");
        buffer.append("    </graph>\n");
        buffer.append("</gexf>\n");
        
        FileWriter writer = null;
        
        try {
            //filepath = "c:\\Java\\lattes.gexf"
            writer = new FileWriter(new File(filepath));
            writer.write(buffer.toString());
            writer.close();
        } catch (IOException io){
            io.printStackTrace();
        }
    }
    
    public static boolean isValidEdge(String source, String target) {
        if (XtractorHelper.isEmptyOrNull(source) 
                || XtractorHelper.isEmptyOrNull(target) 
                || source.equals(target)) {
            return false;
        }
        
        return true;
    }
    
    
    
}
