package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.List;

import app.com.example.wagner.meupredi.Controller.TaxasController;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;

public class ListaTaxas extends Activity implements LiveUpdateHelper<Taxas> {

    private Paciente paciente;
    private ImageView informacao;
    private RadioGroup radioTaxasGroup;
    private List<Taxas> taxas;
    private RadioButton radioTaxasButton;
    private android.widget.ListView listaDeTaxas;
    private ListaAdapter adapter;
    private AlertDialog.Builder alertaDuvidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_taxas);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        listaDeTaxas = findViewById(R.id.lista_taxas);
        radioTaxasGroup = findViewById(R.id.radioTaxas);
        informacao = findViewById(R.id.image_informacao_lista_taxas);

        int tipoEscolhido = radioTaxasGroup.getCheckedRadioButtonId();
        radioTaxasButton = (RadioButton) findViewById(tipoEscolhido);

        informacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaDuvidas = new AlertDialog.Builder(ListaTaxas.this);
                alertaDuvidas.setTitle("Informativo");
                alertaDuvidas.setMessage("Clicando em alguma Taxa na lista, você poderá altera-la ou exclui-la\n");
                // Caso EDITAR
                alertaDuvidas.setNegativeButton("VOLTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ListaTaxas.this, "Clique para editar ou remover uma taxa da lista!", Toast.LENGTH_LONG).show();
                            }
                        });
                /* Caso REMOVER
                alertaPesoSelecionado.setPositiveButton("REMOVER",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });*/
                alertaDuvidas.create().show();
            }
        });

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
        Collections.reverse(taxas);
        adapter = new ListaAdapter(ListaTaxas.this, R.layout.lista_item, taxas, Taxas.class);
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
            }
        });
    }

    @Override
    public void onReceiveData(List<Taxas> data) {
        this.taxas = data;
        //Collections.reverse(medidas);
        onChangedAdapter(taxas);
    }
}