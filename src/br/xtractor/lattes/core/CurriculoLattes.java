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
package br.xtractor.lattes.core;

import br.xtractor.lattes.vo.Pesquisador;
import java.util.ArrayList;
import java.util.List;

public class CurriculoLattes {
    
    private String data_processamento;
    
    private List <Pesquisador>pesquisador; 

    public String getData_processamento() {
        return data_processamento;
    }

    public void setData_processamento(String data_processamento) {
        this.data_processamento = data_processamento;
    }
    
    public List<Pesquisador> getPesquisador() {
        return pesquisador;
    }

    public void setPesquisador(List<Pesquisador> pesquisador) {
        this.pesquisador = pesquisador;
    }
    
    public void addPesquisador(Pesquisador p){
        if (this.pesquisador == null){
            this.pesquisador = new ArrayList<>();
        }
        this.pesquisador.add(p);
    }
    
    public int size() {
        if (pesquisador != null){
            return pesquisador.size();
        }
        return 0;
    }
    
}
