package app.com.example.wagner.meupredi.View.Application.Tabs.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Controller.ConsultaController;
import app.com.example.wagner.meupredi.Model.ModelClass.Consulta;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.ConsultaView;
import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

import static app.com.example.wagner.meupredi.R.layout.tab_consultas_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabConsultas extends Activity implements LiveUpdateHelper<Consulta> {

    private Paciente paciente;
    private ListView listaDeConsultas;
    private ArrayAdapter<String> adapter;
    private TextView chamadaConsultas;
    private ListenerRegistration listListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_consultas_perfil);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        listaDeConsultas = (ListView) findViewById(R.id.lista_consultas);
        chamadaConsultas = (TextView) findViewById(R.id.tab_perfil_consultas);
        listaDeConsultas.setAdapter(new ArrayAdapter<String>(this, R.layout.lista_consultas_item,
                                    R.id.text_consulta_item, adapterList(new ArrayList<Consulta>())));

        listListener = ConsultaController.getLiveConsultas(this, paciente);

        chamadaConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabConsultas.this, ConsultaView.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        if(isFinishing()){
            if(listListener != null) listListener.remove();
        }
        super.onPause();
    }

    @Override
    public void onReceiveData(List<Consulta> consultas) {
        Log.d("Got Consultas", Integer.toString(consultas.size()));
        adapter = new ArrayAdapter<String>(TabConsultas.this, R.layout.lista_consultas_item,
                R.id.text_consulta_item, adapterList(consultas));

        listaDeConsultas.setAdapter(adapter);
    }

    private ArrayList<String> adapterList(List<Consulta> consultas){

        ArrayList<String> auxConsultas = new ArrayList<>();

        for(Consulta consulta : consultas){
            auxConsultas.add(consulta.toString());
        }

        return auxConsultas;
    }

}