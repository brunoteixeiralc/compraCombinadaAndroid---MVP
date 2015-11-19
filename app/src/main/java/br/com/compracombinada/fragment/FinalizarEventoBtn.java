package br.com.compracombinada.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.compracombinada.R;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaValidadeCotacao;
import br.com.compracombinada.model.Evento;

/**
 * Created by bruno on 21/08/14.
 */
public class FinalizarEventoBtn extends android.support.v4.app.Fragment {

    private View view;
    private Button btnFinalizar;
    private Evento evento;
    private TextView msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.finalizar_evento, container, false);

        evento = (Evento) getArguments().getSerializable("evento");

        btnFinalizar = (Button) view.findViewById(R.id.btnFinalizar);
        msg = (TextView) view.findViewById(R.id.msg);

        if (!evento.isAcabouCotacao()) {

            btnFinalizar.setVisibility(View.GONE);
            msg.setVisibility(View.VISIBLE);

        } else {

            msg.setVisibility(View.GONE);
            btnFinalizar.setVisibility(View.VISIBLE);
            btnFinalizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AsyncTaskCompraColetivaValidadeCotacao(FinalizarEventoBtn.this).execute(evento);

                }
            });
        }

        return view;
    }

    public void retornoAsyncTaskCompraCombinadaValidaCotacao(String retorno) {

        Fragment fragment = new SucessoFinalizarMerge();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null)
                .commit();

//        AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarEventoBtn.this.getActivity());
//        builder.setMessage("Merge feito com sucesso!!Ir no menu Suas Cotações para ver o resultado...")
//                .setTitle("Compra Combinada");
//        AlertDialog dialog = builder.create();
//        dialog.show();

    }
}
