package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.Controller.ControllerExames;
import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.ModelClass.ExameClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.PesoClass;
import app.com.example.wagner.meupredi.R;

public class ListaTaxas extends Activity {

    private Paciente paciente;
    private android.widget.ListView listaDeTaxas;
    private ArrayAdapter<String> adapter;
    private Button botaoAlterarMedicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_taxas);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        Log.d("listar taxas ", paciente.get_nome());

        listaDeTaxas = (android.widget.ListView) findViewById(R.id.lista_taxas);
        botaoAlterarMedicao = (Button) findViewById(R.id.btn_alterar_medicao_taxas);

        ControllerExames exameController = new ControllerExames(ListaTaxas.this);
        ArrayList<Float> taxaList = exameController.getGlicosesJejum(paciente);
        Log.d("TAMANHO DE TAXALIST: ", String.valueOf(taxaList.size()));

        Log.d("estou aqui ", paciente.get_nome());
        adapter = new ArrayAdapter<String>(this, R.layout.lista_item_taxas, R.id.text_item_lista_taxas, adapterList(exameController));

        listaDeTaxas.setAdapter(adapter);
    }

    private ArrayList<String> adapterList(ControllerExames exameController) {

        Integer tipo = 1;

        ArrayList<Float> taxaListJejum = exameController.getGlicosesJejum(paciente);

        ArrayList<String> taxasListAux = new ArrayList<>();

        for(Float t : taxaListJejum){
            taxasListAux.add(String.valueOf(t));
        }
        //Collections.reverse(pesoListAux);
        Log.d("ESTOU NA ARRAYLIST: ", String.valueOf(taxasListAux.size()));
        return taxasListAux;
    }
}
