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
package br.xtractor.lattes.vo;

import br.xtractor.lattes.core.ApresentacaoTrabalho;
import br.xtractor.lattes.core.AreaAtuacao;
import br.xtractor.lattes.core.ArtigosEmPeriodicos;
import br.xtractor.lattes.core.ArtigosEmRevistas;
import br.xtractor.lattes.core.CapitulosLivros;
import br.xtractor.lattes.core.Colaboradores;
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
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author richardv
 */
public class Pesquisador implements Serializable {
    private int internalId;

    private String id;
    private IdentificacaoVO identificacao;
    private Idiomas idiomas;
    private EnderecoVO endereco;
    private FormacaoAcademica formacaoAcademica;
    private ProjetosPesquisa projetosPesquisa;
    private AreaAtuacao areaAtuacao;
    private PremiosTitulos premiosTitulos;
    private ArtigosEmPeriodicos artigosEmPeriodicos;
    private TextosEmJornal textosEmJornal;
    private TrabalhoCompletoCongresso trabalhoCompletoCongresso;
    private ResumoCongressoExpandido resumoCongressoExpandido;
    private ResumoCongresso resumoCongresso;
    private ArtigosEmRevistas artigosEmRevista;
    private ApresentacaoTrabalho trabalhosApresentados;
    private ParticipacaoEvento participacaoEventos;
    private OrganizacaoEvento organizacaoEventos; //################ verificar o objeto evento
    private CapitulosLivros capitulosLivros;
    private OrientacaoMestradoAndamento orientacaoMestradoEmAndamento;
    private LivrosPublicados livrosPublicados;
    private ProducaoTecnica producaoTecnica;
    private OrientacaoDoutoradoConcluido orientacoesDoutoradoConcluidos;
    private OrientacaoMestradoConcluido orientacoesMestradoConcluidos;
    private OrientacaoEspecializacaoConcluida orientacoesEspecializacaoConcluidos;
    private OrientacaoDoutoradoAndamento orientacoesDoutoradoEmAndamento;
    private OrientacaoIniciacaoCientificaAndamento orientacoesIniciacaoCientificaAndamento;
    private OrientacaoIniciacaoCientificaConcluida orientacoesIniciacaoCientificaConcluida;
    private ProducaoBibliografica producoesBibliograficas;
    private TrabalhosTecnicos trabalhosTecnicos;
    private OrientacaoTccConcluida orientacoesTccConcluidas;
    private OrientacaoOutrosTiposConcluida orientacoesOutrosTiposConcluida;
    private ProdutoTecnologico produtoTecnologico;
    private SupervisaoPosDoutoradoAndamento supervisoesDoutAndamento;
    private SupervisaoPosDoutoradoConcluido supervisaoPosDoutConcluido;
    private ProducaoArtistica producaoArtistica;
    private OrientacaoOutrosTiposAndamento orientacaoOutrosTiposAndamento;
    private OrientacaoTccAndamento orientacoesTccAndamento;
    private Colaboradores colaboradores;
    private OrientacaoMonografiaEspecializacaoAndamento orientacaoMonograiaEspecializacaoAndamento;
    private ProcessoOuTecnica processoTecnica;
    private SoftwareComPatente softwareComPatente;
    private SoftwareSemPatente softwareSemPatente;
    
    public int getInternalId() {
        return internalId;
    }

