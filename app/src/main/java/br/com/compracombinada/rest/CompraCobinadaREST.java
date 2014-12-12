package br.com.compracombinada.rest;


import br.com.compracombinada.R;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.util.Utils;

public class CompraCobinadaREST {

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
}
