package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import app.com.example.wagner.meupredi.Controller.ControllerAgenda;
import app.com.example.wagner.meupredi.Model.ModelClass.AgendaClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.Consultas;

import static app.com.example.wagner.meupredi.R.layout.tab_consultas_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabConsultas extends Activity {

    private Paciente paciente;
    private ListView listaDeConsultas;
    private ArrayAdapter<String> adapter;
    ArrayList<AgendaClass> agendaList = new ArrayList<>();
    private TextView chamadaConsultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_consultas_perfil);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        listaDeConsultas = (ListView) findViewById(R.id.lista_consultas);
        chamadaConsultas = (TextView) findViewById(R.id.tab_perfil_consultas);

        ControllerAgenda controllerAgenda = new ControllerAgenda(TabConsultas.this);

        adapter = new ArrayAdapter<String>(this, R.layout.lista_consultas_item, R.id.text_consulta_item, adapterList(controllerAgenda));

        listaDeConsultas.setAdapter(adapter);

        chamadaConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabConsultas.this, Consultas.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> adapterList(ControllerAgenda controllerAgenda){

        agendaList = controllerAgenda.getAllEventos(paciente);

        ArrayList<String> agendaList2 = new ArrayList<>();

        for(AgendaClass agenda : agendaList){
            agendaList2.add(agenda.toString());
        }

        return agendaList2;
    }

}