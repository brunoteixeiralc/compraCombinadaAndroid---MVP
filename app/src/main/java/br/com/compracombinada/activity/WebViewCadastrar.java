package br.com.compracombinada.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.compracombinada.R;


public class WebViewCadastrar extends Activity {

    private TextView txt_cadastro;
 //   private WebView webView;
 //   private ProgressBar progressBar;
 //   private static final String URL = "http://ec2-52-21-177-220.compute-1.amazonaws.com/combobox/form_usuario/?tp_cadastro={SITE}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_cadastrar);
        txt_cadastro = (TextView) findViewById(R.id.txt_cadastro);
        txt_cadastro.setText(Html.fromHtml(getString(R.string.txt_cadastro)));
//        progressBar = (ProgressBar)findViewById(R.id.progress);
//        webView = (WebView) findViewById(R.id.webView);
//        setWebViewClient(webView);
//        webView.loadUrl(URL);

    }

//    private void setWebViewClient(WebView webview) {
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView webview, String url, Bitmap favicon) {
//                super.onPageStarted(webview, url, favicon);
//                // Liga o progress
//                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView webview, String url) {
//                // Desliga o progress
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//        });
//
//    }
}
