package app.com.example.wagner.meupredi.View.Application.Tabs.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;
import app.com.example.wagner.meupredi.View.Application.MainViews.TaxasView;
import app.com.example.wagner.meupredi.View.Application.PacienteListener;
import app.com.example.wagner.meupredi.View.Application.TaxasListener;

import static app.com.example.wagner.meupredi.R.layout.tab_taxas_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabTaxas extends Activity implements TaxasListener {

    private TextView chamadaAtualizarTaxas, valor_glicoseJejum, valor_glicose75g , valor_hemoglobina_glicolisada;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_taxas_perfil);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        chamadaAtualizarTaxas = (TextView) findViewById(R.id.btn_atualizar_taxas_75g);
        valor_hemoglobina_glicolisada = (TextView) findViewById(R.id.text_valor_hemoglobina_glicolisada_atual);
        valor_glicoseJejum = (TextView) findViewById(R.id.text_valor_glicose_jejum_atual);
        valor_glicose75g = (TextView) findViewById(R.id.text_valor_glicose_75g_atual);

        PacienteUpdater.addListener(this);

        valor_hemoglobina_glicolisada.setText(String.format(Locale.getDefault(), "%.2f %%", paciente.getHemoglobinaGlicolisada()));
        valor_glicose75g.setText(String.format(Locale.getDefault(), "%.2f mg/dL", paciente.getGlicose75g()));
        valor_glicoseJejum.setText(String.format(Locale.getDefault(), "%.2f  mg/dL", paciente.getGlicoseJejum()));

        chamadaAtualizarTaxas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabTaxas.this, TaxasView.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onChangeTaxas(Taxas taxas) {
        if (taxas != null){
            valor_hemoglobina_glicolisada.setText(String.format(Locale.getDefault(), "%.2f %%", taxas.getHemoglobinaGlico()));
            valor_glicose75g.setText(String.format(Locale.getDefault(), "%.2f mg/dL", taxas.getGlicose75g()));
            valor_glicoseJejum.setText(String.format(Locale.getDefault(), "%.2f  mg/dL", taxas.getGlicoseJejum()));
        }
    }
}
