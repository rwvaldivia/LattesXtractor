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

import br.xtractor.helper.SimplePreProcessXmlText;
import br.xtractor.lattes.core.ApresentacaoTrabalho;
import br.xtractor.lattes.core.AreaAtuacao;
import br.xtractor.lattes.core.ArtigosEmPeriodicos;
import br.xtractor.lattes.core.ArtigosEmRevistas;
import br.xtractor.lattes.core.CapitulosLivros;
import br.xtractor.lattes.core.Colaboradores;
import br.xtractor.lattes.core.CurriculoLattes;
import br.xtractor.lattes.core.FormacaoAcademica;
import br.xtractor.lattes.core.Idiomas;
import br.xtractor.lattes.core.LivrosPublicados;
import br.xtractor.lattes.core.OrganizacaoEvento;
import br.xtractor.lattes.core.OrientacaoDoutoradoAndamento;
import br.xtractor.lattes.core.OrientacaoDoutoradoConcluido;
import br.xtractor.lattes.core.OrientacaoEspecializacaoConcluida;
import br.xtractor.lattes.core.OrientacaoIniciacaoCientificaAndamento;
import br.xtractor.lattes.core.OrientacaoIniciacaoCientificaConcluida;
import br.xtractor.lattes.core.OrientacaoMestradoAndamento;
import br.xtractor.lattes.core.OrientacaoMestradoConcluido;
import br.xtractor.lattes.core.OrientacaoMonografiaEspecializacaoAndamento;
import br.xtractor.lattes.core.OrientacaoOutrosTiposAndamento;
import br.xtractor.lattes.core.OrientacaoOutrosTiposConcluida;
import br.xtractor.lattes.core.OrientacaoTccAndamento;
import br.xtractor.lattes.core.OrientacaoTccConcluida;
import br.xtractor.lattes.core.ParticipacaoEvento;
import br.xtractor.lattes.core.PremiosTitulos;
import br.xtractor.lattes.core.ProcessoOuTecnica;
import br.xtractor.lattes.core.ProducaoArtistica;
import br.xtractor.lattes.core.ProducaoBibliografica;
import br.xtractor.lattes.core.ProducaoTecnica;
import br.xtractor.lattes.core.ProdutoTecnologico;
import br.xtractor.lattes.core.ProjetosPesquisa;
import br.xtractor.lattes.core.ResumoCongresso;
import br.xtractor.lattes.core.ResumoCongressoExpandido;
import br.xtractor.lattes.core.SoftwareComPatente;
import br.xtractor.lattes.core.SoftwareSemPatente;
import br.xtractor.lattes.core.SupervisaoPosDoutoradoAndamento;
import br.xtractor.lattes.core.SupervisaoPosDoutoradoConcluido;
import br.xtractor.lattes.core.TextosEmJornal;
import br.xtractor.lattes.core.TrabalhoCompletoCongresso;
import br.xtractor.lattes.core.TrabalhosTecnicos;
import br.xtractor.lattes.vo.ArtigoRevistaVO;
import br.xtractor.lattes.vo.ArtigoVO;
import br.xtractor.lattes.vo.CapitulosLivrosVO;
import br.xtractor.lattes.vo.DissertacaoVO;
import br.xtractor.lattes.vo.EnderecoVO;
import br.xtractor.lattes.vo.EventoVO;
import br.xtractor.lattes.vo.FormacaoVO;
import br.xtractor.lattes.vo.IdentificacaoVO;
import br.xtractor.lattes.vo.IdiomaVO;
import br.xtractor.lattes.vo.IniciacaoCientificaVO;
import br.xtractor.lattes.vo.LivroPublicadoVO;
import br.xtractor.lattes.vo.MonografiaVO;
import br.xtractor.lattes.vo.OrientacaoOutrosTiposVO;
import br.xtractor.lattes.vo.Pesquisador;
import br.xtractor.lattes.vo.PremioTituloVO;
import br.xtractor.lattes.vo.ProducaoVO;
import br.xtractor.lattes.vo.ProdutoVO;
import br.xtractor.lattes.vo.ProjetoVO;
import br.xtractor.lattes.vo.ResumoCongressoExpandidoVO;
import br.xtractor.lattes.vo.ResumoCongressoVO;
import br.xtractor.lattes.vo.SoftwareVO;
import br.xtractor.lattes.vo.SupervisaoVO;
import br.xtractor.lattes.vo.TccVO;
import br.xtractor.lattes.vo.TeseVO;
import br.xtractor.lattes.vo.TextoJornalVO;
import br.xtractor.lattes.vo.TrabalhoApresentadoVO;
import br.xtractor.lattes.vo.TrabalhoCompletoVO;
import br.xtractor.lattes.vo.TrabalhoTecnicoVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author richardv
 */
