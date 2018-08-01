package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.PesoClass;
import app.com.example.wagner.meupredi.R;

public class ListaPesos extends Activity {

    private Paciente paciente;
    private android.widget.ListView listaDePesos;
    private ArrayAdapter<String> adapter;
    ArrayList<PesoClass> pesoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pesos);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        listaDePesos = (android.widget.ListView) findViewById(R.id.lista_pesos);

        ControllerPeso pesoController = new ControllerPeso(ListaPesos.this);

        adapter = new ArrayAdapter<String>(this, R.layout.lista_item_pesos, R.id.text_item_lista_peso, adapterList(pesoController));

        listaDePesos.setAdapter(adapter);

    }

    private ArrayList<String> adapterList(ControllerPeso pesoController){

        ArrayList<PesoClass> pesoList = pesoController.getAllInfos(paciente);

        ArrayList<String> pesoListAux = new ArrayList<>();

        for(PesoClass peso : pesoList){
            pesoListAux.add(peso.toString());
        }

        return pesoListAux;
    }

}
