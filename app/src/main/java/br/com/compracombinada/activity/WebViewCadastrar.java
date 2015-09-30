package br.com.compracombinada.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import br.com.compracombinada.R;


public class WebViewCadastrar extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_cadastrar);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://ec2-52-21-177-220.compute-1.amazonaws.com/combobox/form_usuario/?tp_cadastro={SITE}");

    }

}
