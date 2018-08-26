package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import app.com.example.wagner.meupredi.Controller.MedidaController;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.MedidaView;

import static app.com.example.wagner.meupredi.R.layout.tab_corpo_perfil;

/**
 * Created by wagne on 12/02/2018.
 */

public class TabCorpo extends Activity{

    private TextView pesoAtual, chamadaPeso, ultimaMedicao, imcAtual;
    private TextView informativoIMC, pesoIdeal, statusIMC;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(tab_corpo_perfil);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        double imc = paciente.getImc();

        pesoAtual = (TextView) findViewById(R.id.text_tab_corpo_peso_atual);
        chamadaPeso = (TextView) findViewById(R.id.text_tab_corpo_atualizar_peso);
        ultimaMedicao = (TextView) findViewById(R.id.text_tab_corpo_peso_ultima_medicao);
        imcAtual = (TextView) findViewById(R.id.text_tab_corpo_imc_atual);
        informativoIMC = (TextView) findViewById(R.id.text_chamada_info_imc);
        pesoIdeal = (TextView) findViewById(R.id.text_qual_meu_peso_ideal);
        statusIMC = (TextView) findViewById(R.id.text_tab_corpo_status_imc);

        pesoAtual.setText(String.format("%.2f", paciente.getPeso()));
        imcAtual.setText(String.valueOf(imc));

        if(paciente.getImc() <= 16.9){
            statusIMC.setText("Muito abaixo do Peso");
        }
        else if(paciente.getImc() <= 18.4){
            statusIMC.setText("Abaixo do peso");
        }
        else if(paciente.getImc() <= 24.9){
            statusIMC.setText("Peso normal");
        }
        else if(paciente.getImc() <= 29.9){
            statusIMC.setText("Acima do peso");
        }
        else if(paciente.getImc() <= 34.9){
            statusIMC.setText("Obesidade I");
        }
        else if(paciente.getImc() <= 40){
            statusIMC.setText("Obesidade II");
        }
        else{
            statusIMC.setText("Obesidade III");
        }
        ultimaMedicao.setText("Ultima medição: 01/01/1900");
        MedidaController.getMedida(paciente)
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Medida medida = queryDocumentSnapshots.toObjects(Medida.class).get(0);
                    String date = medida.getDateMedida().replace("-", "/").split(" ")[1];

                    ultimaMedicao.setText("Ultima medição: "+date);
                }
            });

        pesoAtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPeso = new Intent(TabCorpo.this, MedidaView.class);
                chamadaPeso.putExtra("Paciente", paciente);
                startActivity(chamadaPeso);
            }
        });

        chamadaPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPeso = new Intent(TabCorpo.this, MedidaView.class);
                chamadaPeso.putExtra("Paciente", paciente);
                startActivity(chamadaPeso);
            }
        });

        informativoIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaTabelaImc = new Intent(TabCorpo.this, TabelaImc.class);
                chamadaTabelaImc.putExtra("Paciente", paciente);
                startActivity(chamadaTabelaImc);
            }
        });

        pesoIdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamadaPesoIdeal = new Intent(TabCorpo.this, PesoIdeal.class);
                chamadaPesoIdeal.putExtra("Paciente", paciente);
                startActivity(chamadaPesoIdeal);
            }
        });

    }
}