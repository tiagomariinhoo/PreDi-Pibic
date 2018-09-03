package app.com.example.wagner.meupredi.View.Application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.mail.imap.protocol.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.R;

public class listaAdapter extends ArrayAdapter {

    List<Taxas> taxasList = new ArrayList<>();

    String type = "Glicose Jejum";

    public listaAdapter(Context context, int textViewResourceId, List<Taxas> objects) {
        super(context, textViewResourceId, objects);
        taxasList = objects;
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
        CheckBox checkBoxItem = (CheckBox) v.findViewById(R.id.checkBox_item_lista);
        dateViewItem.setText(taxasList.get(position).printDate());
        switch (type){
            case "Glicose Jejum":
                valueViewItem.setText(taxasList.get(position).printGlicoseJejum());
                break;
            case "Glicose 75g":
                valueViewItem.setText(taxasList.get(position).printGlicose75g());
                break;
            case "Hemoglobina Glicada":
                valueViewItem.setText(taxasList.get(position).printHemoglobinaGlicada());
                break;
        }

        return v;
    }
}