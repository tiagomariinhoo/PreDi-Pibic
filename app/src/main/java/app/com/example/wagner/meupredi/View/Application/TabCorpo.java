package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.Peso;

import static app.com.example.wagner.meupredi.R.layout.tab_corpo_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

    public class TabCorpo extends Activity{

    private TextView pesoAtual, chamadaPeso, ultimaMedicao, imcAtual;
    private TextView informativoIMC, pesoIdeal, statusIMC;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_corpo_perfil);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        double imc = paciente.get_imc();

        pesoAtual = (TextView) findViewById(R.id.text_tab_corpo_peso_atual);
        chamadaPeso = (TextView) findViewById(R.id.text_tab_corpo_atualizar_peso);
        ultimaMedicao = (TextView) findViewById(R.id.text_tab_corpo_peso_ultima_medicao);
        imcAtual = (TextView) findViewById(R.id.text_tab_corpo_imc_atual);
        informativoIMC = (TextView) findViewById(R.id.text_chamada_info_imc);
        pesoIdeal = (TextView) findViewById(R.id.text_qual_meu_peso_ideal);
        statusIMC = (TextView) findViewById(R.id.text_tab_corpo_status_imc);

        pesoAtual.setText(String.format("%.2f", paciente.get_peso()));
        imcAtual.setText(String.valueOf(imc));

        if(imc <= 16.9){
            statusIMC.setText("Muito abaixo do Peso");
        }
        else if(imc <= 18.4){
            statusIMC.setText("Abaixo do peso");
        }
        else if(imc <= 24.9){
            statusIMC.setText("Peso normal");
        }
        else if(imc <= 29.9){
            statusIMC.setText("Acima do peso");
        }
        else if(imc <= 34.9){
            statusIMC.setText("Obesidade I");
        }
        else if(imc <= 40){
            statusIMC.setText("Obesidade II");
        }
        else{
            statusIMC.setText("Obesidade III");
        }

        pesoAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPeso = new Intent(TabCorpo.this, Peso.class);
                chamadaPeso.putExtra("Paciente", paciente);
                startActivity(chamadaPeso);
            }
        });

        chamadaPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPeso = new Intent(TabCorpo.this, Peso.class);
                chamadaPeso.putExtra("Paciente", paciente);
                startActivity(chamadaPeso);
            }
        });

        informativoIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaTabelaImc = new Intent(TabCorpo.this, TabelaImc.class);
                chamadaTabelaImc.putExtra("Paciente", paciente);
                startActivity(chamadaTabelaImc);
            }
        });

        pesoIdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPesoIdeal = new Intent(TabCorpo.this, PesoIdeal.class);
                chamadaPesoIdeal.putExtra("Paciente", paciente);
                startActivity(chamadaPesoIdeal);
            }
        });

        }
    }