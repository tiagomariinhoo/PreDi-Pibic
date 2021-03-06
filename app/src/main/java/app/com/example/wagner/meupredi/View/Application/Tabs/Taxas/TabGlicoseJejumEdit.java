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

import static app.com.example.wagner.meupredi.R.layout.tab_glicose_jejum_taxas;

public class TabGlicoseJejumEdit extends Activity implements TaxasListener {

    private Paciente paciente;
    private TextView glicoseJejum;
    private EditText novaGlicoseJejum;
    private Button atualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_glicose_jejum_taxas);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        PacienteUpdater.addListener(this);

        atualizar = findViewById(R.id.btn_atualizar_taxas_jejum);

        glicoseJejum = findViewById(R.id.text_hemoglobina_glicolisadaAtual_taxas);

        novaGlicoseJejum = findViewById(R.id.edit_hemoglobina_glicolisada_taxas);
        novaGlicoseJejum.setRawInputType(Configuration.KEYBOARD_QWERTY);

        findViewById(R.id.tab_glicose_jejum_taxas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(novaGlicoseJejum.getWindowToken(), 0);
                }
            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(novaGlicoseJejum.getText().toString().length() != 0) {

                    String novaGJ = novaGlicoseJejum.getText().toString();

                    novaGJ = novaGJ.replace(',', '.');
                    Double gJAtualizada = 0d;
                    try{
                        gJAtualizada = Double.parseDouble(novaGJ);
                    } catch(Exception e){
                        Toast.makeText(TabGlicoseJejumEdit.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    String gJFormatada = String.format(Locale.ENGLISH, "%.2f", gJAtualizada);
                    Double gJDoPaciente = Double.parseDouble(gJFormatada);
                    glicoseJejum.setText(String.valueOf(gJDoPaciente) + " mg/dL");
                    Log.d("GJejum : ", glicoseJejum.getText().toString());
                    paciente.setGlicoseJejum(gJDoPaciente);
                } else {
                    paciente.setGlicoseJejum(0);
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
        glicoseJejum.setText(taxas.stringGlicoseJejum()+" mg/dL");
    }
}
