package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Controller.TaxasController;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.R;

public class ListaTaxas extends Activity {

    private Paciente paciente;
    private android.widget.ListView listaDeTaxas;
    private ArrayAdapter<String> adapter;
    private AlertDialog.Builder alertaTaxaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_taxas);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        listaDeTaxas = (ListView) findViewById(R.id.lista_taxas);

        TaxasController.getAllTaxas(paciente).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Taxas> taxas = queryDocumentSnapshots.toObjects(Taxas.class);
                adapter = new ArrayAdapter<String>(ListaTaxas.this, R.layout.lista_item_pesos,
                        R.id.text_item_lista_peso, adapterList(taxas));

                listaDeTaxas.setAdapter(adapter);
            }
        });
    }

    private ArrayList<String> adapterList(List<Taxas> taxas){

        ArrayList<String> taxasAux = new ArrayList<>();

        for(Taxas taxa : taxas){
            taxasAux.add(taxa.toString());
        }
        return taxasAux;
    }
}