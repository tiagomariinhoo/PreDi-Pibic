package app.com.example.wagner.meupredi.View.Application.Tabs.Perfil;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Controller.ConsultaController;
import app.com.example.wagner.meupredi.Model.Consulta;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.ListaAdapterConsultas;
import app.com.example.wagner.meupredi.View.Application.MainViews.ConsultaView;
import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabConsultas extends Fragment implements LiveUpdateHelper<Consulta> {

    private Paciente paciente;
    private ListView listaDeConsultas;
    private ListaAdapterConsultas adapter;
    private Button chamadaConsultas;
    private ListenerRegistration listListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_consultas_perfil, container, false);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        listaDeConsultas = view.findViewById(R.id.lista_consultas);
        chamadaConsultas = view.findViewById(R.id.btn_tab_perfil_consultas);
        listaDeConsultas.setAdapter(new ListaAdapterConsultas(getActivity(), R.layout.lista_consultas_item, new ArrayList<Consulta>()));

        listListener = ConsultaController.getLiveConsultas(this, paciente);

        chamadaConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConsultaView.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        if(getActivity().isFinishing()){
            if(listListener != null) listListener.remove();
        }
        super.onPause();
    }

    @Override
    public void onReceiveData(List<Consulta> consultas) {
        Log.d("Got Consultas", Integer.toString(consultas.size()));
        adapter = new ListaAdapterConsultas(getActivity(), R.layout.lista_consultas_item, consultas);

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