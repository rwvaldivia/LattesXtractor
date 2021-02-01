# LattesXtractor - Proposta da Ferramenta e Histórico
LattesXtractor is a powerfull tool to collect and organize data from Plataforma Lattes. Plataforma Lattes is a database of brazilian researches.

LattesXtractor é resultado de um projeto de pesquisa de Mestrado Profissional realizado em 2019. Seu objetivo é ter uma ferramenta capaz de obter dados da Plataforma Lattes de forma estruturada e assim realizar qualquer tipo de análise cientométrica. 

Porém o LattesXtractor não funciona como uma ferramenta "all in one". A proposta era tirar proveito de outro motor de software que fosse capaz de realizar o Crawling de informações na Plataforma Lattes e então concentrar os esforços na Análise Quantitiva e/ou Qualitativa dos dados através do processamento da informação

<p align="center">
<img src="https://github.com/rwvaldivia/LattesXtractor/blob/master/LAttesXtractor.png">
</p>

Foi realizado um enorme esforço para delimitar as regras de análise de dados e encontrar uma ferramenta que fosse capaz de obter os dados. A escolha do motor de harvesting de dados foi do ScriptLattes. Essa ferramenta foi projetada para realizar um enorme esforço computacional para ir, de forma eletrônica, até o site Web da Plataforma Lattes e baixar os curriculos lattes disponíveis através de uma parametrização em um arquivo de configurações.

<p align="center">
<img src="https://github.com/rwvaldivia/LattesXtractor/blob/master/InteroperabilidadeLattesXtractor-ScriptLattes.png">
</p>

Porém o ScriptLattes, uma vez que coleta as informações, apresenta os dados tabulados que atende a uma demanda das inúmeras analises cientométricas possíveis. A proposta do LattesXtractor é atender a qualquer tipo de análise, uma vez que ao invés de gerar uma saída única e estática, permite ao programador, através de sua imaginação, realizar qualquer outra análise.

Para que isso fosse possível, eu trabalhei em 2013-2014 na tradução do resultado do ScriptLattes para que houvesse um output dos dados em formato XML, e solicitei Prof. Dr. Jesus Mena (autor da ferramenta) em um Congresso de Bibliometria e Cientometria realizado em Gramado que essa nova feature fosse incluida no código fonte do ScriptLattes. Nesse período a contribuição para a ferramenta teve carater meramente de contribuição para melhoria do ScriptLattes. 

Entretanto após receber diversos pedidos de análise cientométríca das mais variadas fontes, notei uma oportunidade de construir uma ferramenta que atendesse essa demanda. Tirando proveito do XML incluido nos fontes do ScriptLattes, ingresei no curso de Mestrado Profissional da Universidade Federal de São Paulo - UNIFESP apresentando como projeto de pesquisa a construção dessa ferramenta. Como resultado, na pesquisa, além da construção da ferramenta, foi realizado um estudo de caso de Análise de Rede de Pesquisadores baseado nas informações da Plataforma Lattes com um resultado visual baseado em grafos.

<p align="center">
<img src="https://github.com/rwvaldivia/LattesXtractor/blob/master/AnaliseDeRede01.png">
</p>

<p align="center">
<img src="https://github.com/rwvaldivia/LattesXtractor/blob/master/AnaliseDeRede02.png">
</p>


# Links e Resultados

[Link do Repostório de Pesquisa](http://repositorio.unifesp.br/handle/11600/57106)

[Download da Dissertação Mestrado](http://repositorio.unifesp.br/bitstream/handle/11600/57106/Dissertacao_Richard%20William%20Valdivia.pdf?sequence=1&isAllowed=y)

