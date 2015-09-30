package br.com.compracombinada.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.compracombinada.BuildConfig;
import br.com.compracombinada.R;
import br.com.compracombinada.activity.MainActivity;

/**
 * Created by bruno on 21/08/14.
 */
public class SucessoMerge extends android.support.v4.app.Fragment {

    private View view;
    private Button btnMinhasCotacoes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sucesso_merge, container, false);

        btnMinhasCotacoes = (Button) view.findViewById(R.id.btnMinhasCotacoes);
        btnMinhasCotacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Cotacoes();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }

}
