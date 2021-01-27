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

import br.xtractor.helper.CloseFileHelper;
import br.xtractor.helper.Constants;
import br.xtractor.helper.XtractorHelper;
import br.xtractor.lattes.core.ArtigosEmPeriodicos;
import br.xtractor.lattes.core.CapitulosLivros;
import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.core.LivrosPublicados;
import br.xtractor.lattes.core.ResumoCongresso;
import br.xtractor.lattes.core.ResumoCongressoExpandido;
import br.xtractor.lattes.core.TextosEmJornal;
import br.xtractor.lattes.core.TrabalhoCompletoCongresso;
import br.xtractor.lattes.task.XtractorTask;
import br.xtractor.lattes.vo.ArtigoVO;
import br.xtractor.lattes.vo.CapitulosLivrosVO;
import br.xtractor.lattes.vo.LivroPublicadoVO;
import br.xtractor.lattes.vo.Pesquisador;
import br.xtractor.lattes.vo.ResumoCongressoExpandidoVO;
import br.xtractor.lattes.vo.ResumoCongressoVO;
import br.xtractor.lattes.vo.TextoJornalVO;
import br.xtractor.lattes.vo.TrabalhoCompletoVO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author richard.valdivia
 */
public class XtractAllCurriculum extends XtractorTask {
    
    public static void main(String... args) {
        new XtractAllCurriculum().executeTask();
    }
    
    @Override
    public void executeTask() {

        /*
        1 - Faz a leitura de todos os curriculuns
        Precisa da lista de orientadores e de IDLattes
         */
        String dataExtracao = "04/05/2017";
        String fileWithIdLattesAndUnifespID_ORI = "D:\\ScriptLattes - Extracao 05-2017\\_Arquivos_de_integracao\\ori_id-lattes16.txt";
        String basePathForXmls = "D:\\ScriptLattes - Extracao 05-2017\\UNIFESP_orientadores_porPrograma\\";
        String xmlFileName = "unifesp-orientadores-database.xml";

        //########################################################
        //# TESTES de desenvolvimento
        String basePathForXmlsDevTest = "D:\\ScriptLattes - Extracao 05-2017\\UNIFESP_orientadores_porPrograma_teste_dev\\";
        basePathForXmls = basePathForXmlsDevTest;
        //########################################################

        List<Pesquisador> p = this.readAllResearchAndPutId(fileWithIdLattesAndUnifespID_ORI, basePathForXmls, xmlFileName);

        /*
        2 - Remove as duplicidades
         */
        p = this.removeDuplicated(p);

        /*
        3 - Gera o formato de Saída dos dados
         */
        String pathForProcessResult = "D:\\ScriptLattes - Extracao 05-2017\\_Resultado_processamento\\";
        this.formatPublicacaoToDatabase(p, pathForProcessResult, dataExtracao);
        
    }
    
    public List<Pesquisador> readAllResearchAndPutId(String fileWithIdLattesAndUnifespID_ORI, String basePathForXmls, String xmlFileName) {

        //String fileNameIdOri_lattes16 = "C:\\Users\\richard.valdivia\\Desktop\\Trabalhos\\Integracao ID Orientador - Lattes\\ori_id-lattes16.txt";
        HashMap<String, String> hmap = this.createHashMapByFileSeparatedByPype(fileWithIdLattesAndUnifespID_ORI);

        //String basePath = "A:\\Aplic01\\div-divulg-gestao-info\\Levantamentos\\ScriptLattes - Resultado Execucao\\Extracao em 05-2017\\UNIFESP_orientadores_porPrograma\\";
        //String basePath = "C:\\Users\\richard.valdivia\\Desktop\\Trabalhos\\extracao_jr\\Extracao Setor WEB";
        //String fileName = "unifesp-orientadores-database.xml";
        File f = new File(basePathForXmls);
        String[] dirs = f.list();
        String xmlFileNamePath;
        List<Pesquisador> finalList = new ArrayList();
        String idLattes16 = "";
        int idOrientador = -1;
        ScriptLattesXmlParser parser;
        
        if (dirs != null) {
            FileWriter writer = null;
            
            for (int i = 0; i < dirs.length; i++) {
                
                xmlFileNamePath = basePathForXmls + "\\" + dirs[i] + "\\" + xmlFileName;
                
                try {
                    XtractorHelper.log("Lendo registros do diretório: [" + dirs[i] + "]");
                    parser = new ScriptLattesXmlParser(xmlFileNamePath);
                    CurriculoLattes cv = parser.parse();
                    List<Pesquisador> pList = cv.getPesquisador();
                    
                    for (Pesquisador pesquisador : pList) {
                        idLattes16 = pesquisador.getId();
                        try {
                            idOrientador = Integer.parseInt(hmap.get(idLattes16));
                            pesquisador.setInternalId(idOrientador);
                            //XtractorHelper.log("Setting: idLattes16: [" + idLattes16 + "] idOrientador: [" +  idOrientador + "]");
                        } catch (Exception e) {
                            XtractorHelper.log("Setting: idLattes16: [ERRO AO PROCESSAR ID DO ORIENTADOR] idLattes16: [" + idLattes16 + "] --> idOrientador + [" + idOrientador + "] - Mensagem: [" + e.getMessage() + "]");
                            //e.printStackTrace();
                        }
                    }
                    parser = null;
                    finalList.addAll(pList);
                    XtractorHelper.log("Total de Pesquisadores lidos: " + finalList.size() + " - Total: " + pList.size());
                } catch (IOException io) {
                    io.printStackTrace();
                    XtractorHelper.log("Erro ao processar o arquivo " + xmlFileNamePath);
                    CloseFileHelper.close(writer);
                }
            }
        }
        
        return finalList;
    }

