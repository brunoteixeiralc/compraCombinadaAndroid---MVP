package br.com.compracombinada.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.compracombinada.ListaDetalheCompraColetiva;
import br.com.compracombinada.R;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaAddListaProdutoCotacao;
import br.com.compracombinada.model.Produto;

/**
 * Created by bruno on 09/09/14.
 */
public class DialogFragmentBuscaProduto extends android.support.v4.app.DialogFragment implements TextWatcher {

    private View view;
    private Button btnOk;
    private Button btnCancelar;
    private EditText edtPreco;
    private ImageView foto;
    private Produto produto;
    private EditText edtQuantidade;
    private Fragment fragment;

    public static DialogFragmentBuscaProduto newInstance() {
        DialogFragmentBuscaProduto f = new DialogFragmentBuscaProduto();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_busca_produto, container, false);

        produto = (Produto) getArguments().getSerializable("produto");

        foto = (ImageView) view.findViewById(R.id.foto);
        if(produto.getFoto() != null)
            foto.setImageBitmap(convertBase64Image(produto.getFoto()));

        edtPreco = (EditText) view.findViewById(R.id.reais);
        edtPreco.addTextChangedListener(this);

        edtQuantidade = (EditText) view.findViewById(R.id.quant);

        getDialog().setTitle(produto.getNome());

        btnOk = (Button) view.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getIntent().putExtra("preco", edtPreco.getText().toString());
                getActivity().getIntent().putExtra("quantidade", edtQuantidade.getText().toString());
                getActivity().getIntent().putExtra("fragment", getArguments().getString("fragment"));

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());

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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    private String current = "";

    @Override
    public void onTextChanged(CharSequence s, int i, int i2, int i3) {

        if (!s.toString().equals(current) && !s.toString().isEmpty()) {
            edtPreco.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[R$,.]", "");

            DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));

            BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100));
            String formatted = precoFinal.format(parsed);

            current = formatted;
            edtPreco.setText(current);
            edtPreco.setSelection(formatted.length());

            edtPreco.addTextChangedListener(this);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private Bitmap convertBase64Image(String strFoto){

        byte[] decodedString = Base64.decode(strFoto, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }
}



