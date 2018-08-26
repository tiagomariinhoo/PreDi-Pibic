package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Controller.ConsultaController;
import app.com.example.wagner.meupredi.Model.ModelClass.Consulta;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.ConsultaView;

import static app.com.example.wagner.meupredi.R.layout.tab_consultas_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabConsultas extends Activity {

    private Paciente paciente;
    private ListView listaDeConsultas;
    private ArrayAdapter<String> adapter;
    private TextView chamadaConsultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_consultas_perfil);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        listaDeConsultas = (ListView) findViewById(R.id.lista_consultas);
        chamadaConsultas = (TextView) findViewById(R.id.tab_perfil_consultas);
        listaDeConsultas.setAdapter(new ArrayAdapter<String>(this, R.layout.lista_consultas_item,
                                    R.id.text_consulta_item, adapterList(new ArrayList<Consulta>())));
        ConsultaController.getAllConsultas(paciente)
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<Consulta> consultas = queryDocumentSnapshots.toObjects(Consulta.class);
                    Log.d("Got Consultas", Integer.toString(consultas.size()));
                    adapter = new ArrayAdapter<String>(TabConsultas.this, R.layout.lista_consultas_item,
                            R.id.text_consulta_item, adapterList(consultas));

                    listaDeConsultas.setAdapter(adapter);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Firebase Error: ", e.getMessage());
                }
            });

        chamadaConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabConsultas.this, ConsultaView.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> adapterList(List<Consulta> consultas){

        ArrayList<String> auxConsultas = new ArrayList<>();

        for(Consulta consulta : consultas){
            auxConsultas.add(consulta.toString());
        }

        return auxConsultas;
    }

}