public class ScriptLattesXmlParser {
    
    private String xmlPathFile;
    
    public ScriptLattesXmlParser(String xmlPathFile) throws IOException {
        this.xmlPathFile = xmlPathFile;
    }
    
    public CurriculoLattes parse() throws IOException {
        
        if (this.xmlPathFile == null || "".equals(this.xmlPathFile)) {
            throw new IllegalArgumentException("EN: XML file name invalid/PT-BR: Nome de arquivo XML invalido");
        }
        
        XStream xst = new XStream(new DomDriver());
        
        /*
         * Objeto CurriculoLattes
         */
        xst.alias("curriculo_lattes", CurriculoLattes.class);
        //xst.useAttributeFor(CurriculoLattes.class, "data_processamento");
        xst.addImplicitCollection(CurriculoLattes.class, "pesquisador", Pesquisador.class);
        
        
        /*
         * Objeto Pesquisador
         */
        
        xst.alias("pesquisador", Pesquisador.class);
        xst.useAttributeFor(Pesquisador.class, "id");
        
        /*
         * Objeto Identificacao
         */
        xst.alias("identficacao", IdentificacaoVO.class);
        xst.aliasField("nome_inicial", IdentificacaoVO.class, "nomeInicial");
        xst.aliasField("nome_completo", IdentificacaoVO.class, "nomeCompleto");
        xst.aliasField("nome_citacao_bibliografica", IdentificacaoVO.class, "nomeCitacaoBibliografica");
        
        /*
         * Objeto Idiomas
         */
        xst.alias("idiomas", Idiomas.class);
        xst.addImplicitCollection(Idiomas.class, "idiomas", IdiomaVO.class);
        
        /*
         * Objeto Idioma
         */
        xst.alias("idioma", IdiomaVO.class);
        
        /*
         * Objeto Endereco
         */
        xst.alias("endereco", EnderecoVO.class);
        xst.aliasField("endereco_profissional", EnderecoVO.class, "enderecoProfissional");
        xst.aliasField("endereco_profissional_lat", EnderecoVO.class, "enderecoProfissionalLat");
        xst.aliasField("endereco_profissional_long", EnderecoVO.class, "enderecoProfissionalLong");
        
        /*
         * Objeto Formacao Academica
         */
        xst.alias("formacao_academica", FormacaoAcademica.class);
        xst.aliasField("formacao_academica", Pesquisador.class, "formacaoAcademica");
        xst.addImplicitCollection(FormacaoAcademica.class, "formacao", FormacaoVO.class);
        
        /*
         * Objeto Formacao
         */
        xst.alias("formacao", FormacaoVO.class);
        xst.aliasField("ano_inicio", FormacaoVO.class, "anoInicio");
        xst.aliasField("ano_conclusao", FormacaoVO.class, "anoConclusao");
        xst.aliasField("nome_instituicao", FormacaoVO.class, "nomeInstituicao");
        
        /*
         * Objeto ProjetosPesquisa
         */
        xst.alias("projetos_pesquisa", ProjetosPesquisa.class);
        xst.aliasField("projetos_pesquisa", Pesquisador.class, "projetosPesquisa");
        xst.addImplicitCollection(ProjetosPesquisa.class, "projeto", ProjetoVO.class);
        
        /*
         * Objeto Projeto
         */        
        xst.alias("projeto", ProjetoVO.class);
        xst.aliasField("ano_inicio", ProjetoVO.class, "anoInicio");
        xst.aliasField("ano_conclusao", ProjetoVO.class, "anoConclusao");
        
        /*
         * Objeto AreaAtuacao
         */
        
        xst.alias("area_atuacao", AreaAtuacao.class);
        xst.aliasField("area_atuacao", Pesquisador.class, "areaAtuacao");
        xst.addImplicitCollection(AreaAtuacao.class, "descricao", String.class);
        
        /*
         * Objeto PremiosTitulos
         */
        
        xst.alias("premios_titulos", PremiosTitulos.class);
        xst.aliasField("premios_titulos", Pesquisador.class, "premiosTitulos");
        xst.aliasField("premio_titulo", PremiosTitulos.class, "premiosTitulos");
        xst.addImplicitCollection(PremiosTitulos.class, "premiosTitulos", PremioTituloVO.class);
        
        /*
         * Objeto PremiosTitulosVO
         */
        
        xst.alias("premio_titulo", PremioTituloVO.class);
        
        /*
         * Objeto Colaborador
         */
        
        xst.alias("colaboradores", Colaboradores.class);
        xst.aliasField("colaboradores", Pesquisador.class, "colaboradores");
        xst.addImplicitCollection(Colaboradores.class, "colaboradores", String.class);
        xst.aliasField("id_lattes_colaborador", Colaboradores.class, "colaboradores");
        
        /*
         * Objeto ColaboradorVO
         */
        /*
        xst.alias("id_lattes_colaborador", ColaboradorVO.class);
        xst.aliasField("id_lattes_colaborador", ColaboradorVO.class, "idLattes");
        */
        
        /*
         * objeto ArtigoEmPeriodicos
         */
        xst.alias("artigos_em_periodicos", ArtigosEmPeriodicos.class);
        xst.aliasField("artigos_em_periodicos", Pesquisador.class, "artigosEmPeriodicos");
        xst.addImplicitCollection(ArtigosEmPeriodicos.class, "artigosEmPeriodicos", ArtigoVO.class);
        
        /*
         * objeto ArtigoVO
         */
        xst.alias("artigo", ArtigoVO.class);
        
        /*
         * objeto TextoEmJornal
         */
        xst.alias("texto_em_jornal", TextosEmJornal.class);
        xst.aliasField("texto_em_jornal", Pesquisador.class, "textosEmJornal");
        xst.addImplicitCollection(TextosEmJornal.class, "textosEmJornal", TextoJornalVO.class);
        
        /*
         * Objeto TextoJornal
         */
        xst.alias("texto", TextoJornalVO.class);
        
        /*
         * objeto TrabalhoCompletoCongresso
         */
        xst.alias("trabalho_completo_congresso", TrabalhoCompletoCongresso.class);
        xst.aliasField("trabalho_completo_congresso", Pesquisador.class, "trabalhoCompletoCongresso");
        xst.aliasField("trabalho_completo", TrabalhoCompletoCongresso.class, "trabalhoCompletoCongresso");
        xst.addImplicitCollection(TrabalhoCompletoCongresso.class, "trabalhoCompletoCongresso", TrabalhoCompletoVO.class);
        
        /*
         * objeto TrabalhoCompletoVO
         */
        xst.alias("trabalho_completo", TrabalhoCompletoVO.class);
        xst.aliasField("nome_evento", TrabalhoCompletoVO.class, "nomeEvento");
        
        /*
         * objeto ResumoCongressoExpandido
         */
        xst.alias("resumo_expandido_congresso", ResumoCongressoExpandido.class);
        xst.aliasField("resumo_expandido_congresso", Pesquisador.class, "resumoCongressoExpandido");
        xst.aliasField("resumo_expandido", ResumoCongressoExpandido.class, "resumoCongressoExpandido");
        xst.addImplicitCollection(ResumoCongressoExpandido.class, "resumoCongressoExpandido", ResumoCongressoExpandidoVO.class);
        
        /*
         * objeto ResumoExpandidoVO
         */
        xst.alias("resumo_expandido", ResumoCongressoExpandidoVO.class);
        xst.aliasField("nome_evento", ResumoCongressoExpandidoVO.class, "nomeEvento");
        
        /*
         * objeto ResumoCongresso
         */
        
        xst.alias("resumo_congresso", ResumoCongresso.class);
        xst.aliasField("resumo_congresso", Pesquisador.class, "resumoCongresso");
        xst.addImplicitCollection(ResumoCongresso.class, "resumoCongresso", ResumoCongressoVO.class);
        
        /*
         * Objeto ResumoCongressoVO
         */
        xst.alias("resumo", ResumoCongressoVO.class);
        xst.aliasField("nome_evento", ResumoCongressoVO.class, "nomeEvento");
        
        /*
         * Objetos ArtigosEmRevista
         */
        xst.alias("artigos_em_revista", ArtigosEmRevistas.class);
        xst.aliasField("artigos_em_revista", Pesquisador.class, "artigosEmRevista");
        xst.addImplicitCollection(ArtigosEmRevistas.class, "artigosEmRevistas", ArtigoRevistaVO.class);
        
        /*
         * Objeto ArtigoRevistaVO
         */
        xst.alias("artigo_revista", ArtigoRevistaVO.class);
        
        /*
         * Objeto ApresentacaoTrabalho
         */
        xst.alias("apresentacao_trabalho", ApresentacaoTrabalho.class);
        xst.aliasField("apresentacao_trabalho", ApresentacaoTrabalho.class, "trabalhosApresentados");
        xst.aliasField("apresentacao_trabalho", Pesquisador.class, "trabalhosApresentados");
        xst.addImplicitCollection(ApresentacaoTrabalho.class, "trabalhosApresentados", TrabalhoApresentadoVO.class);
        
        /*
         * Objeto TrabalhoApresentadoVO
         */
        xst.alias("trabalho_apresentado", TrabalhoApresentadoVO.class);
        
        
        /*
         * Objeto ParticipacaoEvento
         */
        xst.alias("participacao_evento", ParticipacaoEvento.class);
        xst.aliasField("participacao_evento", Pesquisador.class, "participacaoEventos");
        xst.addImplicitCollection(ParticipacaoEvento.class, "participacaoEventos", EventoVO.class);
        
        /*
         * Objeto EventoVO
         */
        xst.alias("evento", EventoVO.class);
        
        /*
         * Objeto OrganizacaoEvento
         */
        xst.alias("organizacao_evento", OrganizacaoEvento.class);
        xst.aliasField("organizacao_evento", Pesquisador.class, "organizacaoEventos");
        xst.addImplicitCollection(OrganizacaoEvento.class, "organizacaoEventos", EventoVO.class);
        
        /*
         * Objeto CapitulosLivros
         */
        xst.alias("capitulos_livros", CapitulosLivros.class);
        xst.aliasField("capitulos_livros", Pesquisador.class, "capitulosLivros");
        xst.addImplicitCollection(CapitulosLivros.class, "capitulosLivros", CapitulosLivrosVO.class);
        
        /*
         * objeto CapitulosLivrosVO
         */
        xst.alias("capitulo", CapitulosLivrosVO.class);
        
        /*
         * Objeto OrientacaoDissertacaoMestradoAndamento
         */
        xst.alias("orientacao_mestrado_em_andamento", OrientacaoMestradoAndamento.class);
        xst.aliasField("orientacao_mestrado_em_andamento", Pesquisador.class, "orientacaoMestradoEmAndamento");
        xst.addImplicitCollection(OrientacaoMestradoAndamento.class, "orientacaoMestradoAndamento", DissertacaoVO.class);
        
        /*
         * Objeto DissertacaoVO
         */
        xst.alias("dissertacao", DissertacaoVO.class);
        xst.aliasField("nome_aluno", DissertacaoVO.class, "nomeAluno");
        xst.aliasField("titulo_trabalho", DissertacaoVO.class, "tituloTrabalho");
        xst.aliasField("agencia_de_fomento", DissertacaoVO.class, "agenciaDeFomento");
        xst.aliasField("tipo_de_orientacao", DissertacaoVO.class, "tipoDeOrientacao");
        
        /*
         * Objeto LivrosPublicados
         */
        xst.alias("livros_publicados", LivrosPublicados.class);
        xst.aliasField("livros_publicados", Pesquisador.class, "livrosPublicados");
        xst.addImplicitCollection(LivrosPublicados.class, "livrosPublicados", LivroPublicadoVO.class);
        
        /*
         * Objeto LivroPublicado
         */
        xst.alias("livro", LivroPublicadoVO.class);
        
        /*
         * Objeto ProducaoTecnica
         */
        xst.alias("producao_tecnica", ProducaoTecnica.class);
        xst.aliasField("producao_tecnica", Pesquisador.class, "producaoTecnica");
        xst.addImplicitCollection(ProducaoTecnica.class, "producaoTecnica", ProducaoVO.class);
        
        /*
         * Objeto ProducaoVO
         */
        xst.alias("producao", ProducaoVO.class);
        
        /*
         * objeto OrientacaoDoutoradoConcluido
         */
        xst.alias("orientacao_doutorado_concluido", OrientacaoDoutoradoConcluido.class);
        xst.aliasField("orientacao_doutorado_concluido", Pesquisador.class, "orientacoesDoutoradoConcluidos");
        xst.addImplicitCollection(OrientacaoDoutoradoConcluido.class, "orientacoesDoutoradoConcluidos", TeseVO.class);
        
        /*
         * objeto TeseVO
         */
        xst.alias("tese", TeseVO.class);
        xst.aliasField("nome_aluno", TeseVO.class, "nomeAluno");
        xst.aliasField("titulo_trabalho", TeseVO.class, "tituloTrabalho");
        xst.aliasField("agencia_de_fomento", TeseVO.class, "agenciaDeFomento");
        xst.aliasField("tipo_de_orientacao", TeseVO.class, "tipoDeOrientacao");
        
        /*
         * objeto OrientacaoMestradoConcluido
         */
        
        xst.alias("orientacao_mestrado_concluido", OrientacaoMestradoConcluido.class);
        xst.aliasField("orientacao_mestrado_concluido", Pesquisador.class, "orientacoesMestradoConcluidos");
        xst.addImplicitCollection(OrientacaoMestradoConcluido.class, "orientacoesMestradoConcluidos", DissertacaoVO.class);
        
        /*
         * objeto OrientacaoMonografiaEspecializacaoConcluido
         * orientacao_especializacao_concluido
         */
        xst.alias("orientacao_especializacao_concluido", OrientacaoEspecializacaoConcluida.class);
        xst.aliasField("orientacao_especializacao_concluido", Pesquisador.class, "orientacoesEspecializacaoConcluidos");
        xst.addImplicitCollection(OrientacaoEspecializacaoConcluida.class, "orientacoesEspecializacaoConcluidos", MonografiaVO.class);
        
        /*
         * objeto MonografiaVO
         */
        xst.alias("monografia", MonografiaVO.class);
        xst.aliasField("nome_aluno", MonografiaVO.class, "nomeAluno");
        xst.aliasField("titulo_trabalho", MonografiaVO.class, "tituloTrabalho");
        xst.aliasField("agencia_de_fomento", MonografiaVO.class, "agenciaDeFomento");
        xst.aliasField("tipo_de_orientacao", MonografiaVO.class, "tipoDeOrientacao");
        
        /*
         * objeto OrientacaoDoutoradoAndamento
         */
        xst.alias("orientacao_doutorado_em_andamento", OrientacaoDoutoradoAndamento.class);
        xst.aliasField("orientacao_doutorado_em_andamento", Pesquisador.class, "orientacoesDoutoradoEmAndamento");
        xst.addImplicitCollection(OrientacaoDoutoradoAndamento.class, "orientacoesDoutoradoEmAndamento", TeseVO.class);
        
        /*
         * Objeto OrientacaoIniciacaoCientifica
         */
        xst.alias("orientacao_iniciacao_cientifica_em_andamento", OrientacaoIniciacaoCientificaAndamento.class);
        xst.aliasField("orientacao_iniciacao_cientifica_em_andamento", Pesquisador.class, "orientacoesIniciacaoCientificaAndamento");
        xst.addImplicitCollection(OrientacaoIniciacaoCientificaAndamento.class, "orientacoesIniciacaoCientificaAndamento", IniciacaoCientificaVO.class);
        
        /*
         * Objeto IniciacaoCientificaVO
         */
        
        xst.alias("iniciacao_cientifica", IniciacaoCientificaVO.class);
        xst.aliasField("nome_aluno", IniciacaoCientificaVO.class, "nomeAluno");
        xst.aliasField("titulo_trabalho", IniciacaoCientificaVO.class, "tituloTrabalho");
        xst.aliasField("agencia_de_fomento", IniciacaoCientificaVO.class, "agenciaFomento");
        xst.aliasField("tipo_de_orientacao", IniciacaoCientificaVO.class, "tipoOrientacao");
        
        
        /*
         * Objeto IniciacaoCientificaVO
         */
        xst.alias("orientacao_iniciacao_cientifica_concluido", OrientacaoIniciacaoCientificaConcluida.class);
        xst.aliasField("orientacao_iniciacao_cientifica_concluido", Pesquisador.class, "orientacoesIniciacaoCientificaConcluida");
        xst.addImplicitCollection(OrientacaoIniciacaoCientificaConcluida.class, "orientacoesIniciacaoCientificaConcluida", IniciacaoCientificaVO.class);
        
        /*
         * Objeto ProducaoBibliografica
         */
        xst.alias("producao_bibliografica", ProducaoBibliografica.class);
        xst.aliasField("producao_bibliografica", Pesquisador.class, "producoesBibliograficas");
        xst.addImplicitCollection(ProducaoBibliografica.class, "producoesBibliograficas", ProducaoVO.class);
        
        /*
         * Objeto TrabalhosTecnicos
         */
        xst.alias("trabalhos_tecnicos", TrabalhosTecnicos.class);
        xst.aliasField("trabalhos_tecnicos", Pesquisador.class, "trabalhosTecnicos");
        xst.addImplicitCollection(TrabalhosTecnicos.class, "trabalhosTecnicos", TrabalhoTecnicoVO.class);
        
        /*
         * Objeto TrabalhoTecnicoVO
         */
        xst.alias("trabalho", TrabalhoTecnicoVO.class);
        
        /**
         * Objeto OrientacaoTccConcluida
         */
        xst.alias("orientacao_tcc_concluido", OrientacaoTccConcluida.class);
        xst.aliasField("orientacao_tcc_concluido", Pesquisador.class, "orientacoesTccConcluidas");
        xst.addImplicitCollection(OrientacaoTccConcluida.class, "orientacoesTccConcluidas", TccVO.class);
        
        /**
         * Objeto TccVO
         */
        xst.alias("tcc", TccVO.class);
        xst.aliasField("nome_aluno", TccVO.class, "nomeAluno");
        xst.aliasField("agencia_de_fomento", TccVO.class, "agenciaFomento");
        xst.aliasField("tipo_de_orientacao", TccVO.class, "tipoOrientacao");
        xst.aliasField("titulo_trabalho", TccVO.class, "tituloTrabalho");
        
        /**
         * Objeto OrientacaoOutrosTiposConcluido
         */
        xst.alias("orientacao_outros_tipos_concluido", OrientacaoOutrosTiposConcluida.class);
        xst.aliasField("orientacao_outros_tipos_concluido", Pesquisador.class, "orientacoesOutrosTiposConcluida");
        xst.addImplicitCollection(OrientacaoOutrosTiposConcluida.class, "orientacoesOutrosTiposConcluida", OrientacaoOutrosTiposVO.class);
        
        /**
         * Objeto OrientacaoOutrosTiposVO
         */
        xst.alias("orientacao_outra", OrientacaoOutrosTiposVO.class);
        xst.aliasField("nome_aluno", OrientacaoOutrosTiposVO.class, "nomeAluno");
        xst.aliasField("agencia_de_fomento", OrientacaoOutrosTiposVO.class, "agenciaFomento");
        xst.aliasField("tipo_de_orientacao", OrientacaoOutrosTiposVO.class, "tipoOrientacao");
        xst.aliasField("titulo_trabalho", OrientacaoOutrosTiposVO.class, "tituloTrabalho");
        
        /**
         * Objeto ProdutoTecnologico
         */
        xst.alias("produto_tecnologico", ProdutoTecnologico.class);
        xst.aliasField("produto_tecnologico", Pesquisador.class, "produtoTecnologico");
        xst.addImplicitCollection(ProdutoTecnologico.class, "produtoTecnologico", ProdutoVO.class);
        
        /**
         * Objeto ProdutoVO
         */
        xst.alias("produto", ProdutoVO.class);
        
         /**
         * Objeto SupervisaoPosDoutoradoAndamento
         */
        xst.alias("supervisao_pos_doutorado_em_andamento", SupervisaoPosDoutoradoAndamento.class);
        xst.aliasField("supervisao_pos_doutorado_em_andamento", Pesquisador.class, "supervisoesDoutAndamento");
        xst.addImplicitCollection(SupervisaoPosDoutoradoAndamento.class, "supervisoesDoutAndamento", SupervisaoVO.class);
        
        /*
         * Objeto SupervisaoVO
         */
        xst.alias("supervisao", SupervisaoVO.class);
        xst.aliasField("titulo_trabalho", SupervisaoVO.class, "tituloTrabalho");
        xst.aliasField("nome_aluno", SupervisaoVO.class, "nomeAluno");
        xst.aliasField("agencia_de_fomento", SupervisaoVO.class, "agenciaFomento");
        xst.aliasField("tipo_de_orientacao", SupervisaoVO.class, "tipoOrientacao");
        
        /**
         * Objeto SupervisaoPosDoutoradoConcluido
         */
        xst.alias("supervisao_pos_doutorado_em_andamento", SupervisaoPosDoutoradoConcluido.class);
        xst.aliasField("supervisao_pos_doutorado_concluido", Pesquisador.class, "supervisaoPosDoutConcluido");
        xst.addImplicitCollection(SupervisaoPosDoutoradoConcluido.class, "supervisaoPosDoutConcluido", SupervisaoVO.class);
        
        /**
         * Objeto ProducaoArtistica
         */
        xst.alias("producao_artistica", ProducaoArtistica.class);
        xst.aliasField("producao_artistica", Pesquisador.class, "producaoArtistica");
        xst.addImplicitCollection(ProducaoArtistica.class, "producaoArtistica", ProducaoVO.class);
        
        /**
         * Objeto ProducaoArtisticaVO
         */
        xst.alias("orientacao_outros_tipos_em_andamento", OrientacaoOutrosTiposAndamento.class);
        xst.aliasField("orientacao_outros_tipos_em_andamento", Pesquisador.class, "orientacaoOutrosTiposAndamento");
        xst.addImplicitCollection(OrientacaoOutrosTiposAndamento.class, "orientacaoOutrosTiposAndamento", OrientacaoOutrosTiposVO.class);
        
        /**
         * Objeto OrientacaoTccAndamento
         */
        xst.alias("orientacao_tcc_em_andamento", OrientacaoTccAndamento.class);
        xst.aliasField("orientacao_tcc_em_andamento", Pesquisador.class, "orientacoesTccAndamento");
        xst.addImplicitCollection(OrientacaoTccAndamento.class, "orientacoesTccAndamento", TccVO.class);
        
        /**
         * Objeto OrientacaoMonografiaEspecializacaoAndamento
         */
        xst.alias("orientacao_monografia_especializacao_em_andamento", OrientacaoMonografiaEspecializacaoAndamento.class);
        xst.aliasField("orientacao_monografia_especializacao_em_andamento", Pesquisador.class, "orientacaoMonograiaEspecializacaoAndamento");
        xst.addImplicitCollection(OrientacaoMonografiaEspecializacaoAndamento.class, "orientacaoMonograiaEspecializacaoAndamento", MonografiaVO.class);
        
        /**
         * Objeto ProcessoOuTecnica
         */
        xst.alias("processo_tecnica", ProcessoOuTecnica.class);
        xst.aliasField("processo_tecnica", Pesquisador.class, "processoTecnica");
        xst.addImplicitCollection(ProcessoOuTecnica.class, "processoTecnica", ProdutoVO.class);
        
        /**
         * Objeto SoftwareSemPatente
         */
        xst.alias("software_sem_patente", SoftwareSemPatente.class);
        xst.aliasField("software_sem_patente", Pesquisador.class, "softwareSemPatente");
        xst.addImplicitCollection(SoftwareSemPatente.class, "softwareSemPatente", SoftwareVO.class);
        
        /**
         * Objeto SoftwareVO
         */
        xst.alias("software", SoftwareVO.class);
        
        
        /**
         * Objeto SoftwareComPatente
         */
        xst.alias("software_com_patente", SoftwareComPatente.class);
        xst.aliasField("software_com_patente", Pesquisador.class, "softwareComPatente");
        xst.addImplicitCollection(SoftwareComPatente.class, "softwareComPatente", SoftwareVO.class);
        
        FileReader xmlfile = new FileReader(this.xmlPathFile);
        BufferedReader buffer = new BufferedReader(xmlfile);
        StringBuilder builder = new StringBuilder();
        String line;
        SimplePreProcessXmlText spt = new SimplePreProcessXmlText();
        while ((line = buffer.readLine()) != null){
            line = spt.preProcessText(line);
            builder.append(line);
        }
        
        CurriculoLattes lattes = (CurriculoLattes)xst.fromXML(builder.toString());
        return lattes;
    }
    
}
