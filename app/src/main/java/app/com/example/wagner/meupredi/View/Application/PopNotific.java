package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class PopNotific extends Activity {

    private TextView relatorio;
    private ImageView chamadaExplicativoDiagnostico;
    private Paciente paciente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_notific);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Log.d("Teste glicose jejum: ", String.valueOf(paciente.getGlicoseJejum()));
        Log.d("Teste glicose 2h: ", String.valueOf(paciente.getGlicose75g()));

        chamadaExplicativoDiagnostico = findViewById(R.id.image_informacao_diagnostico);

        getWindow().setLayout((int) ( width*.8), (int) (height*.50));

        relatorio = (TextView) findViewById(R.id.text_pop_notific_relatorio);

        relatorio.setText(paciente.calculoStatus().getFrase());

        chamadaExplicativoDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopNotific.this, StartRelatorio.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
