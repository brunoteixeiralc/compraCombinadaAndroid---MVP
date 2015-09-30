package br.com.compracombinada.rest;


import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.Solicitacoes;
import br.com.compracombinada.utils.Utils;

public class CompraCobinadaREST {


    public String getConfiguracaoCompraCombinada() throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/configuracao";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getProdutosCompraCombinda() throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/obterAllProdutos";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getMarcasCompraCombinda() throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/obterMarca";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getFamiliasCompraCombinda() throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/obterFamilia";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getDivisaoCompraCombinda() throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/obterDivisao";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getConfiguracaoCompraCombinadaAtualizar(br.com.compracombinada.model.Configuracao configuracao) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/configuracaoAtualizar";
        String[] resposta = new WebServiceCompraCombinada().post(URL_WS, configuracao);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }


    public String getUsuarioCompraCombinada(String login, String senha) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/usuario/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + login + "/" + senha);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getCotacoesUsuario(Integer usuarioId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/cotacao/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + usuarioId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getEventosConvidados(Integer usuarioId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/evento/convidado/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + usuarioId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getEventosUsuario(Integer usuarioId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/evento/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + usuarioId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getEventosFinalizadosUsuario(Integer usuarioId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/eventoFinalizado/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + usuarioId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getAmizadeUsuario(Integer usuarioId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/amizade/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + usuarioId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getListaUsuario(Integer usuarioId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/lista/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + usuarioId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getListaCotacaoUsuario(Integer usuarioId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/listaCotacaoUsuario/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + usuarioId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getProdutosEmFalta(Integer eventoId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/listaCotacao/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + eventoId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getPreferenciasProduto(Integer produtoId) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/preferencia/";
        String[] resposta = new WebServiceCompraCombinada().get(URL_WS + produtoId);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }


    public String addCotacaoCompraCombinada(Cotacao cotacao) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/addCotacao";
        String[] resposta = new WebServiceCompraCombinada().post(URL_WS, cotacao);
        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String validaCotacao(Evento evento) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/validarCotacao/";
        String[] resposta = new WebServiceCompraCombinada().post(URL_WS, evento);
        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getSolicitacoesCompraCombinada(Solicitacoes solicitacoes) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/solicitacoes";
        String[] resposta = new WebServiceCompraCombinada().post(URL_WS, solicitacoes);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getAddProdutoCompraCombinada(Produto produto) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/addProduto";
        String[] resposta = new WebServiceCompraCombinada().post(URL_WS, produto);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getUpdateProdutoCotacao(Produtos produtos) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/updateListaProdutoCotacao";
        String[] resposta = new WebServiceCompraCombinada().post(URL_WS, produtos);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }

    public String getAddListaProdutoCotacao(Produtos produtos) throws Exception {

        String URL_WS = Utils.ip.toString() + "/REST/compracombinada/addListaProdutoCotacao";
        String[] resposta = new WebServiceCompraCombinada().post(URL_WS, produtos);

        if (resposta[0].equals("200")) {
            return resposta[1];

        } else {
            throw new Exception(resposta[1]);
        }
    }


}
