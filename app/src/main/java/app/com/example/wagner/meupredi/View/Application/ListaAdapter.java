package app.com.example.wagner.meupredi.View.Application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Model.ModelClass.Medida;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.R;

public class ListaAdapter<T> extends ArrayAdapter {

    List<T> adapterList = new ArrayList<>();
    Class<T> parameterClass;
    String type;

    public ListaAdapter(Context context, int textViewResourceId, List<T> objects, Class<T> parameterClass) {
        super(context, textViewResourceId);
        adapterList = objects;
        this.parameterClass = parameterClass;
        if(parameterClass == Taxas.class){
            type = "Glicose Jejum";
        }
        else if(parameterClass == Medida.class){
            type = "Peso";
        }
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public void setType(String type){
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.lista_item, null);
        TextView dateViewItem = (TextView) v.findViewById(R.id.text_date_item_lista);
        TextView valueViewItem = (TextView) v.findViewById(R.id.text_item_lista);

        if(parameterClass == Taxas.class){
            List<Taxas> aux = (List<Taxas>) adapterList;
            dateViewItem.setText(aux.get(position).printDate());
            switch (type){
                case "Glicose Jejum":
                    valueViewItem.setText(aux.get(position).printGlicoseJejum());
                    break;
                case "Glicose 75g":
                    valueViewItem.setText(aux.get(position).printGlicose75g());
                    break;
                case "Hemoglobina Glicada":
                    valueViewItem.setText(aux.get(position).printHemoglobinaGlicada());
                    break;
            }
        }else if(parameterClass == Medida.class){
            List<Medida> aux = (List<Medida>) adapterList;
            dateViewItem.setText(aux.get(position).printDate());
            switch (type) {
                case "Peso":
                    valueViewItem.setText(aux.get(position).printPeso());
                    break;
                case "Circunferencia":
                    valueViewItem.setText(aux.get(position).printCircunferencia());
                    break;
            }
        }

        return v;
    }
}