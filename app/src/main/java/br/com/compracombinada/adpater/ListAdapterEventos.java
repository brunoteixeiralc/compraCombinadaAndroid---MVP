package br.com.compracombinada.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.R;
import br.com.compracombinada.model.Amizade;
import br.com.compracombinada.model.Evento;

public class ListAdapterEventos extends BaseAdapter {

    private List<Evento> lista;
    private static LayoutInflater inflater = null;
    private View view;
    private ArrayList<Evento> arraylist;

    public ListAdapterEventos(Context context, List<Evento> lista) {
        this.lista = lista;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Evento>();
        this.arraylist.addAll(lista);

    }

    static class ViewHolder {

        TextView eventoNome;
        TextView eventoData;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        view = convertView;

        if (view == null) {

            holder = new ViewHolder();

            view = inflater.inflate(R.layout.list_row_evento, null);
            view.setTag(holder);

            holder.eventoNome = (TextView) view.findViewById(R.id.evento_nome);
            holder.eventoData = (TextView) view.findViewById(R.id.evento_data);

        } else {

            holder = (ViewHolder) view.getTag();
        }

        holder.eventoNome.setText(lista.get(position).getNome());

//         DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
//         DateTime date = new DateTime(Long.parseLong(lista.get(position).getDataHora()));

        holder.eventoData.setText(lista.get(position).getDataHora());

        return view;

    }


}