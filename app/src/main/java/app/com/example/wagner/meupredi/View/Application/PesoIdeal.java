package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by leandro on 10/04/18.
 */

public class PesoIdeal extends Activity  {

    Double imc = 0.00;
    Double emagrecer = 0.00;

    String perder_ganhar = "perder";
    String condicao = "acima";

    String PesoIdeal = "De acordo com a sua altura e idade, o seu peso ideal está entre 53kg e 71 kg.";
    String imcRuim = "Seu imc é de " + imc + " kg/m², por isso você está "+ condicao + " do seu peso ideal e deveria" +
            perder_ganhar + " pelo menos " + emagrecer + " kg";
    String imcBom = "Seu IMC é de " + imc + " kg/m², por isso você está " + condicao + " do seu peso ideal!";
    String imcMedio = "Seu imc é de " + imc + " kg/m², por isso você está "+ condicao + " do seu peso ideal e deveria" +
            perder_ganhar + " pelo menos " + emagrecer + " kg";

    String circunferenciaIdeal = "";

    Paciente paciente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.peso_ideal);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        if(paciente.getSenha().equals("M")){
            if(paciente.getCircunferencia() < 94){
                circunferenciaIdeal = "Baixo risco de complicações metabólicas!";
            } else if(paciente.getCircunferencia() >= 94 && paciente.getCircunferencia() <= 101){
                circunferenciaIdeal = "Risco aumentado de complicações metabólicas!";
            } else {
                circunferenciaIdeal = "Risco muito aumentado de complicações metabólicas!";
            }
        } else {
            if(paciente.getCircunferencia() < 80){
                circunferenciaIdeal = "Baixo risco de complicações metabólicas!";
            } else if(paciente.getCircunferencia() >= 80 && paciente.getCircunferencia() <= 87){
                circunferenciaIdeal = "Risco aumentado de complicações metabólicas!";
            } else {
                circunferenciaIdeal = "Risco muito aumentado de complicações metabólicas!";
            }
        }

        getWindow().setLayout((int) ( width*.8), (int) (height*.40));

    }
}
