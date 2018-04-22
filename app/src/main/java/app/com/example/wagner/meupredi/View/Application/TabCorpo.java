package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

import static app.com.example.wagner.meupredi.R.layout.tab_corpo_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

    public class TabCorpo extends Activity{

    private TextView pesoAtual, chamadaPeso, ultimaMedicao, imcAtual;
    private TextView informativoIMC, pesoIdeal;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_corpo_perfil);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        pesoAtual = (TextView) findViewById(R.id.text_tab_corpo_peso_atual);
        chamadaPeso = (TextView) findViewById(R.id.text_tab_corpo_atualizar_peso);
        ultimaMedicao = (TextView) findViewById(R.id.text_tab_corpo_peso_ultima_medicao);
        imcAtual = (TextView) findViewById(R.id.text_tab_corpo_imc_atual);
        informativoIMC = (TextView) findViewById(R.id.text_chamada_info_imc);
        pesoIdeal = (TextView) findViewById(R.id.text_qual_meu_peso_ideal);

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