    public void setInternalId(int internalId) {
        this.internalId = internalId;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public SoftwareComPatente getSoftwareComPatente() {
        return softwareComPatente;
    }
    
    public SoftwareVO []  getSoftwareComPatenteVO() {
        SoftwareVO [] vo = null;
        
        if (this.softwareComPatente != null && this.softwareComPatente.getSoftwareComPatente() != null){
            vo = this.softwareComPatente.getSoftwareComPatente().toArray(new SoftwareVO [0]);
        }
        
        return vo;
    }

    public void setSoftwareComPatente(SoftwareComPatente softwareComPatente) {
        this.softwareComPatente = softwareComPatente;
    }

    public SoftwareSemPatente getSoftwareSemPatente() {
        return softwareSemPatente;
    }
    
    public SoftwareVO []  getSoftwareSemPatenteVO() {
        SoftwareVO [] vo = null;
        
        if (this.softwareSemPatente != null && this.softwareSemPatente.getSoftwareSemPatente() != null){
            vo = this.softwareSemPatente.getSoftwareSemPatente().toArray(new SoftwareVO [0]);
        }
        
        return vo;
    }
    
    

    public void setSoftwareSemPatente(SoftwareSemPatente softwareSemPatente) {
        this.softwareSemPatente = softwareSemPatente;
    }
    
    public ProcessoOuTecnica getProcessoTecnica() {
        return processoTecnica;
    }
    
    public ProdutoVO [] getProcessoTecnicaVO() {
        ProdutoVO [] vo = null;
        if (this.processoTecnica != null && this.processoTecnica.getProcessoTecnica() != null){
            vo = this.processoTecnica.getProcessoTecnica().toArray(new ProdutoVO[0]);
        }
        return vo;
    }

    public void setProcessoTecnica(ProcessoOuTecnica processoTecnica) {
        this.processoTecnica = processoTecnica;
    }
    
    public OrientacaoMonografiaEspecializacaoAndamento getOrientacaoMonograiaEspecializacaoAndamento() {
        return orientacaoMonograiaEspecializacaoAndamento;
    }
    
    public MonografiaVO [] getOrientacaoMonograiaEspecializacaoAndamentoVO() {
        MonografiaVO [] vo = null;
        if (this.orientacaoMonograiaEspecializacaoAndamento != null && this.orientacaoMonograiaEspecializacaoAndamento.getOrientacaoMonograiaEspecializacaoAndamento() != null){
            vo = this.orientacaoMonograiaEspecializacaoAndamento.getOrientacaoMonograiaEspecializacaoAndamento().toArray(new MonografiaVO [0]);
        }
        return vo;
    }

    public void setOrientacaoMonograiaEspecializacaoAndamento(OrientacaoMonografiaEspecializacaoAndamento orientacaoMonograiaEspecializacaoAndamento) {
        this.orientacaoMonograiaEspecializacaoAndamento = orientacaoMonograiaEspecializacaoAndamento;
    }
    

    public Colaboradores getColaboradores() {
        return colaboradores;
    }
    
    public String [] getColaboradoresStringArray() {
        String [] colabArray = null;
        
        if (this.colaboradores != null) {
            if (this.colaboradores.getColaboradores() != null){
                colabArray = this.colaboradores.getColaboradores().toArray(new String[0]);
            }
        }
        
        return colabArray;
    }
    

    public void setColaboradores(Colaboradores colaboradores) {
        this.colaboradores = colaboradores;
    }
    
    public OrientacaoTccAndamento getOrientacoesTccAndamento() {
        return orientacoesTccAndamento;
    }
    
    public TccVO []  getOrientacoesTccAndamentoVO() {
        
        TccVO [] vo = null;
        if (this.orientacoesTccAndamento != null && this.orientacoesTccAndamento.getOrientacoesTccAndamento() != null){
            vo = this.orientacoesTccAndamento.getOrientacoesTccAndamento().toArray(new TccVO [0] );
        }
        
        return vo;
    }
    
    public void setOrientacoesTccAndamento(OrientacaoTccAndamento orientacoesTccAndamento) {
        this.orientacoesTccAndamento = orientacoesTccAndamento;
    }
    
    public OrientacaoOutrosTiposAndamento getOrientacaoOutrosTiposAndamento() {
        return orientacaoOutrosTiposAndamento;
    }
    
    public OrientacaoOutrosTiposVO [] getOrientacaoOutrosTiposAndamentoVO() {
        OrientacaoOutrosTiposVO [] vo = null;
        if (this.orientacaoOutrosTiposAndamento != null && this.orientacaoOutrosTiposAndamento.getOrientacaoOutrosTiposAndamento() != null){
            vo = this.orientacaoOutrosTiposAndamento.getOrientacaoOutrosTiposAndamento().toArray(new OrientacaoOutrosTiposVO[0]);
        }
        return vo;
    }

    public void setOrientacaoOutrosTiposAndamento(OrientacaoOutrosTiposAndamento orientacaoOutrosTiposAndamento) {
        this.orientacaoOutrosTiposAndamento = orientacaoOutrosTiposAndamento;
    }
    

    public ProducaoArtistica getProducaoArtistica() {
        return producaoArtistica;
    }

    public void setProducaoArtistica(ProducaoArtistica producaoArtistica) {
        this.producaoArtistica = producaoArtistica;
    }
    

    public IdentificacaoVO getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(IdentificacaoVO identificacao) {
        this.identificacao = identificacao;
    }

    public Idiomas getIdiomas() {
        return idiomas;
    }
    
    public IdiomaVO [] getIdiomasVO(){
        IdiomaVO [] idiom = null;
        if (this.idiomas != null){
            idiom = this.idiomas.getIdiomas().toArray(new IdiomaVO[0]);
        }
        return idiom;
    }

    public void setIdiomas(Idiomas idiomas) {
        this.idiomas = idiomas;
    }

    public EnderecoVO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoVO endereco) {
        this.endereco = endereco;
    }

