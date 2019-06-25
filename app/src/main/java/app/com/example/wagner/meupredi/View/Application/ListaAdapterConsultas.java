package app.com.example.wagner.meupredi.View.Application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Model.Consulta;
import app.com.example.wagner.meupredi.R;

public class ListaAdapterConsultas extends ArrayAdapter {

    private List<Consulta> adapterList = new ArrayList<>();

    public ListaAdapterConsultas(Context context, int textViewResourceId, List<Consulta> objects) {

        super(context, textViewResourceId, objects);
        adapterList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.lista_consultas_item, null);
        TextView dateViewItem = v.findViewById(R.id.text_data_consulta_item_lista);
        TextView valueViewItem = v.findViewById(R.id.text_consulta_item);
        TextView localViewItem = v.findViewById(R.id.text_local_consulta_item_lista);
        TextView horarioViewItem = v.findViewById(R.id.text_horario_consulta_item_lista);

        List<Consulta> aux = adapterList;
        dateViewItem.setText(aux.get(position).printingDate());
        horarioViewItem.setText(aux.get(position).printingTime());
        localViewItem.setText(aux.get(position).getLocal());
        valueViewItem.setText(aux.get(position).getTitulo());

        return v;
    }
}