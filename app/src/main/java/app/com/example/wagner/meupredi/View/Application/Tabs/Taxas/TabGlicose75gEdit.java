package app.com.example.wagner.meupredi.View.Application.Tabs.Taxas;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

import static app.com.example.wagner.meupredi.R.layout.tab_glicose_apos75g_taxas;

public class TabGlicose75gEdit extends Activity {

    private Paciente paciente;
    private TextView glicose75;
    private EditText novaGlicose75;
    private Button atualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_glicose_apos75g_taxas);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        glicose75 = findViewById(R.id.text_hemoglobina_glicolisadaAtual_taxas);
        glicose75.setText(String.valueOf(paciente.getGlicose75g()) + " mg/dL");

        novaGlicose75 = findViewById(R.id.edit_hemoglobina_glicolisada_taxas);
        novaGlicose75.setRawInputType(Configuration.KEYBOARD_QWERTY);

        findViewById(R.id.tab_glicose_75g_taxas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(novaGlicose75.getWindowToken(), 0);
                    }
            }
        });

        if(novaGlicose75.getText().toString().length() != 0) {

            String novaG75 = novaGlicose75.getText().toString();

            novaG75 = novaG75.replace(',' , '.');

            Double g75Atualizada = 0d;
            try{
                g75Atualizada = Double.parseDouble(novaG75);
            } catch(Exception e){
                Toast.makeText(TabGlicose75gEdit.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                finish();
            }

            String g75Formatada = String.format(Locale.ENGLISH, "%.2f", g75Atualizada);
            Double g75DoPaciente = Double.parseDouble(g75Formatada);

            glicose75.setText(String.valueOf(g75DoPaciente) + " mg/dL");

            Log.d("Gli75 : ", glicose75.getText().toString());

            paciente.setGlicose75g(g75DoPaciente);

        } else {
            paciente.setGlicose75g(0);
        }

    }
}