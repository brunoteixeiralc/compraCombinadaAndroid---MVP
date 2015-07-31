package br.com.compracombinada.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import br.com.compracombinada.AdicionarProduto;
import br.com.compracombinada.R;
import br.com.compracombinada.adpater.SpinnerAdapter;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaPreferencias;
import br.com.compracombinada.model.Divisao;
import br.com.compracombinada.model.Familia;
import br.com.compracombinada.model.Marca;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.ProdutoPreferencia;
import br.com.compracombinada.model.Produtos;

/**
 * Created by bruno on 09/09/14.
 */
public class DialogFragmentCotacao extends android.support.v4.app.DialogFragment implements TextWatcher {

    private View view;
    private Button btnOk;
    private Button btnCancelar;
    private EditText edtPreco;
    private CheckBox checkNaoContem;
    private ImageView foto;
    private Produtos produtos;
    private Spinner spinnerPreferencia;
    private List<Object> produtoPreferencias;
    private ProdutoPreferencia produtoPreferenciaSelecionado;

    public static DialogFragmentCotacao newInstance() {
        DialogFragmentCotacao f = new DialogFragmentCotacao();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        produtos = (Produtos) getArguments().getSerializable("produto");

        if (produtos.getProduto().getPreferencia() == 1) {

            view = inflater.inflate(R.layout.dialog_cotacao_preferencia, container, false);

            spinnerPreferencia = (Spinner) view.findViewById(R.id.pref);
            spinnerPreferencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    produtoPreferenciaSelecionado = (ProdutoPreferencia) adapterView.getItemAtPosition(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            new AsyncTaskCompraColetivaPreferencias(DialogFragmentCotacao.this).execute(produtos.getProduto().getId());

        }else {

           view = inflater.inflate(R.layout.dialog_cotacao, container, false);
        }

        foto = (ImageView) view.findViewById(R.id.foto);
        if(produtos.getProduto().getFoto() != null)
            foto.setImageBitmap(convertBase64Image(produtos.getProduto().getFoto()));

        edtPreco = (EditText) view.findViewById(R.id.reais);
        edtPreco.addTextChangedListener(this);

        checkNaoContem = (CheckBox) view.findViewById(R.id.chkNaoContem);
        checkNaoContem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkNaoContem.isChecked())
                    edtPreco.setEnabled(false);
                else
                    edtPreco.setEnabled(true);

            }
        });

        getDialog().setTitle(produtos.getProduto().getNome());

        btnOk = (Button) view.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getIntent().putExtra("preco", edtPreco.getText().toString());
                getActivity().getIntent().putExtra("naoContem", checkNaoContem.isChecked());
                if(produtos.getProduto().getPreferencia() == 1)
                    getActivity().getIntent().putExtra("produtoPreferencia", produtoPreferenciaSelecionado);

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


    public void retornoAsyncTaskCompraCombinadaPrefvivierencias(String jsonString){

        produtoPreferencias = new ArrayList<Object>();

        Gson gson = new Gson();
        produtoPreferencias = gson.fromJson(jsonString, new TypeToken<List<ProdutoPreferencia>>() {
        }.getType());

        SpinnerAdapter spinnerAdpPreferencia = new SpinnerAdapter(produtoPreferencias,DialogFragmentCotacao.this.getActivity(), SpinnerEnum.PREFERENCIA);
        spinnerPreferencia.setAdapter(spinnerAdpPreferencia);

    }
}