    //public void createPublicacoesResumosArtigosAceitosPublicacao(List<Pesquisador> pesquisadores, String filename){}
    public void formatPublicacaoToDatabase(List<Pesquisador> pesquisadores, String baseDirectory, String dataExtracao) {

        if (pesquisadores == null || pesquisadores.isEmpty()){
            return;
        }
        
        //String baseDirectory = "C:\\";
        this.createPublicacoesArtigosCompletos(pesquisadores, baseDirectory + "\\artigos_completos.sql", dataExtracao);
        this.createPublicacoesLivrosPublicados(pesquisadores, baseDirectory + "\\livros_publicados.sql", dataExtracao);
        this.createPublicacoesCapitulosDeLivros(pesquisadores, baseDirectory + "\\capitulos_livros.sql", dataExtracao);
        this.createPublicacoesTextosEmJornais(pesquisadores, baseDirectory + "\\textos_jornais.sql", dataExtracao);
        this.createPublicacoesTrabalhoCompletoAnaisCongressos(pesquisadores, baseDirectory + "\\tabalhos_completos_anais_congr.sql", dataExtracao);
        this.createPublicacoesResumosExpandidorAnaisCongressos(pesquisadores, baseDirectory + "\\resumo_expandido_anais_congresso.sql", dataExtracao);
        this.createPublicacoesResumosPublicadosAnaisCongressos(pesquisadores, baseDirectory + "\\anais_congressos.sql", dataExtracao);
        this.createPublicacoesResumosApresentacaoTrabalhos(pesquisadores, baseDirectory + "\\apresentacao_trabalhos.sql", dataExtracao);
        this.createPublicacoesResumosDemaisTiposdDeProducaoBibliografica(pesquisadores, baseDirectory + "\\demais_producoes_bibliograficas.sql", dataExtracao);
        
    }

