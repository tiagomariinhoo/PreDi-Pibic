package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Controller.TaxasController;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.R;

public class ListaTaxas extends Activity {

    private Paciente paciente;
    private Button btnListar;
    private ImageView deletar;
    private RadioGroup radioTaxasGroup;
    private RadioButton radioTaxasButton;
    private android.widget.ListView listaDeTaxas;
    private listaAdapter adapter;
    private AlertDialog.Builder alertaTaxaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_taxas);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        listaDeTaxas = findViewById(R.id.lista_taxas);
        radioTaxasGroup = findViewById(R.id.radioTaxas);
        deletar = findViewById(R.id.image_deletar_item);

        deletar.setVisibility(View.INVISIBLE);

        int tipoEscolhido = radioTaxasGroup.getCheckedRadioButtonId();
        radioTaxasButton = (RadioButton) findViewById(tipoEscolhido);

        TaxasController.getAllTaxas(paciente).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<Taxas> taxas = queryDocumentSnapshots.toObjects(Taxas.class);
                onChangedAdapter(taxas);
                listaDeTaxas.setAdapter(adapter);

                radioTaxasGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                        onChangedAdapter(taxas);
                        listaDeTaxas.setAdapter(adapter);
                    }
                });
                addListeners(taxas);
            }
        });
    }

    private void onChangedAdapter(List<Taxas> taxas){
        adapter = new listaAdapter(ListaTaxas.this, R.layout.lista_item, taxas);
        switch (radioTaxasGroup.getCheckedRadioButtonId()){
            case R.id.radioJejum:
                adapter.setType("Glicose Jejum");
                break;
            case R.id.radioApos75g:
                adapter.setType("Glicose 75g");
                break;
            case R.id.radioGlicada:
                adapter.setType("Hemoglobina Glicada");
                break;
        }
    }

    private void addListeners(List<Taxas> taxas){

        listaDeTaxas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Taxas taxa = taxas.get(position);
                deletar.setVisibility(View.VISIBLE);
            }
        });
    }
}