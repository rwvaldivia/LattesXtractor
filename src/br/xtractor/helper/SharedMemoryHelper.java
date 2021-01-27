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

package br.xtractor.helper;
import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.vo.Pesquisador;
import java.util.List;

/**
 *
 * @author richard.valdivia
 */
public final class SharedMemoryHelper {
    
    
    private static CurriculoLattes curriculoLattes;
    
    public SharedMemoryHelper(){
        SharedMemoryHelper.resetSharedMemory();

    }
    
    public static void resetSharedMemory(){
        SharedMemoryHelper.curriculoLattes = null;
    }

    public static CurriculoLattes getCurriculoLattes() {
        return curriculoLattes;
    }

    public static void setCurriculoLattes(CurriculoLattes curriculoLattes) {
        SharedMemoryHelper.curriculoLattes = curriculoLattes;
    }
    
}
