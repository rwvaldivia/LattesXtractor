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

import java.io.Serializable;

/**
 *
 * @author richardv
 */
public class Edge implements Serializable {
    private static long edgeCounter;
    private String id;
    private String description;
    private Node source;
    private Node target;
    private float weigth;
    
    public Edge(){}
    
    public Edge(Node source, Node target){
        Edge.edgeCounter ++;
        this.id = "" + Edge.edgeCounter;
        this.source = source;
        this.target = target;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public float getWeigth() {
        return weigth;
    }

    public void setWeigth(float weigth) {
        this.weigth = weigth;
        
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == null){
            return false;
        }
        
        if (!(obj instanceof Edge)){
            throw new IllegalArgumentException("Incompatible Class/Classe Incompativel");
        }
        
        Edge objEdge = (Edge)obj;
        
        
        if (    
                (objEdge.getSource().equals(this.getSource()) && 
                objEdge.getTarget().equals(this.getTarget()) )
                
                /*
                Nao deixa duplicar a resta de V1 --> V2 se V2 --> V1
                ||
                ( objEdge.getSource().equals(this.getTarget()) && 
                objEdge.getTarget().equals(this.getSource()))
                */                
                ){
           return true;
        }
        
        return false;
    }

    
}
