package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.GraficosPeso;

import static app.com.example.wagner.meupredi.R.layout.tab_evolucao_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabEvolucao extends Activity {

    private Button graficopeso, graficotaxas;
    private Paciente paciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_evolucao_perfil);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
/*
        graficopeso = (Button) findViewById(R.id.btn_tab_evolucao_peso);

        graficopeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabEvolucao.this, GraficosPeso.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });
*/
    }
}