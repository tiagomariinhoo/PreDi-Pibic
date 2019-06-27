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
import app.com.example.wagner.meupredi.Model.Taxas;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;
import app.com.example.wagner.meupredi.View.Application.TaxasListener;

import static app.com.example.wagner.meupredi.R.layout.tab_hemoglobina_glicada_taxas;

public class TabHemoglobinaGlicadaEdit extends Activity implements TaxasListener {

    private Paciente paciente;
    private TextView hemoglobinaGlicolisada;
    private EditText novaHemoglobinaGlicolisada;
    private Button atualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_hemoglobina_glicada_taxas);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        PacienteUpdater.addListener(this);

        hemoglobinaGlicolisada = findViewById(R.id.text_hemoglobina_glicolisadaAtual_taxas);

        novaHemoglobinaGlicolisada = findViewById(R.id.edit_hemoglobina_glicolisada_taxas);
        novaHemoglobinaGlicolisada.setRawInputType(Configuration.KEYBOARD_QWERTY);

        findViewById(R.id.tab_hemoglobina_glicada_taxas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(novaHemoglobinaGlicolisada.getWindowToken(), 0);
                }
            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(novaHemoglobinaGlicolisada.getText().toString().length() != 0) {

                    String novaHG = novaHemoglobinaGlicolisada.getText().toString();

                    novaHG = novaHG.replace(',', '.');

                    Double hgAtualizada = 0.0;
                    try{
                        hgAtualizada = Double.parseDouble(novaHG);
                    } catch(Exception e){
                        Toast.makeText(TabHemoglobinaGlicadaEdit.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    String hgFormatada = String.format(Locale.ENGLISH, "%.2f", hgAtualizada);
                    Double hgDoPaciente = Double.parseDouble(hgFormatada);

                    hemoglobinaGlicolisada.setText(String.valueOf(hgDoPaciente) + " mg/dL");

                    Log.d("HG : ", hemoglobinaGlicolisada.getText().toString());

                    paciente.setHemoglobinaGlicolisada(hgDoPaciente);

                } else {
                    paciente.setHemoglobinaGlicolisada(0);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        if(isFinishing()){
            Log.d("Listener ", "Removido");
            PacienteUpdater.removeListener(this);
        }
        super.onPause();
    }

    @Override
    public void onChangeTaxas(Taxas taxas) {
        hemoglobinaGlicolisada.setText(taxas.stringHemoglobinaGlico()+" %");
    }
}