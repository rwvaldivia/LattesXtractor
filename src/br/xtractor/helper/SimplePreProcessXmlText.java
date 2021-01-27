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

import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author richard.valdivia
 */
public class SimplePreProcessXmlText {
    public static final char XML_SCAPE11 = '1';
    public static final char XML_SCAPE10 = '0';
    private char typeScape;
    
    public SimplePreProcessXmlText(){
        this.typeScape = SimplePreProcessXmlText.XML_SCAPE10;
    }
    
    public SimplePreProcessXmlText(char typeScape){
        switch(typeScape){
            case '1':
                this.typeScape = SimplePreProcessXmlText.XML_SCAPE11;
                break;
            default:
                this.typeScape = SimplePreProcessXmlText.XML_SCAPE10;
        }
    }
    
    public String preProcessText(String xmlLine){
        
        String initTag = "";
        String finalTag = "";
        int inipos;
        int fimpos;
        
        if (xmlLine == null || xmlLine.trim().equals("")){
           return "";
        }
        
        xmlLine = xmlLine.trim();
        //##############################################
        //Recupera a Tag inicial
        inipos = xmlLine.indexOf('<');
        fimpos = xmlLine.indexOf('>');
        
        if (inipos != -1 && fimpos != -1){
            initTag = xmlLine.substring(inipos, fimpos + 1);
        }
        
        //##############################################
        //Prepara text removendo a tagInicial
        xmlLine = xmlLine.substring(fimpos + 1, xmlLine.length());
        //##############################################
        //Recupera a Tag final
        inipos = xmlLine.lastIndexOf("</");
        fimpos = xmlLine.lastIndexOf(">");
        if (inipos != -1 && fimpos != -1){
            finalTag = xmlLine.substring(inipos, fimpos + 1);
        }
        
        //##############################################
        //Recupera a Tag final
        if (inipos != -1 && fimpos != -1){
            xmlLine = xmlLine.substring(0, inipos);
        }
        
        //##############################################
        //Trata a tag
        if (this.typeScape == SimplePreProcessXmlText.XML_SCAPE10){
            xmlLine = StringEscapeUtils.escapeXml10(xmlLine);
        } else {
            xmlLine = StringEscapeUtils.escapeXml11(xmlLine);
        }
        xmlLine = xmlLine.replaceAll("&", "&amp;");
        xmlLine = initTag + xmlLine + finalTag + "\n";
        
        return xmlLine;
    }
}
