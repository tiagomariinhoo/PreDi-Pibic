package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

import static app.com.example.wagner.meupredi.R.layout.tab_taxas_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabTaxas extends Activity {

    private TextView chamadaAtualizarTaxas;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_taxas_perfil);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        chamadaAtualizarTaxas = (TextView) findViewById(R.id.btn_atualizar_taxas);

        chamadaAtualizarTaxas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabTaxas.this, Taxas.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });
    }
}
