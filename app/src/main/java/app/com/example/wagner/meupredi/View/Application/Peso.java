package app.com.example.wagner.meupredi.View.Application;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ControllerPaciente;
import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.MenuPrincipal;

/**
 * Created by Tiago on 27/06/2017.
 */

public class Peso extends AppCompatActivity{

    TextView peso, novoPeso;
    Button atualizarPeso;
    Paciente paciente;
    BarChart barChart;
    ArrayList<Float> pesos;
    ArrayList<BarEntry> barEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        paciente.getInfo();
        //peso = (TextView) findViewById(R.id.text_pesoAtual_valor_peso);
        //peso.setText(String.valueOf(paciente.get_peso()) + " kg");

        //TODO: criar calculo de meta
        //TODO: criar atributo de meta para guardar o peso que o paciente devera alcancar

        //meta = (TextView) findViewById(R.id.text_meta_valor_peso);

        //pega novo peso digitado pelo usuario
        novoPeso = (TextView) findViewById(R.id.text_registrar_valor_peso);

        Double peso_atual = paciente.get_peso();
        novoPeso.setText(peso_atual+"");

        novoPeso.setRawInputType(Configuration.KEYBOARD_QWERTY);

        findViewById(R.id.tela_peso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(novoPeso.getWindowToken(), 0);
                }
            }
        });

        atualizarPeso = (Button) findViewById(R.id.btn_atualizar_peso);

        atualizarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pega string do peso e verifica tamanho
                String pesoAtual = novoPeso.getText().toString();

                if(pesoAtual.length() == 0) {
                    Toast.makeText(getApplicationContext(),"Preencha o campo correspondente!",Toast.LENGTH_SHORT).show();
                } else {

                    //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                    pesoAtual = pesoAtual.replace(',', '.');
                    Double pesoAtualizado = Double.parseDouble(pesoAtual);
                    String pesoFormatado = String.format(Locale.ENGLISH, "%.2f", pesoAtualizado);
                    Double pesoDoPaciente = Double.parseDouble(pesoFormatado);

                    if(pesoDoPaciente > 0) {
                        //atualiza valor na tela
                        if(pesoDoPaciente == null){
                            peso.setText(String.valueOf(0));
                        } else {

                           //  peso.setText(String.valueOf(pesoDoPaciente) + " kg");
                        }

                        //atualiza peso no objeto
                        paciente.set_peso(pesoDoPaciente);

                        //recalcula imc
                        if(paciente.get_peso() > 0 && paciente.get_altura() > 0) {

                            double imc = (paciente.get_peso()/(paciente.get_altura()*paciente.get_altura()));
                            String imcFormatado = String.format(Locale.ENGLISH, "%.2f", imc);
                            imc = Double.parseDouble(imcFormatado);
                            paciente.set_imc(imc);
                        } else {
                            paciente.set_imc(0);
                        }

                        //atualiza o peso e o imc do paciente no banco
                        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        ControllerPeso controllerPeso = new ControllerPeso(getApplicationContext());
                        ControllerPaciente controllerPaciente = new ControllerPaciente(getApplicationContext());

                        controllerPeso.atualizarPeso(paciente);
                        controllerPaciente.atualizarPaciente(paciente);

                        Toast.makeText(getApplicationContext(),"Peso atualizado com sucesso!",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Peso.this, MenuPrincipal.class);
                        intent.putExtra("Paciente", paciente);
                        finish();
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(),"Peso inválido!",Toast.LENGTH_SHORT).show();
                    }

                    novoPeso.setText("");

                    try {
                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch(NullPointerException e) {
                        //caso o teclado ja esteja escondido
                    }
                }
            }
        });

        // Calculo Meta IMC //
        /*
            Abaixo do peso : < 20,7
            Peso normal : 20,7 - 26,4
            Marginalmente acima do peso 26,4 - 27,8
            Acima do peso ideal 27,8 - 31,1
            Obeso > 31,1
         */

        /*if(paciente.get_imc() > 26.4){
            meta.setText(String.valueOf(paciente.get_peso() - (paciente.get_peso()*0.05)));
        } else if (paciente.get_imc() < 20.7){
            meta.setText(String.valueOf(paciente.get_peso() + (paciente.get_peso()*0.05)));
        } else {
            meta.setText(String.valueOf('-'));
        }*/

        barChart = (BarChart) findViewById(R.id.bargraph_peso);
        //DatabaseHandler db = new DatabaseHandler (getApplicationContext());
        ControllerPeso controllerPeso = new ControllerPeso(getApplicationContext());

        pesos = controllerPeso.getAllPesos(paciente);

        for(int i=1;i<=10;i++) pesos.add(Float.valueOf(0));

        if(pesos.size()>0){
            for(int i=0;i<pesos.size();i++){
                barEntries.add(new BarEntry(Float.valueOf(pesos.get(i)),i));
            }
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        ArrayList<String> theDates = new ArrayList<>();

        for(int i=0;i<pesos.size();i++){
            theDates.add(String.valueOf(i));
        }

        barDataSet.setColor(Color.rgb(255, 182, 193));

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
