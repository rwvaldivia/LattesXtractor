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

import br.xtractor.helper.XtractorHelper;
import br.xtractor.lattes.core.Colaboradores;
import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.core.FormacaoAcademica;
import br.xtractor.lattes.vo.ArtigoRevistaVO;
import br.xtractor.lattes.vo.ArtigoVO;
import br.xtractor.lattes.vo.FormacaoVO;
import br.xtractor.lattes.vo.Pesquisador;
import br.xtractor.presets.GephXmlGenerator;
import br.xtractor.presets.PrefuseTreeViewXMLCreator;
import core.TreeViewDisplay;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class LattesXtractor {

    public static void main(String[] args) {

        LattesXtractor extractor = new LattesXtractor();
        CurriculoLattes cv = null;
        String path = null;

        if (args.length > 1) {
            path = args[1];
        } else {
            path = "c:\\java\\Projetos_Mestrado\\Pesquisadores-database.xml";
            path = "D:\\files\\enfermagem.xml";
            //extractor.usage();
            //return;
        }

        try {
            cv = extractor.extractCurriculoLattes(path);
            //extractor.printDOIPublicacao(cv);
            
            //extractor.printTotalProducaoPeriodico(cv);
            //extractor.displayLattesGraph(cv);
            extractor.generateGephXml(cv, "d:\\files\\enfermagem.gexf", false);
            //extractor.displayCurriculoData(cv);
            //cv = extractor.extractCurriculoLattesFromCNPqXML("c:\\java\\curriculo.xml");



//            RDFLattesGenerator generator = new RDFLattesGenerator(cv, path);
//            generator.RDF_toTerminalAbrev();
//            generator.RDF_toTerminalTripleForm();

            //RDFResourcesMap map = RDFResourcesMap.getSingletonInstance();
            //System.out.println(map.toString());

            //generator.RDF_toTerminal();
            //System.out.println(RDFResourcesMap.getSingletonInstance().toString());

            //LattesStractorMainGUI gui = new LattesStractorMainGUI(cv);


        } catch (Exception io) {
            io.printStackTrace();
        }
    }
    
    private void printTotalProducaoPeriodico(CurriculoLattes cv){
        
        List <Pesquisador> pesquisadorList = cv.getPesquisador();
        Pesquisador pesquisador = null;
        if (pesquisadorList != null && !pesquisadorList.isEmpty()){
            for( int i = 0 ; i < pesquisadorList.size(); i++){
                
                pesquisador = pesquisadorList.get(i);
                if (pesquisador != null && pesquisador.getArtigosEmPeriodicosVO() != null) {
                    if (pesquisador.getArtigosEmPeriodicosVO().length != 0) {
                        System.out.println(pesquisador.getArtigosEmPeriodicosVO().length + ": " + pesquisador.getIdentificacao().getNomeCompleto() + ": ");
                    }
                } else {
                    System.out.print("0: ");
                    if (pesquisador != null) {
                        System.out.println(pesquisador.getIdentificacao().getNomeCompleto());
                    } else {
                        System.out.println("SEM NOME ");
                    }
                }
            }
        }
    }
    
    private void printDOIPublicacao(CurriculoLattes cv) {
        
        List<Pesquisador> list = cv.getPesquisador();
        Pesquisador pesquisador = null;

        if (list == null) {
            return;
        }
        
        ArtigoVO[] artigoVO             = null;
        ArtigoRevistaVO[] artigoRevista = null;
        String doi                      = null;
        String titulo                   = null;
        String ano                      = null;
        String nome                     = null;
        String idLattes                 = null;
        
        for (int i = 0; i < list.size(); i++) {
            
            pesquisador = list.get(i);
            nome = pesquisador.getIdentificacao().getNomeCompleto();
            idLattes = pesquisador.getId();
            artigoVO = pesquisador.getArtigosEmPeriodicosVO();
            if (artigoVO != null) {
                
                for (int j = 0; j < artigoVO.length; j++) {
                    doi     = XtractorHelper.nullToEmpty(artigoVO[j].getDoi());
                    titulo  = XtractorHelper.nullToEmpty(artigoVO[j].getTitulo());
                    ano     = XtractorHelper.nullToEmpty(artigoVO[j].getAno());

                    this.printDOIPublicacao(nome, idLattes, doi, titulo, ano);
                }
            }
            
            artigoRevista = pesquisador.getArtigosEmRevistaVO();
            if (artigoRevista != null) {
                for (int j = 0; j < artigoRevista.length; j++) {
                    if (artigoRevista[j] != null) {  
                        doi     = XtractorHelper.nullToEmpty(artigoRevista[j].getDoi());
                        titulo  = XtractorHelper.nullToEmpty(artigoRevista[j].getTitulo());
                        ano     = XtractorHelper.nullToEmpty(artigoRevista[j].getAno());
                        this.printDOIPublicacao(nome, idLattes, doi, titulo, ano);
                    }
                }
            }
        }
    }

    private void printDOIPublicacao(String nome, String idLAttes, String doi, String titulo, String ano) {
        if ("".equals(doi)) {
            return;
        }
        
        String separator = "*-*-*";

        System.out.println("" + doi + separator + titulo + separator + ano + separator + nome + separator + idLAttes);
    }

    private void usage() {
        System.out.println("USAGE: \nExemplo de execução: java -jar LattesXtractor <script_lattes_file_name>");
    }

    /**
     * Gera um xml para a ferramenta Gephi<br /> Maiores detalhes sobre o
     * algorimo veja em : http://lattesxtractor.sourceforge.net/
     *
     * @param cv
     * @param filePath
     */
    public void generateGephXml(CurriculoLattes cv, String filePath, boolean isNomeCompleto) {
        GephXmlGenerator g = new GephXmlGenerator();
        g.createXml(cv, filePath, isNomeCompleto);
    }

    /**
     * Cria o uma JFrame com um grafo representando o xml do script_lattes.xml
     *
     * @param cv
     * @throws IOException
     */
    public void displayLattesGraph(CurriculoLattes cv) throws IOException {
        PrefuseTreeViewXMLCreator preset = new PrefuseTreeViewXMLCreator();
        String xml = preset.createXml(cv);
        TreeViewDisplay.showTreeDisplay(xml);
    }

    /**
     *
     * @param filePath caminho do arquivo script_lattes.xml
     * @return Retorna um objeto CurriculoLattes com a lista de todos os
     * pesquisadores
     * @throws IOException Observacao: Será lançado IllegalArgumentException se
     * filePath for uma String vazia (empty) ou Null
     */
    public CurriculoLattes extractCurriculoLattes(String filePath) throws IOException {
        ScriptLattesXmlParser xml = new ScriptLattesXmlParser(filePath);
        CurriculoLattes cvlattes = xml.parse();
        return cvlattes;
    }

    public CurriculoLattes extractCurriculoLattesFromCNPqXML(String path) throws ParserConfigurationException, SAXException, IOException {

        FileReader xmlfile = new FileReader(path);
        BufferedReader buffer = new BufferedReader(xmlfile);
        String xmlPartStr = null;
        StringBuilder builder = new StringBuilder();

        while ((xmlPartStr = buffer.readLine()) != null) {
            builder.append(xmlPartStr);
        }

        CNPqXmlHandler handler = new CNPqXmlHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory1.newDocumentBuilder();
        InputSource inStream = new InputSource();

        inStream.setCharacterStream(new StringReader(builder.toString()));
        saxParser.parse(inStream, handler);

        CurriculoLattes lattes = handler.getCurriculoLattes();
        return lattes;

    }

    /**
     * <br /><b>Imprime um teste com as seguintes informações<b>: <br /><br />
     *
     * - Nome do Pesquisador<br /> - ID Lattes do Pesquisador<br /> - Formação
     * do pesquisador<br /> - ID Lattes de Colaboradores com esse pesquisador<br
     * /><br />
     *
     * @param lattes
     *
     */
    private void displayCurriculoData(CurriculoLattes lattes) {

        int counterTemp = 0;
        String s = null;
        StringBuffer buffer = new StringBuffer();
        List pesquisadores = lattes.getPesquisador();

        for (int i = 0; i < pesquisadores.size(); i++) {
            Pesquisador p = (Pesquisador) pesquisadores.get(i);
            s = p.getIdentificacao().getNomeCompleto();
            this.debug("Registro No. (" + (i + 1) + ") - " + s);
            this.debug("Id Lates do Pesquisador: " + p.getId());


            FormacaoAcademica f = p.getFormacaoAcademica();

            if (f != null) {
                for (int k = 0; k < f.getFormacao().size(); k++) {
                    FormacaoVO fvo = f.getFormacao().get(k);
                    buffer = new StringBuffer();
                    buffer.append("\n        Titulacao: ").append(fvo.getTipo());
                    buffer.append("          Ano Inicio: ").append(fvo.getAnoInicio());
                    buffer.append("\n        Ano Fim: ").append(fvo.getAnoConclusao());
                    buffer.append("\n        Instituicao: ").append(fvo.getNomeInstituicao());
                    buffer.append("\n\n");

                    debug(buffer.toString());
                }

            }
            Colaboradores colab = p.getColaboradores();
            if (colab != null) {
                counterTemp = 0;
                String idLattesColaborador;
                for (int k = 0; k < colab.getColaboradores().size(); k++) {
                    idLattesColaborador = colab.getColaboradores().get(k);
                    debug("            idLattes de Colaborador: " + idLattesColaborador);
                    counterTemp++;
                }
                debug("            Total Colaboradores: " + counterTemp);
            }


        }

    }

    private void debug(String s) {
        if (s == null) {
            s = "Warning: null value!!!!";
        }
        //System.out.println("DEBUG [" + s + "]");
        System.out.println("" + s + "");
    }
}