    /*
    //FIELDS
    tableFields = new String [] {
            "ID_PRODUCAO"
            , "TP_PRODUCAO"
            , "TITULO"
            , "NOME_PERIODICO"
            , "EDITORA"
            , "EDICAO"
            , "VOLUME"
            , "ANO"
            , "AUTORES"
            , "PAGINAS"
            , "NUMERO"
            , "DOI"
            , "EVENTO"
            , "NATUREZA_PUBL"
            , "DT_EXTRACAO"
    };
    
     */
    public void createPublicacoesArtigosCompletos(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {
        
        String TABLE_NAME = " SYS_POSGRAD.PRODUCAO_CIENT ";
        String TABLE_NAME_RELACIONAMENTO = " SYS_POSGRAD.PRODUCAO_CIENT_ORIENT ";
        StringBuilder statement = new StringBuilder();
        Pesquisador p = null;
        String[] tableValues = null;
        String[] tableFields = null;
        String[] tableFieldsRel = null;
        
        int idOrientador = -1;
        
        for (int i = 0; i < pesquisadores.size(); i++) {
            p = pesquisadores.get(i);
            ArtigosEmPeriodicos artigosEmPeriodicos = p.getArtigosEmPeriodicos();
            ArtigoVO vo = null;
            idOrientador = p.getInternalId();
            
            if (artigosEmPeriodicos != null) {
                List<ArtigoVO> listVo = artigosEmPeriodicos.getArtigosEmPeriodicos();
                
                if (listVo != null) {
                    //##############################################
                    //inserir aqui os nomes das tabelas
                    tableFields = new String[]{
                        "ID_PRODUCAO", "TP_PRODUCAO", "TITULO", "NOME_PERIODICO", "VOLUME", "ANO", "AUTORES", "PAGINAS", "NUMERO", "DOI", "DT_EXTRACAO"
                    };
                    
                    tableFieldsRel = new String[]{
                        "ID_PUBLICACAO", "ID_ORIENTADOR"
                    };
                    
                    for (int y = 0; y < listVo.size(); y++) {
                        vo = listVo.get(y);
                        if (vo != null) {
                            //##############################################
                            //inserir aqui os campos de valor
                            tableValues = new String[tableFields.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = Constants.PRODUCAO_ARTIGOS_COMPLETOS;;
                            tableValues[2] = vo.getTitulo();
                            tableValues[3] = vo.getRevista();
                            tableValues[4] = "";//vo.getVolume(); //Volume precisa ser alterado no ScriptLattes
                            tableValues[5] = vo.getAno();
                            tableValues[6] = vo.getAutores();
                            tableValues[7] = String.valueOf(vo.getPaginas());
                            tableValues[8] = vo.getNumero();
                            tableValues[9] = vo.getDoi();
                            tableValues[10] = dataExtracao;
                            
                            statement.append(this.generateInsertTable(TABLE_NAME, tableFields, tableValues)).append("\n");

                            //##############################################
                            //inserir dados na tabela de relacionamento
                            tableValues = new String[tableFieldsRel.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = String.valueOf(p.getInternalId());
                            statement.append(this.generateInsertTable(TABLE_NAME_RELACIONAMENTO, tableFieldsRel, tableValues)).append("\n");
                        }
                    }
                }
            }
        }
        
        this.persistXtractedData(statement, filename);
        
    }
    
    public void createPublicacoesLivrosPublicados(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {
        
        String TABLE_NAME = " SYS_POSGRAD.PRODUCAO_CIENT ";
        String TABLE_NAME_RELACIONAMENTO = " SYS_POSGRAD.PRODUCAO_CIENT_ORIENT ";
        StringBuilder statement = new StringBuilder();
        Pesquisador p = null;
        String[] tableValues = null;
        String[] tableFields = null;
        String[] tableFieldsRel = null;
        
        int idOrientador = -1;
        
        for (int i = 0; i < pesquisadores.size(); i++) {
            p = pesquisadores.get(i);
            LivrosPublicados livros = p.getLivrosPublicados();
            
            if (livros != null) {
                List<LivroPublicadoVO> listVo = livros.getLivrosPublicados();
                LivroPublicadoVO vo = null;
                idOrientador = p.getInternalId();
                
                if (listVo != null) {
                    //##############################################
                    //inserir aqui os nomes das tabelas
                    tableFields = new String[]{
                        "ID_PRODUCAO", "TP_PRODUCAO", "TITULO", "EDICAO", "VOLUME", "PAGINAS", "ANO", "AUTORES", "DT_EXTRACAO"
                    };
                    
                    tableFieldsRel = new String[]{
                        "ID_PUBLICACAO", "ID_ORIENTADOR"
                    };
                    
                    for (int y = 0; y < listVo.size(); y++) {
                        vo = listVo.get(y);
                        if (vo != null) {
                            //##############################################
                            //inserir aqui os campos de valor
                            tableValues = new String[tableFields.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = Constants.PRODUCAO_LIVROS_PUBLICADOS;;
                            tableValues[2] = vo.getTitulo();
                            tableValues[3] = vo.getEdicao();
                            tableValues[4] = vo.getVolume();
                            tableValues[5] = vo.getPaginas();
                            tableValues[6] = vo.getAno();
                            tableValues[7] = vo.getAutores();
                            tableValues[8] = dataExtracao;
                            statement.append(this.generateInsertTable(TABLE_NAME, tableFields, tableValues)).append("\n");

                            //##############################################
                            //inserir dados na tabela de relacionamento
                            tableValues = new String[tableFieldsRel.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = String.valueOf(p.getInternalId());
                            statement.append(this.generateInsertTable(TABLE_NAME_RELACIONAMENTO, tableFieldsRel, tableValues)).append("\n");
                        }
                    }
                }
            }
        }
        
        this.persistXtractedData(statement, filename);
    }
    
    public void createPublicacoesCapitulosDeLivros(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {
        
        String TABLE_NAME = " SYS_POSGRAD.PRODUCAO_CIENT ";
        String TABLE_NAME_RELACIONAMENTO = " SYS_POSGRAD.PRODUCAO_CIENT_ORIENT ";
        StringBuilder statement = new StringBuilder();
        Pesquisador p = null;
        String[] tableValues = null;
        String[] tableFields = null;
        String[] tableFieldsRel = null;
        boolean stop = false;
        
        int idOrientador = -1;
        
        for (int i = 0; i < pesquisadores.size(); i++) {
            p = pesquisadores.get(i);
            CapitulosLivros capitulosLivros = p.getCapitulosLivros();
            
            if (capitulosLivros != null) {
                List<CapitulosLivrosVO> listVo = capitulosLivros.getCapitulosLivros();
                CapitulosLivrosVO vo = null;
                idOrientador = p.getInternalId();
                
                if (listVo != null) {
                    //##############################################
                    //inserir aqui os nomes das tabelas
                    tableFields = new String[]{
                        "ID_PRODUCAO", "TP_PRODUCAO", "TITULO", "NOME_PERIODICO", "EDITORA", "EDICAO", "VOLUME", "ANO", "AUTORES", "PAGINAS", "DT_EXTRACAO"
                    };
                    
                    tableFieldsRel = new String[]{
                        "ID_PUBLICACAO", "ID_ORIENTADOR"
                    };
                    
                    for (int y = 0; y < listVo.size(); y++) {
                        vo = listVo.get(y);
                        
                        if (vo != null) {
                            //##############################################
                            //inserir aqui os campos de valor
                            tableValues = new String[tableFields.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = Constants.PRODUCAO_CAPITULO_DE_LIVROS;
                            tableValues[2] = vo.getTitulo();
                            tableValues[3] = vo.getLivro();
                            tableValues[4] = vo.getEditora();
                            tableValues[5] = vo.getEdicao();
                            tableValues[6] = vo.getAno();
                            tableValues[7] = vo.getVolume();
                            tableValues[8] = vo.getAutores();
                            tableValues[9] = vo.getPaginas();
                            tableValues[10] = dataExtracao;
                            statement.append(this.generateInsertTable(TABLE_NAME, tableFields, tableValues)).append("\n");

                            //##############################################
                            //inserir dados na tabela de relacionamento
                            tableValues = new String[tableFieldsRel.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = String.valueOf(p.getInternalId());
                            statement.append(this.generateInsertTable(TABLE_NAME_RELACIONAMENTO, tableFieldsRel, tableValues)).append("\n");
                        }
                    }
                }
            }
        }
        this.persistXtractedData(statement, filename);
        
    }
    
    public void createPublicacoesTextosEmJornais(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {
        
        String TABLE_NAME = " SYS_POSGRAD.PRODUCAO_CIENT ";
        String TABLE_NAME_RELACIONAMENTO = " SYS_POSGRAD.PRODUCAO_CIENT_ORIENT ";
        StringBuilder statement = new StringBuilder();
        Pesquisador p = null;
        String[] tableValues = null;
        String[] tableFields = null;
        String[] tableFieldsRel = null;
        
        int idOrientador = -1;
        
        for (int i = 0; i < pesquisadores.size(); i++) {
            p = pesquisadores.get(i);
            TextosEmJornal textosEmJornais = p.getTextosEmJornal();
            TextoJornalVO vo = null;
            idOrientador = p.getInternalId();
            
            if (textosEmJornais != null) {
                List<TextoJornalVO> listVo = textosEmJornais.getTextosEmJornal();
                
                if (listVo != null) {
                    //##############################################
                    //inserir aqui os nomes das tabelas
                    tableFields = new String[]{
                        "ID_PRODUCAO", "TP_PRODUCAO", "TITULO", "NOME_PERIODICO", "VOLUME", "ANO", "AUTORES", "PAGINAS", "DT_PUBLICACAO", "DT_EXTRACAO"
                    };
                    
                    tableFieldsRel = new String[]{
                        "ID_PUBLICACAO", "ID_ORIENTADOR"
                    };
                    
                    for (int y = 0; y < listVo.size(); y++) {
                        vo = listVo.get(y);
                        if (vo != null) {
                            //##############################################
                            //inserir aqui os campos de valor
                            tableValues = new String[tableFields.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = Constants.PRODUCAO_TEXTOS_EM_JORNAIS;;
                            tableValues[2] = vo.getTitulo();
                            tableValues[3] = vo.getNome_jornal();
                            tableValues[4] = vo.getVolume(); //Volume precisa ser alterado no ScriptLattes
                            tableValues[5] = vo.getAno();
                            tableValues[6] = vo.getAutores();
                            tableValues[7] = String.valueOf(vo.getPaginas());
                            tableValues[8] = XtractorHelper.formatDateDDMMYYY(vo.getData(), XtractorHelper.FORMAT_DATE_DDMMYYY_ABREV);
                            tableValues[9] = dataExtracao;
                            statement.append(this.generateInsertTable(TABLE_NAME, tableFields, tableValues)).append("\n");

                            //##############################################
                            //inserir dados na tabela de relacionamento
                            tableValues = new String[tableFieldsRel.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = String.valueOf(p.getInternalId());
                            statement.append(this.generateInsertTable(TABLE_NAME_RELACIONAMENTO, tableFieldsRel, tableValues)).append("\n");
                        }
                    }
                }
            }
        }
        this.persistXtractedData(statement, filename);
        
    }
    
    public void createPublicacoesTrabalhoCompletoAnaisCongressos(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {
        
        String TABLE_NAME = " SYS_POSGRAD.PRODUCAO_CIENT ";
        String TABLE_NAME_RELACIONAMENTO = " SYS_POSGRAD.PRODUCAO_CIENT_ORIENT ";
        StringBuilder statement = new StringBuilder();
        Pesquisador p = null;
        String[] tableValues = null;
        String[] tableFields = null;
        String[] tableFieldsRel = null;
        
        int idOrientador = -1;
        
        for (int i = 0; i < pesquisadores.size(); i++) {
            p = pesquisadores.get(i);
            TrabalhoCompletoCongresso trabalhoCompletoCongresso = p.getTrabalhoCompletoCongresso();
            TrabalhoCompletoVO vo = null;
            idOrientador = p.getInternalId();
            
            if (trabalhoCompletoCongresso != null) {
                List<TrabalhoCompletoVO> listVo = trabalhoCompletoCongresso.getTrabalhoCompletoCongresso();
                
                if (listVo != null) {
                    //##############################################
                    //inserir aqui os nomes das tabelas
                    tableFields = new String[]{
                        "ID_PRODUCAO", "TP_PRODUCAO", "TITULO", "VOLUME", "ANO", "AUTORES", "PAGINAS", "NUMERO", "DOI", "EVENTO", "DT_EXTRACAO"
                    };
                    
                    tableFieldsRel = new String[]{
                        "ID_PUBLICACAO", "ID_ORIENTADOR"
                    };
                    
                    for (int y = 0; y < listVo.size(); y++) {
                        vo = listVo.get(y);
                        if (vo != null) {
                            //##############################################
                            //inserir aqui os campos de valor
                            tableValues = new String[tableFields.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = Constants.PRODUCAO_TRABALHO_COMPLETO_ANAIS_CONGR;;
                            tableValues[2] = vo.getTitulo();
                            tableValues[3] = vo.getVolume();
                            tableValues[4] = vo.getAno();
                            tableValues[5] = vo.getAutores();
                            tableValues[6] = String.valueOf(vo.getPaginas());
                            tableValues[7] = XtractorHelper.nullToEmpty(vo.getNumero());
                            tableValues[8] = vo.getDoi();
                            tableValues[9] = vo.getNomeEvento();
                            tableValues[10] = dataExtracao;
                            statement.append(this.generateInsertTable(TABLE_NAME, tableFields, tableValues)).append("\n");

                            //##############################################
                            //inserir dados na tabela de relacionamento
                            tableValues = new String[tableFieldsRel.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = String.valueOf(p.getInternalId());
                            statement.append(this.generateInsertTable(TABLE_NAME_RELACIONAMENTO, tableFieldsRel, tableValues)).append("\n");
                        }
                    }
                }
            }
        }
        
        this.persistXtractedData(statement, filename);
    }
    
    public void createPublicacoesResumosExpandidorAnaisCongressos(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {
        
        String TABLE_NAME = " SYS_POSGRAD.PRODUCAO_CIENT ";
        String TABLE_NAME_RELACIONAMENTO = " SYS_POSGRAD.PRODUCAO_CIENT_ORIENT ";
        StringBuilder statement = new StringBuilder();
        Pesquisador p = null;
        
        String[] tableValues = null;
        String[] tableFields = null;
        String[] tableFieldsRel = null;
        
        int idOrientador = -1;
        
        for (int i = 0; i < pesquisadores.size(); i++) {
            p = pesquisadores.get(i);
            ResumoCongressoExpandido resumoCongExp = p.getResumoCongressoExpandido();
            ResumoCongressoExpandidoVO vo = null;
            idOrientador = p.getInternalId();
            
            if (resumoCongExp != null) {
                List<ResumoCongressoExpandidoVO> listVo = resumoCongExp.getResumoCongressoExpandido();
                
                if (listVo != null) {
                    //##############################################
                    //inserir aqui os nomes das tabelas
                    tableFields = new String[]{
                        "ID_PRODUCAO", "TP_PRODUCAO", "TITULO", "ANO", "AUTORES", "PAGINAS", "NUMERO", "VOLUME", "DOI", "EVENTO", "DT_EXTRACAO"
                    };
                    
                    tableFieldsRel = new String[]{
                        "ID_PUBLICACAO", "ID_ORIENTADOR"
                    };
                    //rwv
                    for (int y = 0; y < listVo.size(); y++) {
                        vo = listVo.get(y);
                        if (vo != null) {
                            //##############################################
                            //inserir aqui os campos de valor
                            tableValues = new String[tableFields.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = Constants.PRODUCAO_RESUMO_EXPANDIDO_PUBLIC_ANAIS_CONGR;
                            tableValues[2] = vo.getTitulo();
                            tableValues[3] = vo.getAno();
                            tableValues[4] = vo.getAutores();
                            tableValues[5] = String.valueOf(vo.getPaginas());
                            tableValues[6] = XtractorHelper.nullToEmpty(vo.getNumero());
                            tableValues[7] = vo.getVolume();
                            tableValues[8] = vo.getDoi();
                            tableValues[9] = vo.getNomeEvento();
                            tableValues[10] = dataExtracao;
                            statement.append(this.generateInsertTable(TABLE_NAME, tableFields, tableValues)).append("\n");

                            //##############################################
                            //inserir dados na tabela de relacionamento
                            tableValues = new String[tableFieldsRel.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = String.valueOf(p.getInternalId());
                            statement.append(this.generateInsertTable(TABLE_NAME_RELACIONAMENTO, tableFieldsRel, tableValues)).append("\n");
                        }
                    }
                }
            }
        }
        this.persistXtractedData(statement, filename);
    }
    
    public void createPublicacoesResumosPublicadosAnaisCongressos(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {

        /*
        //FIELDS
        tableFields = new String [] {
                "ID_PRODUCAO"
                , "TP_PRODUCAO"
                , "TITULO"
                , "NOME_PERIODICO"
                , "EDITORA"
                , "EDICAO"
                , "VOLUME"
                , "ANO"
                , "AUTORES"
                , "PAGINAS"
                , "NUMERO"
                , "DOI"
                , "EVENTO"
                , "NATUREZA_PUBL"
                , "DT_PUBLICACAO"
                , "DT_EXTRACAO"
        };
         */
        String TABLE_NAME = " SYS_POSGRAD.PRODUCAO_CIENT ";
        String TABLE_NAME_RELACIONAMENTO = " SYS_POSGRAD.PRODUCAO_CIENT_ORIENT ";
        StringBuilder statement = new StringBuilder();
        Pesquisador p = null;
        String[] tableValues = null;
        String[] tableFields = null;
        String[] tableFieldsRel = null;
        
        int idOrientador = -1;
        
        for (int i = 0; i < pesquisadores.size(); i++) {
            p = pesquisadores.get(i);
            
            ResumoCongresso resumoCongresso = p.getResumoCongresso();
            ResumoCongressoVO vo = null;
            idOrientador = p.getInternalId();
            
            if (resumoCongresso != null) {
                List<ResumoCongressoVO> listVo = resumoCongresso.getResumoCongresso();
                
                if (listVo != null) {
                    //##############################################
                    //inserir aqui os nomes das tabelas
                    tableFields = new String[]{
                        "ID_PRODUCAO", "TP_PRODUCAO", "TITULO", "ANO", "AUTORES", "PAGINAS", "NUMERO", "VOLUME", "DOI", "EVENTO", "DT_EXTRACAO"
                    };
                    
                    tableFieldsRel = new String[]{
                        "ID_PUBLICACAO", "ID_ORIENTADOR"
                    };
                    //rwv
                    for (int y = 0; y < listVo.size(); y++) {
                        vo = listVo.get(y);
                        if (vo != null) {
                            //##############################################
                            //inserir aqui os campos de valor
                            tableValues = new String[tableFields.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = Constants.PRODUCAO_RESUMO_PUBLIC_ANAIS_CONGR;
                            tableValues[2] = vo.getTitulo();
                            tableValues[3] = vo.getAno();
                            tableValues[4] = vo.getAutores();
                            tableValues[5] = String.valueOf(vo.getPaginas());
                            tableValues[6] = XtractorHelper.nullToEmpty(vo.getNumero());
                            tableValues[7] = vo.getVolume();
                            tableValues[8] = vo.getDoi();
                            tableValues[9] = vo.getNomeEvento();
                            tableValues[10] = dataExtracao;
                            statement.append(this.generateInsertTable(TABLE_NAME, tableFields, tableValues)).append("\n");

                            //##############################################
                            //inserir dados na tabela de relacionamento
                            tableValues = new String[tableFieldsRel.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = String.valueOf(p.getInternalId());
                            statement.append(this.generateInsertTable(TABLE_NAME_RELACIONAMENTO, tableFieldsRel, tableValues)).append("\n");
                        }
                    }
                }
            }
        }
        this.persistXtractedData(statement, filename);
        
    }
    
    public void createPublicacoesResumosApresentacaoTrabalhos(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {

        /*
        //FIELDS
        tableFields = new String [] {
                "ID_PRODUCAO"
                , "TP_PRODUCAO"
                , "TITULO"
                , "NOME_PERIODICO"
                , "EDITORA"
                , "EDICAO"
                , "VOLUME"
                , "ANO"
                , "AUTORES"
                , "PAGINAS"
                , "NUMERO"
                , "DOI"
                , "EVENTO"
                , "NATUREZA_PUBL"
                , "DT_PUBLICACAO"
                , "DT_EXTRACAO"
        };
         */
        String TABLE_NAME = " SYS_POSGRAD.PRODUCAO_CIENT ";
        String TABLE_NAME_RELACIONAMENTO = " SYS_POSGRAD.PRODUCAO_CIENT_ORIENT ";
        StringBuilder statement = new StringBuilder();
        Pesquisador p = null;
        String[] tableValues = null;
        String[] tableFields = null;
        String[] tableFieldsRel = null;
        
        int idOrientador = -1;
        
        for (int i = 0; i < pesquisadores.size(); i++) {
            p = pesquisadores.get(i);
            TrabalhoCompletoCongresso trabalhosCompletos = p.getTrabalhoCompletoCongresso();
            TrabalhoCompletoVO vo = null;
            idOrientador = p.getInternalId();
            
            if (trabalhosCompletos != null) {
                List<TrabalhoCompletoVO> listVo = trabalhosCompletos.getTrabalhoCompletoCongresso();
                
                if (listVo != null) {
                    //##############################################
                    //inserir aqui os nomes das tabelas
                    tableFields = new String[]{
                        "ID_PRODUCAO", "TP_PRODUCAO", "TITULO", "VOLUME", "ANO", "AUTORES", "PAGINAS", "NUMERO", "DOI", "EVENTO", "DT_EXTRACAO"
                    };
                    
                    tableFieldsRel = new String[]{
                        "ID_PUBLICACAO", "ID_ORIENTADOR"
                    };
                    //rwv
                    for (int y = 0; y < listVo.size(); y++) {
                        vo = listVo.get(y);
                        if (vo != null) {
                            //##############################################
                            //inserir aqui os campos de valor
                            tableValues = new String[tableFields.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = Constants.PRODUCAO_RESUMO_APRESENTACAO_TRABALHO;
                            tableValues[2] = vo.getTitulo();
                            tableValues[3] = vo.getVolume();
                            tableValues[4] = vo.getAno();
                            tableValues[5] = vo.getAutores();
                            tableValues[6] = String.valueOf(vo.getPaginas());
                            tableValues[7] = XtractorHelper.nullToEmpty(vo.getNumero());
                            tableValues[8] = vo.getDoi();
                            tableValues[9] = vo.getNomeEvento();
                            tableValues[10] = dataExtracao;
                            statement.append(this.generateInsertTable(TABLE_NAME, tableFields, tableValues)).append("\n");
                            
                            //##############################################
                            //inserir dados na tabela de relacionamento
                            tableValues = new String[tableFieldsRel.length];
                            tableValues[0] = "{ID_PRODUCAO_TAG}";
                            tableValues[1] = String.valueOf(p.getInternalId());
                            statement.append(this.generateInsertTable(TABLE_NAME_RELACIONAMENTO, tableFieldsRel, tableValues)).append("\n");
                        }
                    }
                }
            }
        }
        
        this.persistXtractedData(statement, filename);
        
    }
    
    public void createPublicacoesResumosDemaisTiposdDeProducaoBibliografica(List<Pesquisador> pesquisadores, String filename, String dataExtracao) {
        
    }
    
    private String generateInsertTable(String tableName, String[] fields, String[] values) {
        
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(tableName).append("(");
        
        for (int i = 0; i < fields.length; i++) {
            
            builder.append(fields[i]);
            if (i != fields.length - 1) {
                builder.append(" , ");
            }
        }
        
        builder.append(") VALUES ( ");
        
        for (int i = 0; i < values.length; i++) {
            builder.append("'").append(XtractorHelper.escapeXmlChars(XtractorHelper.nullToEmpty(values[i]))).append("'");
            if (i != values.length - 1) {
                builder.append(" , ");
            }
        }
        builder.append(");");
        
        return builder.toString();
        
    }
    
    public List<Pesquisador> removeDuplicated(List<Pesquisador> pesquisadores) {
        
        if (pesquisadores == null || pesquisadores.isEmpty()) {
            return null;
        }
        
        boolean hasPesquisador = false;
        List<Pesquisador> list = new ArrayList();
        Pesquisador pesquisadorTemp = null;
        Pesquisador pesquisadorInNewList = null;
        
        Iterator<Pesquisador> it = pesquisadores.iterator();
        
        for (int y = 0; y < pesquisadores.size(); y++) {
            pesquisadorTemp = pesquisadores.get(y);
            for (int i = 0; i < list.size(); i++) {
                pesquisadorInNewList = list.get(i);
                
                if (pesquisadorTemp.getId().equals(pesquisadorInNewList.getId()) && pesquisadorTemp.getInternalId() == pesquisadorInNewList.getInternalId()) {
                    hasPesquisador = true;
                    break;
                }
            }
            
            if (!hasPesquisador) {
                if (pesquisadorTemp != null) {
                    list.add(pesquisadorTemp);
                }
            }
            
            hasPesquisador = false;
            
        }
        
        XtractorHelper.debug("Final Pesquisadores: " + list.size() + " --> Total de entrada: " + pesquisadores.size());
        
        return list;
        
    }

    /**
     * Le um arquivo com todos os IDS e Identificador de Orientador e retorna um
     * HashMap<key, value>
     *
     * @param filename
     * @return
     */
    public HashMap<String, String> createHashMapByFileSeparatedByPype(String filename) {
        HashMap<String, String> hmap = new HashMap();
        
        BufferedReader reader = null;
        String line;
        String[] arrTemp;
        
        try {
            reader = new BufferedReader(new FileReader(filename));
            
            while ((line = reader.readLine()) != null) {
                
                line = line.trim();
                if (line.equals("")) {
                    continue;
                }
                
                arrTemp = line.split("\\|");
                hmap.put(arrTemp[0], arrTemp[1]);
                //XtractorHelper.debug("---> 0[" + arrTemp[0] + "] --->1 [" + arrTemp[1] + "]");
            }
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            CloseFileHelper.close(reader);
        }
        return hmap;
        
    }
    
    public void persistXtractedData(StringBuilder valueToPersist, String filename) {
        
        if (valueToPersist == null) {
            return;
        }
        
        XtractorHelper.writeDataToDisk(valueToPersist, filename);
    }
    
}
