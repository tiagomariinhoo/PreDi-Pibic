package app.com.example.wagner.meupredi.View.Application.Popups;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

/**
 * Created by leandro on 10/04/18.
 */

public class PopIMC extends Activity  {

    Paciente paciente;
    private TextView msg, fechar;

    String condicao_imc(double imc){

        String condicao = "";

        if(imc < 18.5){
            condicao = "Magreza";
        }
        else if(imc <= 24.9){
            condicao = "Saudável";
        }
        else if(imc <= 29.9){
            condicao = "Sobrepeso";
        }
        else if(imc <= 34.9){
            condicao = "Obesidade grau I";
        }
        else if(imc <= 39.9){
            condicao = "Obesidade grau II";
        }
        else if(imc > 40){
            condicao = "Obesidade grau III";
        }

        return condicao;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.peso_ideal);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");
        double imc = paciente.getImc();
        msg = findViewById(R.id.txt_imc);
        fechar = findViewById(R.id.txt_fechar_imc);

        String complemento = condicao_imc(imc);

        msg.setText("Seu Índice de Massa Corporal é de " + imc + '\n' + complemento);

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getWindow().setLayout((int) ( width*.8), (int) (height*.60));

    }
}