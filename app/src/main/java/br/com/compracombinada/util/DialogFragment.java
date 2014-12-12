package br.com.compracombinada.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.compracombinada.ListaDetalheCompraColetiva;
import br.com.compracombinada.R;
import br.com.compracombinada.adpater.ListAdapterProdutosCompraColetiva;

/**
 * Created by bruno on 09/09/14.
 */
public class DialogFragment extends android.support.v4.app.DialogFragment implements TextWatcher {

    private View view;
    private Button btnOk;
    private Button btnCancelar;
    private EditText edtPreco;
    private CheckBox checkNaoContem;

    public static DialogFragment newInstance() {
        DialogFragment f = new DialogFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog, container, false);

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

        getDialog().setTitle("Compra Coletiva");

        btnOk = (Button) view.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getIntent().putExtra("preco", edtPreco.getText().toString());
                getActivity().getIntent().putExtra("naoContem", checkNaoContem.isChecked());

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

        if (!s.toString().equals(current)) {
            edtPreco.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[R$,.]", "");

            Locale locale = new Locale("pt", "BR");
            NumberFormat nf = NumberFormat.getNumberInstance(locale);

            BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
            String formatted = nf.format(parsed);

            current = formatted;
            edtPreco.setText(current);
            edtPreco.setSelection(formatted.length());

            edtPreco.addTextChangedListener(this);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}



