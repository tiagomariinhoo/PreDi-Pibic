package app.com.example.wagner.meupredi.View.Application;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ControllerPaciente;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

public class Dados extends AppCompatActivity {

    EditText nome;
    EditText idade;
    EditText altura;
    EditText circunferecia;
    Button atualizar;
    Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nome = (EditText) findViewById(R.id.edit_nome_dados);

        idade = (EditText) findViewById(R.id.edit_idade_dados);
        idade.setRawInputType(Configuration.KEYBOARD_QWERTY);

        altura = (EditText) findViewById(R.id.edit_altura_dados);
        altura.setRawInputType(Configuration.KEYBOARD_QWERTY);

        circunferecia = (EditText) findViewById(R.id.edit_circunferencia_dados);
        circunferecia.setRawInputType(Configuration.KEYBOARD_QWERTY);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        //se o usuario ja preencheu algum dado, preenche como hint
        if(paciente.get_nome().length() != 0) {
            nome.setHint(paciente.get_nome());
        }

        if(paciente.get_idade() != -1) {
            idade.setHint(String.valueOf(paciente.get_idade()) + " anos");
        } else {
            idade.setHint("idade não cadastrada");
        }

        if(paciente.get_altura() != -1.0) {
            altura.setHint(String.valueOf(paciente.get_altura()) + " m");
        } else {
            altura.setHint("altura não cadastrada");
        }

        if(paciente.get_circunferencia() != -1.0) {
            circunferecia.setHint(String.valueOf(paciente.get_circunferencia()) + " cm");
        } else {
            circunferecia.setHint("circunferência não cadastrada");
        }

        findViewById(R.id.tela_dados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(idade.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(altura.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(circunferecia.getWindowToken(), 0);
                }
            }
        });

        atualizar = (Button) findViewById(R.id.btn_atualizar_dado);

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //atualiza nome
                String nomeAtual = nome.getText().toString();

                if(nomeAtual.length() != 0) {
                    paciente.set_nome(nomeAtual);
                    nome.setHint(String.valueOf(paciente.get_nome()));
                    nome.setText("");
                }

                //atualizar idade
                String idadeAtual = idade.getText().toString();

                if(idadeAtual.length() != 0) {
                    try {
                        paciente.set_idade(Integer.parseInt(idadeAtual));
                        idade.setHint(String.valueOf(paciente.get_idade()));
                        idade.setText("");
                    } catch(NumberFormatException e) {
                        Toast.makeText(getApplicationContext(),"Idade em formato incorreto!",Toast.LENGTH_SHORT).show();
                    }
                }

                //atualiza altura
                String alturaAtual = altura.getText().toString();

                if(alturaAtual.length() != 0) {

                    //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a duas casas decimais)
                    alturaAtual = alturaAtual.replace(',', '.');
                    Double alturaAtualizada = Double.parseDouble(alturaAtual);

                    //transforma em metros
                    while(alturaAtualizada > 10) {
                        alturaAtualizada /= 10;
                    }

                    String alturaFormatada = String.format(Locale.ENGLISH, "%.3f", alturaAtualizada);
                    Double alturaDoPaciente = Double.parseDouble(alturaFormatada);

                    //atualiza altura no objeto
                    paciente.set_altura(alturaDoPaciente);

                    //atualiza valor na tela
                    altura.setHint(String.valueOf(paciente.get_altura()) + " m");
                    altura.setText("");

                    //recalcula imc
                    if(paciente.get_peso() > 0 && paciente.get_altura() > 0) {

                        double imc = (paciente.get_peso()/(paciente.get_altura()*paciente.get_altura()));
                        String imcFormatado = String.format(Locale.ENGLISH, "%.2f", imc);
                        imc = Double.parseDouble(imcFormatado);
                        paciente.set_imc(imc);
                    } else {
                        paciente.set_imc(0);
                    }
                }

                //atualiza circunferencia
                String circunferenciaAtual = circunferecia.getText().toString();

                if(circunferenciaAtual.length() != 0) {

                    //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                    circunferenciaAtual = circunferenciaAtual.replace(',', '.');
                    Double circunferenciaAtualizada = Double.parseDouble(circunferenciaAtual);
                    String circunferenciaFormatada = String.format(Locale.ENGLISH, "%.2f", circunferenciaAtualizada);
                    Double circunferenciaDoPaciente = Double.parseDouble(circunferenciaFormatada);

                    //atualiza circunferencia no objeto
                    paciente.set_circunferencia(circunferenciaDoPaciente);

                    //atualiza valor na tela
                    circunferecia.setHint(String.valueOf(paciente.get_circunferencia()) + " cm");
                    circunferecia.setText("");
                }

                //atualiza a idade, altura, circunferencia e o imc do paciente no banco
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                ControllerPaciente controllerPaciente = new ControllerPaciente(getApplicationContext());
                controllerPaciente.atualizarPaciente(paciente);

                Toast.makeText(getApplicationContext(),"Dados atualizados com sucesso!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
