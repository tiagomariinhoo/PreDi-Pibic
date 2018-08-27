package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;
import app.com.example.wagner.meupredi.View.Application.MainViews.TaxasView;

import static app.com.example.wagner.meupredi.R.layout.tab_taxas_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabTaxas extends Activity implements TaxasListener{

    private TextView chamadaAtualizarTaxas, valor_glicoseJejum, valor_glicose75g , valor_hemoglobina_glicolisada;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_taxas_perfil);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        chamadaAtualizarTaxas = (TextView) findViewById(R.id.btn_atualizar_taxas);
        valor_hemoglobina_glicolisada = (TextView) findViewById(R.id.text_valor_hemoglobina_glicolisada_atual);
        valor_glicoseJejum = (TextView) findViewById(R.id.text_valor_glicose_jejum_atual);
        valor_glicose75g = (TextView) findViewById(R.id.text_valor_glicose_75g_atual);

        PacienteUpdater.addListener(this);

        Double hg = paciente.getHemoglobinaGlicolisada();
        valor_hemoglobina_glicolisada.setText(hg.toString()+" mg/dL");

        Double gl75 = paciente.getGlicose75g();
        valor_glicose75g.setText(gl75.toString()+" mg/dL");

        Double jejum = paciente.getGlicoseJejum();
        valor_glicoseJejum.setText(jejum.toString()+" mg/dL");

        chamadaAtualizarTaxas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabTaxas.this, TaxasView.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onChangeTaxas(Taxas taxas) {
        valor_hemoglobina_glicolisada.setText(String.format("%.2f", taxas.getHemoglobinaGlico()));
        valor_glicose75g.setText(String.format("%.2f", taxas.getGlicose75g()));
        valor_glicoseJejum.setText(String.format("%.2f", taxas.getGlicoseJejum()));
    }
}
