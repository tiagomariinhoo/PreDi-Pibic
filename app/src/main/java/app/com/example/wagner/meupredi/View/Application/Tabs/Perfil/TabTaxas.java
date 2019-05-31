package app.com.example.wagner.meupredi.View.Application.Tabs.Perfil;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.Model.Taxas;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;
import app.com.example.wagner.meupredi.View.Application.MainViews.TaxasView;
import app.com.example.wagner.meupredi.View.Application.TaxasListener;

import static app.com.example.wagner.meupredi.R.layout.tab_taxas_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabTaxas extends Fragment implements TaxasListener {

    private TextView valor_glicoseJejum, valor_glicose75g , valor_hemoglobina_glicolisada;
    private Button chamadaAtualizarTaxas;
    private Paciente paciente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_taxas_perfil, container, false);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        chamadaAtualizarTaxas = view.findViewById(R.id.btn_tab_taxas_atualizar);
        valor_hemoglobina_glicolisada = (TextView) view.findViewById(R.id.text_valor_hemoglobina_glicolisada_atual);
        valor_glicoseJejum = (TextView) view.findViewById(R.id.text_valor_glicose_jejum_atual);
        valor_glicose75g = (TextView) view.findViewById(R.id.text_valor_glicose_75g_atual);

        PacienteUpdater.addListener(this);

        chamadaAtualizarTaxas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaxasView.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onChangeTaxas(Taxas taxas) {
        if (taxas != null){
            valor_hemoglobina_glicolisada.setText(String.format(Locale.getDefault(), "%.2f", taxas.getHemoglobinaGlico()));
            valor_glicose75g.setText(String.format(Locale.getDefault(), "%.2f", taxas.getGlicose75g()));
            valor_glicoseJejum.setText(String.format(Locale.getDefault(), "%.2f", taxas.getGlicoseJejum()));
        }
    }
}
