package br.com.compracombinada.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.compracombinada.BuildConfig;
import br.com.compracombinada.R;
import br.com.compracombinada.activity.MainActivity;

/**
 * Created by bruno on 21/08/14.
 */
public class Sobre extends android.support.v4.app.Fragment {

    private View view;
    private TextView versao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sobre, container, false);

        versao = (TextView) view.findViewById(R.id.versao);

        versao.setText("Versão " + BuildConfig.VERSION_NAME);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(10);
        super.onAttach(activity);
    }


}
