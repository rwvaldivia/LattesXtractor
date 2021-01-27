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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author richardv
 */
public final class XtractorHelper {
    
    private static File fileToLog;
    private static String internalFileName;
    public static final int FORMAT_DATE_DDMMYYY_ABREV = 1;
    
    private XtractorHelper(){
        //Nao permite instanciar a classe
    }
    
    public static String escapeXmlChars(String s){
        return StringEscapeUtils.escapeXml10(s);
    }
    
    public static String nullToEmpty(String s){
        return (s == null) ? "" : s;
    }
    
    public static void log(String stringToLog){
        XtractorHelper.debug(stringToLog);
    }
    
    public static void log(String stringToLog, String fileName){
        XtractorHelper.internalFileName = fileName;
        
        if (stringToLog == null){
            stringToLog = "NULL STRING RECEIVED";
        }
        
        if (fileName != null){
            //########################################
            //Grava no Disco
            fileToLog = new File(XtractorHelper.internalFileName);
        } else {
            XtractorHelper.debug(stringToLog);
        }
    }
    
    public static void debug(String stringToDebug){
        XtractorHelper.debug(stringToDebug, "");
    }
    
    public static void debug(String stringToDebug, int repetition){
        XtractorHelper.debug(stringToDebug, "", repetition);
    }
    
    public static void debug(String stringToDebug, String tagDebug){
        XtractorHelper.debug(stringToDebug, tagDebug, -1);
    }
    
    public static void debug(String stringToDebug, String tagDebug, int repetition){
        
        repetition = repetition <= 0? 1: repetition;
        if (stringToDebug == null) stringToDebug = "null value";
        if (tagDebug == null) tagDebug = "LABLE:";
        tagDebug = tagDebug + ": ";
        
        
        for (int i = 0; i < repetition; i++){
            System.out.println(tagDebug + "[" + stringToDebug + "]");
        }
    }
    
    
    public static void printArray(String [] arr1, String [] arr2){
        
        
        if (arr1 == null || arr2 == null){
            if (arr1 == null){
                XtractorHelper.debug("Array 1 null\n", 10);
            }
            
            if (arr2 == null){
                XtractorHelper.debug("Array 1 null\n", 10);
            }
            
            return;
        }
        
        if (arr1.length != arr2.length){
            XtractorHelper.debug("tamanho dos arrays diferentes Arr1: [" + arr1.length + "] Arr2: [" + arr2.length + "]\n", 10);
        }
        
        for (int i=0; i < arr1.length; i++){
            XtractorHelper.debug("[" + arr1[i] + "] -> [" + arr2[i] + "]");
        }
        
    }
    
    
    public static String formatDateDDMMYYY(String data, int type){
        
        if(data == null) {
            return "";
        }
        String [] temp = data.split(" ");
        
        if (temp.length < 3){
            return "";
        }
        
        String dateFormated = "";
        
        switch(type) {
            
            case XtractorHelper.FORMAT_DATE_DDMMYYY_ABREV:
                String month = temp[1].toLowerCase().trim();
                if (month.startsWith("jan")){
                    month = "01";
                } else if (month.startsWith("fe")){
                    month = "02";
                }else if (month.startsWith("mar")){
                    month = "03";
                }else if (month.startsWith("ab")){
                    month = "04";
                }else if (month.startsWith("mai")){
                    month = "05";
                }else if (month.startsWith("jun")){
                    month = "06";
                }else if (month.startsWith("jul")){
                    month = "07";
                }else if (month.startsWith("ago")){
                    month = "08";
                }else if (month.startsWith("set")){
                    month = "09";
                }else if (month.startsWith("out")){
                    month = "10";
                }else if (month.startsWith("nov")){
                    month = "11";
                }else if (month.startsWith("dez")){
                    month = "12";
                } else {
                    return "";
                }
                
                dateFormated = temp[0] + "/" + month + "/" + temp[2];
            default:
                dateFormated = data;
        }
        
        return dateFormated;
    }
    
    
    public static void writeDataToDisk(String value, String filename){
        
        FileWriter writer = null;
        
        try {
            writer = new FileWriter(new File(filename));
            writer.write(value);
        } catch(IOException io){
        
        } finally {
            CloseFileHelper.close(writer);
        }
        
        
    }
    
    public static void writeDataToDisk(StringBuilder value, String filename){
        XtractorHelper.writeDataToDisk(value.toString(), filename);
    }
    
    public static String firstLetterName(String name){
        return XtractorHelper.firstLetterName(name, true);
    }
    
    public static String firstLetterName(String name, boolean isDotted){
        String [] tempArr = name.split(" ");
        String result = "";
        String separator;
        
        if (isDotted){
            separator = ".";
        } else {
            separator = " ";
        }
        String strTemp;
        
        for(int j = 0; j < tempArr.length; j++){
            strTemp = tempArr[j];
            if (strTemp != null){
                strTemp = strTemp.trim();
                if (!strTemp.equals("")) {
                    result += strTemp.charAt(0) + separator;
                }
            }
            
        }
        return result.toUpperCase();
    }
    
    public static boolean isEmptyOrNull(String str){
        
        if (str == null || str.trim().equals("")){
            return true;
        }
        
        return false;
    }
    
}
