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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author gustavov
 */
public class RDFResourcesMap {
    
    private HashMap<String, String[]> resources;
    private static RDFResourcesMap res = null;
    
    public static String RESOURCE_VCARD                         = "A";
    public static String RESOURCE_FOAF                          = "B";
    public static String RESOURCE_BIBLIOGRAFICAL                = "C";
    public static String LATTES_URI                             = "D";
    public static String RESOURCE_EVENT_MUSIC_ARTS              = "E";
    public static String RESOURCE_BIOGRAPHICAL                  = "F";
    public static String RESOURCE_DUBLIN_CORE_ELEMENTS          = "G";
    public static String RESOURCE_DUBLIN_CORE_TERMS             = "H";
    public static String RESOURCE_GEONAMES                      = "I";
    public static String RESOURCE_ACADEMIC                      = "J";
    public static String RESOURCE_TIME                          = "K";
    public static String RESOURCE_SPATIAL_THINGS                = "L";
    public static String RESOURCE_PUBLISHING                    = "M";
    public static String RESOURCE_ADDRESS_TALIS                 = "N";
    public static String RESOURCE_DBPEDIA_ONTOLOGY              = "O";
    public static String RESOURCE_LATTES_ONTOLOGY              = "P";
    
    private RDFResourcesMap(){
        
        String s[] = new String [] {"", ""};
        
        this.resources = new HashMap<>();
        this.resources.put(RDFResourcesMap.LATTES_URI,                  new String [] {
            "http://lattes.cnpq.br#", 
            "lattes", 
            "URI LATTES"});
        this.resources.put(RDFResourcesMap.RESOURCE_VCARD,              new String [] {
            "http://www.w3.org/2001/vcard-rdf/3.0#", 
            "vcard", 
            ""});
        this.resources.put(RDFResourcesMap.RESOURCE_FOAF,               new String [] {
            "http://xmlns.com/foaf/0.1#", 
            "foaf", 
            ""});
        this.resources.put(RDFResourcesMap.RESOURCE_BIBLIOGRAFICAL,     new String [] {
            //"http://purl.org/ontology/bibo#", 
            "http://bibotools.googlecode.com/svn/bibo-ontology/trunk/doc#", 
            "bibo", 
            ""});
        this.resources.put(RDFResourcesMap.RESOURCE_EVENT_MUSIC_ARTS,   new String [] {
            "http://purl.org/NET/c4dm/event.owl#", 
            "event", 
            ""});
        this.resources.put(RDFResourcesMap.RESOURCE_BIOGRAPHICAL,       new String [] {
            "http://purl.org/vocab/bio/0.1#", 
            "bio", 
            "A vocabulary for biographical information"});
        this.resources.put(RDFResourcesMap.RESOURCE_GEONAMES,         new String [] {
            "http://www.geonames.org/ontology#",
            "geo", 
            ""});
        this.resources.put(RDFResourcesMap.RESOURCE_ACADEMIC,           new String [] {
            "http://vocab.org/aiiso/schema#",
            "aiiso",
            "Academic Institution Internal Structure Ontology"});
        this.resources.put(RDFResourcesMap.RESOURCE_TIME,               new String [] {
            "http://www.w3.org/TR/owl-time#",
            "owltime",
            ""});
        this.resources.put(RDFResourcesMap.RESOURCE_SPATIAL_THINGS,     new String [] {
            "http://www.w3.org/2003/01/geo/wgs84_pos#", 
            "wgs84_pos",                                                                           
            "Anything with spatial extent, i.e. size, shape, or position.e.g. people, places, bowling balls, as well as abstract areas like cubes"});
        
        this.resources.put(RDFResourcesMap.RESOURCE_DUBLIN_CORE_ELEMENTS,     new String [] {
            "http://purl.org/dc/elements/1.1#",
            "dc",
            "The Dublin Core Metadata Initiative provides core metadata vocabularies in support of interoperable solutions for discovering and managing resources"});
        
        this.resources.put(RDFResourcesMap.RESOURCE_DUBLIN_CORE_TERMS,     new String [] {
            "http://purl.org/dc/terms#",
            "dcterms",
            "The Dublin Core Metadata Initiative provides core metadata vocabularies in support of interoperable solutions for discovering and managing resources"});
        
        
        this.resources.put(RDFResourcesMap.RESOURCE_PUBLISHING,     new String [] {
            "http://purl.org/dc/terms#",
            "prism",
            "PRISM - Publishing Requirements for Industry Standard Metadata PRISM Specifications are developed by the PRISM Working Group as part of the IDEAlliance PRISM Metadata Activity"});
        
        
        this.resources.put(RDFResourcesMap.RESOURCE_ADDRESS_TALIS,     new String [] {
            "http://schemas.talis.com/2005/address/schema#",
            "address",
            "Resource for address"});
        
        this.resources.put(RDFResourcesMap.RESOURCE_DBPEDIA_ONTOLOGY,     new String [] {
            "http://dbpedia.org/ontology#",
            //"http://dbpedia.org/",
            "dbpedia-owl",
            "DBPedia ontoloty - URL: http://wiki.dbpedia.org/Ontology?v=194q"});
        
        this.resources.put(RDFResourcesMap.RESOURCE_LATTES_ONTOLOGY,     new String [] {
            "http://sourceforge.latesxtractor.net/ontology#",
            "lattes-owl",
            "Ontologia para Plataforma Lattes"});
        
        
    }
    
    public String getResourceNameSpacePrefix(String key) {
        this.resources.keySet();
        return this.resources.get(key)[1];
    }
    
    public String getResourceDescription(String key){
        return this.resources.get(key)[2];
    }
    
    public String getResourceURI(String key){
        return this.resources.get(key)[0];
    }
    
    public String getResourceURINoHash(String key){
        String s = this.getResourceURI(key);
        
        if (s == null || "".equals(s) || s.length() == 0){
            if (s != null){
                return s.trim();
            }
            return s;
        }
        
        s = s.substring(0, s.length() - 1);
        
        return s;
    }
    
    public static RDFResourcesMap getSingletonInstance(){
        
        if (RDFResourcesMap.res == null) {
            RDFResourcesMap.res = new RDFResourcesMap();
        }
        
        return RDFResourcesMap.res;
    }
    
    @Override
    public String toString(){
        Set set = this.resources.keySet();
        Iterator it = set.iterator();
        StringBuilder builder = new StringBuilder();
        
        while(it.hasNext()){
            String key = (String)it.next();
            String [] values = this.resources.get(key);
            builder.append("URI: [").append(values[0]).append("]\nnamespace: [").append(values[1]).append("]\nDescricao: [").append(values[2]).append("]\n\n");
        }
        
        return builder.toString();
    }
}
