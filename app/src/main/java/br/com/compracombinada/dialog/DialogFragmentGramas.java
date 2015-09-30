package br.com.compracombinada.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import br.com.compracombinada.R;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaGramas;
import br.com.compracombinada.model.Produtos;

/**
 * Created by bruno on 09/09/14.
 */
public class DialogFragmentGramas extends android.support.v4.app.DialogFragment {

    private View view;
    private Button btnOk;
    private Button btnCancelar;
    private EditText edtQuant;
    private Produtos produtos;
    private List<Object> produtoPreferencias;

    public static DialogFragmentGramas newInstance() {
        DialogFragmentGramas f = new DialogFragmentGramas();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        produtos = (Produtos) getArguments().getSerializable("produto");

        view = inflater.inflate(R.layout.dialog_cotacao_gramas, container, false);

        edtQuant = (EditText) view.findViewById(R.id.quant);

        btnOk = (Button) view.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calculoKG(Integer.parseInt(edtQuant.getText().toString()),produtos.getPrecoKG());

                if (produtos.getPreco() != null)
                    produtos.setPreco(produtos.getPreco().replace(",", "."));

                if(produtos.getPrecoKG() != null){
                    produtos.setPrecoKG(produtos.getPrecoKG().replace(",","."));
                }

                new AsyncTaskCompraColetivaGramas(DialogFragmentGramas.this).execute(produtos);

            }
        });

        btnCancelar = (Button) view.findViewById(R.id.btnCancel);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
            }
        });

        return view;

    }


    public void retornoAsyncTaskCompraCombinadaNovaMedida(String jsonString){

        getActivity().getIntent().putExtra("quantidade", edtQuant.getText().toString());
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());

    }

    private void calculoKG(int quantGramas, String precoKG){

        Double precoKGFormat = Double.parseDouble(precoKG.replace(",", "."));
        Double calculoFinal = (precoKGFormat * quantGramas)/1000;

        DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));
        String formatted = precoFinal.format(calculoFinal);

        produtos.setQuantidade(quantGramas);
        produtos.setPreco(formatted);

    }
}



