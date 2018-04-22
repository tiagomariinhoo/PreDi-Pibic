package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by leandro on 06/03/18.
 */

public class PopPerfil extends Activity{

    private TextView consultas, academia, dicas;
    private Paciente paciente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_perfil);
        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) ( width*.8), (int) (height*.75));

        consultas = (TextView) findViewById(R.id.text_pop_consultas);
        academia = (TextView) findViewById(R.id.text_pop_academia);
        dicas = (TextView) findViewById(R.id.text_pop_dicas);

        consultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaConsultas = new Intent(PopPerfil.this, Consultas.class);
                chamadaConsultas.putExtra("Paciente", paciente);
                startActivity(chamadaConsultas);
            }
        });

        academia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaAcademia = new Intent(PopPerfil.this, Exercicios.class);
                startActivity(chamadaAcademia);
            }
        });

        dicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaDicas = new Intent(PopPerfil.this, Dicas.class);
                startActivity(chamadaDicas);
            }
        });


    }
}
