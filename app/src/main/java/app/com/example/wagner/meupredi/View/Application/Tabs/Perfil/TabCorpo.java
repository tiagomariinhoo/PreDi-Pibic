package app.com.example.wagner.meupredi.View.Application.Tabs.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import app.com.example.wagner.meupredi.Model.Medida;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.MedidaView;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;
import app.com.example.wagner.meupredi.View.Application.MedidaListener;
import app.com.example.wagner.meupredi.View.Application.PacienteListener;
import app.com.example.wagner.meupredi.View.Application.Popups.PopPesoIdeal;
import app.com.example.wagner.meupredi.View.Application.TabelaImc;

import static app.com.example.wagner.meupredi.R.layout.tab_corpo_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabCorpo extends Activity implements PacienteListener, MedidaListener {

    private TextView pesoAtual, circAtual, imcAtual, ultimaMedicao;
    private Button chamadaPeso;
    private TextView informativoIMC, pesoIdeal;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_corpo_perfil);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        pesoAtual = (TextView) findViewById(R.id.text_valor_hemoglobina_glicolisada_atual);
        circAtual = (TextView) findViewById(R.id.text_valor_glicose_75g_atual);
        imcAtual = (TextView) findViewById(R.id.text_valor_glicose_jejum_atual);
        chamadaPeso = findViewById(R.id.btn_tab_medidas_atualizar);
        ultimaMedicao = (TextView) findViewById(R.id.text_tab_corpo_peso_ultima_medicao);
        informativoIMC = (TextView) findViewById(R.id.imageView10);
        pesoIdeal = (TextView) findViewById(R.id.text_qual_meu_peso_ideal);

        PacienteUpdater.addListener((PacienteListener) this);
        PacienteUpdater.addListener((MedidaListener) this);

        pesoAtual.setText(String.format(Locale.getDefault(), "%.2f", paciente.getPeso()));
        circAtual.setText(String.format(Locale.getDefault(), "%.2f", paciente.getCircunferencia()));
        imcAtual.setText(String.format(Locale.getDefault(), "%.2f", paciente.getImc()));

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
                Intent chamadaPesoIdeal = new Intent(TabCorpo.this, PopPesoIdeal.class);
                startActivity(chamadaPesoIdeal);
            }
        });

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
    }

    @Override
    public void onChangeMedida(Medida medida) {
        if(medida != null) {
            pesoAtual.setText(String.format(Locale.getDefault(), "%.2f", medida.getPeso()));
            ultimaMedicao.setText("Última medição: " + medida.printDate());
            ultimaMedicao.setVisibility(View.VISIBLE);
        }
    }

}