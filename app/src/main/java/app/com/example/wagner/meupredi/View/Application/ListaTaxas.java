package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
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

        listaDeTaxas = (android.widget.ListView) findViewById(R.id.lista_taxas);
        botaoAlterarMedicao = (Button) findViewById(R.id.btn_alterar_medicao_taxas);

        ControllerExames exameController = new ControllerExames(ListaTaxas.this);
        try {
            ArrayList<ExameClass> taxaList = exameController.getAllExamesClass(paciente);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            adapter = new ArrayAdapter<String>(this, R.layout.lista_item_pesos, R.id.text_item_lista_peso, adapterList(exameController));
        } catch (Exception e) {
            e.printStackTrace();
        }

        listaDeTaxas.setAdapter(adapter);

    }

    private ArrayList<String> adapterList(ControllerExames exameController) throws Exception {

        ArrayList<ExameClass> taxasList = exameController.getAllExamesClass(paciente);

        ArrayList<String> taxasListAux = new ArrayList<>();

        for(ExameClass taxa : taxasList){
            taxasListAux.add(taxa.toString());
        }

        //Collections.reverse(pesoListAux);

        return taxasListAux;
    }

}