    public FormacaoAcademica getFormacaoAcademica() {
        return formacaoAcademica;
    }
    
    public FormacaoVO [] getFormacaoAcademicaVO() {
        
        List<FormacaoVO> list = this.formacaoAcademica.getFormacao();
        
        if (list == null){
            return null;
        }
        
        return list.toArray(new FormacaoVO[0]);
        
    }

    public void setFormacaoAcademica(FormacaoAcademica formacaoAcademica) {
        this.formacaoAcademica = formacaoAcademica;
    }
    

    public ProjetosPesquisa getProjetosPesquisa() {
        return projetosPesquisa;
    }
    
    public ProjetoVO [] getProjetosPesquisaVO(){
        if (this.projetosPesquisa == null){
            return null;
        }
        
        List <ProjetoVO> list = this.projetosPesquisa.getProjeto();
        
        if (list == null) {
            return null;
        }
        
        return list.toArray(new ProjetoVO[0]);
    }

    public void setProjetosPesquisa(ProjetosPesquisa projetosPesquisa) {
        this.projetosPesquisa = projetosPesquisa;
    }

    public AreaAtuacao getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(AreaAtuacao areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public PremiosTitulos getPremiosTitulos() {
        return premiosTitulos;
    }

    public void setPremiosTitulos(PremiosTitulos premiosTitulos) {
        this.premiosTitulos = premiosTitulos;
    }
    
    public ArtigosEmPeriodicos getArtigosEmPeriodicos() {
        return artigosEmPeriodicos;
    }
    
    public ArtigoVO [] getArtigosEmPeriodicosVO(){
        if (this.artigosEmPeriodicos  != null){
            if (this.artigosEmPeriodicos.getArtigosEmPeriodicos() != null) {
                return this.artigosEmPeriodicos.getArtigosEmPeriodicos().toArray(new ArtigoVO[0]);
            }
            return null;
        }
        return null;
    }

    public void setArtigosEmPeriodicos(ArtigosEmPeriodicos artigosEmPeriodicos) {
        this.artigosEmPeriodicos = artigosEmPeriodicos;
    }

    public TextosEmJornal getTextosEmJornal() {
        return textosEmJornal;
    }
    
    public TextoJornalVO [] getTextosEmJornalVO(){
        TextoJornalVO [] vo = null;
        if (this.textosEmJornal != null && this.textosEmJornal.getTextosEmJornal() != null){
            vo = this.textosEmJornal.getTextosEmJornal().toArray(new TextoJornalVO[0]);
        }
        return vo;
    }
    

    public void setTextosEmJornal(TextosEmJornal textosEmJornal) {
        this.textosEmJornal = textosEmJornal;
    }

    public TrabalhoCompletoCongresso getTrabalhoCompletoCongresso() {
        return trabalhoCompletoCongresso;
    }
    
    public TrabalhoCompletoVO [] getTrabalhoCompletoCongressoVO(){
        TrabalhoCompletoVO [] vo = null;
        if (this.trabalhoCompletoCongresso != null && this.trabalhoCompletoCongresso.getTrabalhoCompletoCongresso() != null){
            vo = this.trabalhoCompletoCongresso.getTrabalhoCompletoCongresso().toArray(new TrabalhoCompletoVO [0] );
        }
        
        return vo;
    }
    

    public void setTrabalhoCompletoCongresso(TrabalhoCompletoCongresso trabalhoCompletoCongresso) {
        this.trabalhoCompletoCongresso = trabalhoCompletoCongresso;
    }

    public ResumoCongressoExpandido getResumoCongressoExpandido() {
        return resumoCongressoExpandido;
    }
    
    public ResumoCongressoExpandidoVO [] getResumoCongressoExpandidoVO() {
        ResumoCongressoExpandidoVO [] vo = null;
        if (this.resumoCongressoExpandido != null && this.resumoCongressoExpandido.getResumoCongressoExpandido() != null){
            vo = this.resumoCongressoExpandido.getResumoCongressoExpandido().toArray(new ResumoCongressoExpandidoVO[0]);
        }
        return vo;
    }
    
    public void setResumoCongressoExpandido(ResumoCongressoExpandido resumoCongressoExpandido) {
        this.resumoCongressoExpandido = resumoCongressoExpandido;
    }

    public ResumoCongresso getResumoCongresso() {
        return resumoCongresso;
    }
    
    public ResumoCongressoVO [] getResumoCongressoVO(){
        ResumoCongressoVO [] vo = null;
        if (this.resumoCongresso != null && this.resumoCongresso.getResumoCongresso() != null){
            vo = this.resumoCongresso.getResumoCongresso().toArray(new ResumoCongressoVO [0] );
        }
        return vo;
    }
    
    public void setResumoCongresso(ResumoCongresso resumoCongresso) {
        this.resumoCongresso = resumoCongresso;
    }

    public ArtigosEmRevistas getArtigosEmRevista() {
        return artigosEmRevista;
    }
    
    public ArtigoRevistaVO [] getArtigosEmRevistaVO() {
        ArtigoRevistaVO [] vo = null;
        if (this.artigosEmRevista != null && this.artigosEmRevista.getArtigosEmRevistas() != null){
            vo = this.artigosEmRevista.getArtigosEmRevistas().toArray(new ArtigoRevistaVO[0]);
        }
        return vo;
    }
    
    public void setArtigosEmRevista(ArtigosEmRevistas artigosEmRevista) {
        this.artigosEmRevista = artigosEmRevista;
    }

    public ApresentacaoTrabalho getTrabalhosApresentados() {
        return trabalhosApresentados;
    }
    
    public TrabalhoApresentadoVO [] getTrabalhosApresentadosVO() {
        TrabalhoApresentadoVO [] vo = null;
        if (this.trabalhosApresentados != null && this.trabalhosApresentados.getTrabalhosApresentados() != null){
            vo = this.trabalhosApresentados.getTrabalhosApresentados().toArray(new TrabalhoApresentadoVO[0]);
        
        }
        return vo;
        
    }

    public void setTrabalhosApresentados(ApresentacaoTrabalho trabalhosApresentados) {
        this.trabalhosApresentados = trabalhosApresentados;
    }

    public ParticipacaoEvento getParticipacaoEventos() {
        return participacaoEventos;
    }

    public void setParticipacaoEventos(ParticipacaoEvento participacaoEventos) {
        this.participacaoEventos = participacaoEventos;
    }

    public OrganizacaoEvento getOrganizacaoEventos() {
        return organizacaoEventos;
    }
    
    public EventoVO [] getOrganizacaoEventosVO(){
        EventoVO [] vo = null;
        
        if (this.organizacaoEventos != null && this.organizacaoEventos.getOrganizacaoEventos() != null){
            vo = this.organizacaoEventos.getOrganizacaoEventos().toArray(new EventoVO[0]);
        }
        
        return vo;
    
    }

    public void setOrganizacaoEventos(OrganizacaoEvento organizacaoEventos) {
        this.organizacaoEventos = organizacaoEventos;
    }

    public CapitulosLivros getCapitulosLivros() {
        return capitulosLivros;
    }
    
    public CapitulosLivrosVO [] getCapitulosLivrosVO(){
        
        CapitulosLivrosVO [] capitulos = null;
        if (this.capitulosLivros != null && this.capitulosLivros.getCapitulosLivros() != null){
            capitulos = this.capitulosLivros.getCapitulosLivros().toArray(new CapitulosLivrosVO[0]);
        }    
        return capitulos;
    }

    public void setCapitulosLivros(CapitulosLivros capitulosLivros) {
        this.capitulosLivros = capitulosLivros;
    }

    public OrientacaoMestradoAndamento getOrientacaoMestradoEmAndamento() {
        return orientacaoMestradoEmAndamento;
    }
    
    public DissertacaoVO [] getOrientacaoMestradoEmAndamentoVO() {
        
        DissertacaoVO [] vo = null;
        if (this.orientacaoMestradoEmAndamento != null && this.orientacaoMestradoEmAndamento.getOrientacaoMestradoAndamento() !=null){
            vo = this.orientacaoMestradoEmAndamento.getOrientacaoMestradoAndamento().toArray(new DissertacaoVO[0]);
        }
        return vo;
    }

    public void setOrientacaoMestradoEmAndamento(OrientacaoMestradoAndamento orientacaoMestradoEmAndamento) {
        this.orientacaoMestradoEmAndamento = orientacaoMestradoEmAndamento;
    }

    public LivrosPublicados getLivrosPublicados() {
        return livrosPublicados;
    }

    public void setLivrosPublicados(LivrosPublicados livrosPublicados) {
        this.livrosPublicados = livrosPublicados;
    }

    public ProducaoTecnica getProducaoTecnica() {
        return producaoTecnica;
    }
    
    public ProducaoVO [] getProducaoTecnicaVO() {
        ProducaoVO [] vo = null;
        if (this.producaoTecnica != null && this.producaoTecnica.getProducaoTecnica() != null){
            vo = this.producaoTecnica.getProducaoTecnica().toArray(new ProducaoVO[0]);
        }
        return vo;
    }

    public void setProducaoTecnica(ProducaoTecnica producaoTecnica) {
        this.producaoTecnica = producaoTecnica;
    }

    public OrientacaoDoutoradoConcluido getOrientacoesDoutoradoConcluidos() {
        return orientacoesDoutoradoConcluidos;
    }
    
    public TeseVO []  getOrientacoesDoutoradoConcluidosVO() {
        
        TeseVO [] vo = null;
        if (this.orientacoesDoutoradoConcluidos != null && this.orientacoesDoutoradoConcluidos.getOrientacoesDoutoradoConcluidos() != null){
            vo = this.orientacoesDoutoradoConcluidos.getOrientacoesDoutoradoConcluidos().toArray(new TeseVO [0]);
        }
        
        return vo;
    }
    
    public void setOrientacoesDoutoradoConcluidos(OrientacaoDoutoradoConcluido orientacoesDoutoradoConcluidos) {
        this.orientacoesDoutoradoConcluidos = orientacoesDoutoradoConcluidos;
    }

    public OrientacaoMestradoConcluido getOrientacoesMestradoConcluidos() {
        return orientacoesMestradoConcluidos;
    }
    
    public DissertacaoVO [] getOrientacoesMestradoConcluidosVO() {
        DissertacaoVO [] vo = null;
        if (this.orientacoesMestradoConcluidos != null && this.orientacoesMestradoConcluidos.getOrientacoesMestradoConcluidos() != null){
            vo = this.orientacoesMestradoConcluidos.getOrientacoesMestradoConcluidos().toArray(new DissertacaoVO[0]);
        }
        return vo;
    }

    public void setOrientacoesMestradoConcluidos(OrientacaoMestradoConcluido orientacoesMestradoConcluidos) {
        this.orientacoesMestradoConcluidos = orientacoesMestradoConcluidos;
    }

    public OrientacaoEspecializacaoConcluida getOrientacoesEspecializacaoConcluidos() {
        return orientacoesEspecializacaoConcluidos;
    }
    
    public MonografiaVO [] getOrientacoesEspecializacaoConcluidosVO() {
        MonografiaVO [] vo = null;
        if (this.orientacoesEspecializacaoConcluidos != null && this.orientacoesEspecializacaoConcluidos.getOrientacoesEspecializacaoConcluidos() != null){
            vo = this.orientacoesEspecializacaoConcluidos.getOrientacoesEspecializacaoConcluidos().toArray(new MonografiaVO [0]);
        }
        return vo;
    }

    public void setOrientacoesEspecializacaoConcluidos(OrientacaoEspecializacaoConcluida orientacoesEspecializacaoConcluidos) {
        this.orientacoesEspecializacaoConcluidos = orientacoesEspecializacaoConcluidos;
    }

    public OrientacaoDoutoradoAndamento getOrientacoesDoutoradoEmAndamento() {
        return orientacoesDoutoradoEmAndamento;
    }
    
    public TeseVO [] getOrientacoesDoutoradoEmAndamentoVO() {
        TeseVO [] vo = null;
        if (this.orientacoesDoutoradoEmAndamento != null && this.orientacoesDoutoradoEmAndamento.getOrientacoesDoutoradoEmAndamento() != null){
            vo = this.orientacoesDoutoradoEmAndamento.getOrientacoesDoutoradoEmAndamento().toArray(new TeseVO[0]);
        }
        
        return vo;
    }

    public void setOrientacoesDoutoradoEmAndamento(OrientacaoDoutoradoAndamento orientacoesDoutoradoEmAndamento) {
        this.orientacoesDoutoradoEmAndamento = orientacoesDoutoradoEmAndamento;
    }

    public OrientacaoIniciacaoCientificaAndamento getOrientacoesIniciacaoCientificaAndamento() {
        return orientacoesIniciacaoCientificaAndamento;
    }
    
    public IniciacaoCientificaVO [] getOrientacoesIniciacaoCientificaAndamentoVO() {
        IniciacaoCientificaVO [] vo = null;
        if (this.orientacoesIniciacaoCientificaAndamento != null && this.orientacoesIniciacaoCientificaAndamento.getOrientacoesIniciacaoCientificaAndamento() != null){
            vo = this.orientacoesIniciacaoCientificaAndamento.getOrientacoesIniciacaoCientificaAndamento().toArray(new IniciacaoCientificaVO [0]);
        }
        return vo;
    }

    public void setOrientacoesIniciacaoCientificaAndamento(OrientacaoIniciacaoCientificaAndamento orientacoesIniciacaoCientificaAndamento) {
        this.orientacoesIniciacaoCientificaAndamento = orientacoesIniciacaoCientificaAndamento;
    }

    public OrientacaoIniciacaoCientificaConcluida getOrientacoesIniciacaoCientificaConcluida() {
        return orientacoesIniciacaoCientificaConcluida;
    }
    
    public  IniciacaoCientificaVO [] getOrientacoesIniciacaoCientificaConcluidaVO() {
        IniciacaoCientificaVO [] vo = null;
        if (this.orientacoesIniciacaoCientificaConcluida != null && this.orientacoesIniciacaoCientificaConcluida.getOrientacoesIniciacaoCientificaConcluida() != null){
            vo = this.orientacoesIniciacaoCientificaConcluida.getOrientacoesIniciacaoCientificaConcluida().toArray(new IniciacaoCientificaVO[0]);
        }
        return vo;
    }

    public void setOrientacoesIniciacaoCientificaConcluida(OrientacaoIniciacaoCientificaConcluida orientacoesIniciacaoCientificaConcluida) {
        this.orientacoesIniciacaoCientificaConcluida = orientacoesIniciacaoCientificaConcluida;
    }

    public ProducaoBibliografica getProducoesBibliograficas() {
        return producoesBibliograficas;
    }
    
    public ProducaoVO [] getProducoesBibliograficasVO() {
        ProducaoVO [] vo = null;
        if (this.producoesBibliograficas != null && this.producoesBibliograficas.getProducoesBibliograficas() != null){
            vo = this.producoesBibliograficas.getProducoesBibliograficas().toArray(new ProducaoVO[0]);
        }
        return vo;
    }

    public void setProducoesBibliograficas(ProducaoBibliografica producoesBibliograficas) {
        this.producoesBibliograficas = producoesBibliograficas;
    }

    public TrabalhosTecnicos getTrabalhosTecnicos() {
        return trabalhosTecnicos;
    }
    
    public TrabalhoTecnicoVO [] getTrabalhosTecnicosVO() {
        TrabalhoTecnicoVO [] vo = null;
        if (this.trabalhosTecnicos != null && this.trabalhosTecnicos.getTrabalhosTecnicos() != null){
            vo = this.trabalhosTecnicos.getTrabalhosTecnicos().toArray(new TrabalhoTecnicoVO[0]);
        }
        return vo;
    }

    public void setTrabalhosTecnicos(TrabalhosTecnicos trabalhosTecnicos) {
        this.trabalhosTecnicos = trabalhosTecnicos;
    }

    public OrientacaoTccConcluida getOrientacoesTccConcluidas() {
        return orientacoesTccConcluidas;
    }
    
    public TccVO []  getOrientacoesTccConcluidasVO() {
        TccVO [] vo = null;
        
        if (this.orientacoesTccConcluidas != null && this.orientacoesTccConcluidas.getOrientacoesTccConcluidas() != null){
            vo = this.orientacoesTccConcluidas.getOrientacoesTccConcluidas().toArray(new TccVO [0]);
        }
        
        return vo;
    }

    public void setOrientacoesTccConcluidas(OrientacaoTccConcluida orientacoesTccConcluidas) {
        this.orientacoesTccConcluidas = orientacoesTccConcluidas;
    }

    public OrientacaoOutrosTiposConcluida getOrientacoesOutrosTiposConcluida() {
        return orientacoesOutrosTiposConcluida;
    }
    
    public OrientacaoOutrosTiposVO [] getOrientacoesOutrosTiposConcluidaVO() {
        OrientacaoOutrosTiposVO [] vo = null;
        if (this.orientacoesOutrosTiposConcluida != null && this.orientacoesOutrosTiposConcluida.getOrientacoesOutrosTiposConcluida() != null){
            vo = this.orientacoesOutrosTiposConcluida.getOrientacoesOutrosTiposConcluida().toArray(new OrientacaoOutrosTiposVO [0]);
        }
        return vo;
    }

    public void setOrientacoesOutrosTiposConcluida(OrientacaoOutrosTiposConcluida orientacoesOutrosTiposConcluida) {
        this.orientacoesOutrosTiposConcluida = orientacoesOutrosTiposConcluida;
    }

    public ProdutoTecnologico getProdutoTecnologico() {
        return produtoTecnologico;
    }
    
    public ProdutoVO [] getProdutoTecnologicoVO() {
        ProdutoVO [] vo = null;
        if (this.produtoTecnologico != null && this.produtoTecnologico.getProdutoTecnologico() != null){
            vo = this.produtoTecnologico.getProdutoTecnologico().toArray(new ProdutoVO[0]);
        }
        return vo;
        
        
    }

    public void setProdutoTecnologico(ProdutoTecnologico produtoTecnologico) {
        this.produtoTecnologico = produtoTecnologico;
    }

    public SupervisaoPosDoutoradoAndamento getSupervisoesDoutAndamento() {
        return supervisoesDoutAndamento;
    }
    
    public SupervisaoVO [] getSupervisoesDoutAndamentoVO(){
        SupervisaoVO [] vo = null;
        if (this.supervisoesDoutAndamento != null && this.supervisaoPosDoutConcluido.getSupervisaoPosDoutConcluido() != null){
            this.supervisaoPosDoutConcluido.getSupervisaoPosDoutConcluido().toArray(new SupervisaoVO[0]);
        }
        return vo;
    }

    public void setSupervisoesDoutAndamento(SupervisaoPosDoutoradoAndamento supervisoesDoutAndamento) {
        this.supervisoesDoutAndamento = supervisoesDoutAndamento;
    }

    public SupervisaoPosDoutoradoConcluido getSupervisaoPosDoutConcluido() {
        return supervisaoPosDoutConcluido;
    }
    
    public SupervisaoVO [] getSupervisaoPosDoutConcluidoVO() {
        SupervisaoVO [] vo = null;
        if (this.supervisaoPosDoutConcluido != null && this.supervisaoPosDoutConcluido.getSupervisaoPosDoutConcluido() != null){
            vo = this.supervisaoPosDoutConcluido.getSupervisaoPosDoutConcluido().toArray(new SupervisaoVO[0]);
        }
        return vo;
    }
    
    public void setSupervisaoPosDoutConcluido(SupervisaoPosDoutoradoConcluido supervisaoPosDoutConcluido) {
        this.supervisaoPosDoutConcluido = supervisaoPosDoutConcluido;
    }
}
