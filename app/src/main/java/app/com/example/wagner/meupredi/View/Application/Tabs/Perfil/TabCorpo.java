package app.com.example.wagner.meupredi.View.Application.Tabs.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import app.com.example.wagner.meupredi.Model.Medida;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.MedidaView;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;
import app.com.example.wagner.meupredi.View.Application.MedidaListener;
import app.com.example.wagner.meupredi.View.Application.PacienteListener;
import app.com.example.wagner.meupredi.View.Application.PesoIdeal;
import app.com.example.wagner.meupredi.View.Application.TabelaImc;

import static app.com.example.wagner.meupredi.R.layout.tab_corpo_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabCorpo extends Activity implements PacienteListener, MedidaListener {

    private TextView pesoAtual, chamadaPeso, ultimaMedicao, imcAtual;
    private TextView informativoIMC, pesoIdeal, statusIMC;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_corpo_perfil);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        pesoAtual = (TextView) findViewById(R.id.text_tab_corpo_peso_atual);
        chamadaPeso = (TextView) findViewById(R.id.text_tab_corpo_atualizar_peso);
        ultimaMedicao = (TextView) findViewById(R.id.text_tab_corpo_peso_ultima_medicao);
        imcAtual = (TextView) findViewById(R.id.text_tab_corpo_imc_atual);
        informativoIMC = (TextView) findViewById(R.id.text_chamada_info_imc);
        pesoIdeal = (TextView) findViewById(R.id.text_qual_meu_peso_ideal);
        statusIMC = (TextView) findViewById(R.id.text_tab_corpo_status_imc);

        PacienteUpdater.addListener((PacienteListener) this);
        PacienteUpdater.addListener((MedidaListener) this);

        pesoAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPeso = new Intent(TabCorpo.this, MedidaView.class);
                startActivity(chamadaPeso);
            }
        });

        chamadaPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPeso = new Intent(TabCorpo.this, MedidaView.class);
                startActivity(chamadaPeso);
            }
        });

        informativoIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaTabelaImc = new Intent(TabCorpo.this, TabelaImc.class);
                startActivity(chamadaTabelaImc);
            }
        });

        pesoIdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPesoIdeal = new Intent(TabCorpo.this, PesoIdeal.class);
                startActivity(chamadaPesoIdeal);
            }
        });

    }

    public void setImcStatus(){
        if(paciente.getImc() <= 16.9){
            statusIMC.setText("Muito abaixo do Peso");
        }
        else if(paciente.getImc() <= 18.4){
            statusIMC.setText("Abaixo do peso");
        }
        else if(paciente.getImc() <= 24.9){
            statusIMC.setText("Peso normal");
        }
        else if(paciente.getImc() <= 29.9){
            statusIMC.setText("Acima do peso");
        }
        else if(paciente.getImc() <= 34.9){
            statusIMC.setText("Obesidade I");
        }
        else if(paciente.getImc() <= 40){
            statusIMC.setText("Obesidade II");
        }
        else{
            statusIMC.setText("Obesidade III");
        }
    }

    @Override
    protected void onPause() {
        if(isFinishing()){
            Log.d("Listener ", "Removido");
            PacienteUpdater.removeListener((PacienteListener) this);
            PacienteUpdater.removeListener((MedidaListener) this);
        }
        super.onPause();
    }

    @Override
    public void onChangePaciente(Paciente paciente) {
        this.paciente = paciente;
        imcAtual.setText(String.format(Locale.getDefault(), "%.2f",paciente.getImc()));
        setImcStatus();
    }

    @Override
    public void onChangeMedida(Medida medida) {
        if(medida != null) {
            pesoAtual.setText(String.format(Locale.getDefault(), "%.2f", medida.getPeso()));
            ultimaMedicao.setText("Ultima medição: " + medida.printDate());
            ultimaMedicao.setVisibility(View.VISIBLE);
        }
    }

}