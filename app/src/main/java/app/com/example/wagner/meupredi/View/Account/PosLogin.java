package app.com.example.wagner.meupredi.View.Account;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Controller.MedidaController;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;
import app.com.example.wagner.meupredi.View.Application.MainViews.Perfil;

/**
 * Created by Pichau on 08/04/2017.
 */

public class PosLogin extends AppCompatActivity {

    public static final String PREFS_NAME = "Preferences";
    private TextView nomeUsuario;
    private EditText altura;
    private EditText peso;
    private EditText circunferecia;
    private Button concluir;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poslogin);

        nomeUsuario = (TextView) findViewById(R.id.text_nome_usuario_poslogin);
        altura = (EditText) findViewById(R.id.edit_altura_poslogin);
        altura.setRawInputType(Configuration.KEYBOARD_QWERTY);
        peso = (EditText) findViewById(R.id.edit_peso_poslogin);
        peso.setRawInputType(Configuration.KEYBOARD_QWERTY);
        circunferecia = (EditText) findViewById(R.id.edit_circunferencia_poslogin);
        circunferecia.setRawInputType(Configuration.KEYBOARD_QWERTY);
        concluir = (Button) findViewById(R.id.btn_concluir_poslogin);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        nomeUsuario.setText("Sr(a). " + paciente.getNome());

        //DEBUG: imprime dados do usuario pegos do banco
        Log.d("Se ja existe: ", "poslogin");
        Log.d("Nome : ", paciente.getNome());
        Log.d("Email: ", paciente.getEmail());
        Log.d("Sexo: ", String.valueOf(paciente.getSexo()));
        Log.d("Nascimento: ", paciente.printNascimento());
        Log.d("Circunferencia : ", String.valueOf(paciente.getCircunferencia()));
        Log.d("Peso : ", String.valueOf(paciente.getPeso()));
        Log.d("Altura : ", String.valueOf(paciente.getAltura()));
        Log.d("IMC : ", String.valueOf(paciente.getImc()));
        Log.d("GlicoseJejum : ", String.valueOf(paciente.getGlicoseJejum()));
        Log.d("Glicose75g : ", String.valueOf(paciente.getGlicose75g()));
        Log.d("Colesterol : ", String.valueOf(paciente.getColesterol()));

        //se o usuario ja fez o cadastro dos dados, pula esta tela
        if(paciente.getPeso() > 0 && paciente.getAltura() > 0 && paciente.getCircunferencia() > 0) {

            Intent intent = new Intent(PosLogin.this, Perfil.class);
            finish();
            startActivity(intent);
        }

        findViewById(R.id.tela_pos_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(altura.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(peso.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(circunferecia.getWindowToken(), 0);
                }
            }
        });

        concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean flag = false;

                String alturaCadastro = altura.getText().toString();
                String pesoCadastro = peso.getText().toString();
                String circunferenciaCadastro = circunferecia.getText().toString();

                //se o usuario nao preencheu algum dado, deixa como -1

                if (alturaCadastro.length()==0){
                    flag = true;
                } else {

                    //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a duas casas decimais)
                    alturaCadastro = alturaCadastro.replace(',', '.');
                    Double alturaAtualizada = Double.parseDouble(alturaCadastro);

                    //transforma em metros
                    while(alturaAtualizada > 10) {
                        alturaAtualizada /= 10;
                    }

                    String alturaFormatada = String.format(Locale.ENGLISH, "%.3f", alturaAtualizada);
                    Double alturaDoPaciente = Double.parseDouble(alturaFormatada);

                    //atualiza altura no objeto
                    paciente.setAltura(alturaDoPaciente);
                } if (pesoCadastro.length()==0){
                    flag = true;
                } else {

                    //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                    pesoCadastro = pesoCadastro.replace(',', '.');
                    double pesoAtualizado = Double.parseDouble(pesoCadastro);
                    Log.d("Peso atualizado : " , String.valueOf(pesoAtualizado));
                    String pesoFormatado = String.format(Locale.ENGLISH, "%.2f", pesoAtualizado);
                    float pesoDoPaciente = Float.parseFloat(pesoFormatado);
                    paciente.setPeso(pesoDoPaciente);

                } if (circunferenciaCadastro.length()==0){
                    flag = true;
                } else {

                    //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                    circunferenciaCadastro = circunferenciaCadastro.replace(',', '.');
                    double circunferenciaAtualizada = Double.parseDouble(circunferenciaCadastro);
                    String circunferenciaFormatada = String.format(Locale.ENGLISH, "%.2f", circunferenciaAtualizada);
                    double circunferenciaDoPaciente = Double.parseDouble(circunferenciaFormatada);

                    //atualiza circunferencia no objeto
                    paciente.setCircunferencia(circunferenciaDoPaciente);
                }

                //calculo de IMC
                if(paciente.getPeso() > 0 && paciente.getAltura() > 0) {

                    double imc = (paciente.getPeso()/(paciente.getAltura()*paciente.getAltura()));
                    String imcFormatado = String.format(Locale.ENGLISH, "%.2f", imc);
                    imc = Double.parseDouble(imcFormatado);
                    paciente.setImc(imc);
                } else {
                    paciente.setImc(0);
                }

                //se o usuario nao preencheu algum dos dados, avisa que ele pode preencher depois
                if(flag) {
                    Toast.makeText(getApplicationContext(),"Você pode completar ou atualizar" +
                            " esses dados em seu Perfil" +
                            " quando quiser.",Toast.LENGTH_LONG).show();
                }

                //pega peso cadastrado pelo paciente na tela e insere em sua respectiva tabela no banco
                if(pesoCadastro.length() != 0 || circunferenciaCadastro.length() != 0){
                    MedidaController.addMedida(paciente);
                }

                //atualiza dados do usuario no banco
                PacienteController.atualizarPaciente(paciente)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Sucesso","Editar pos-login");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Erro","Editar pos-login");
                        }
                    });

                //DEBUG: imprime os dados do paciente para verificar se estao corretos
                Log.d("Sincronizado: ", "poslogin");
                Log.d("Nome : ", paciente.getNome());
                Log.d("Email: ", paciente.getEmail());
                Log.d("Sexo: ", String.valueOf(paciente.getSexo()));
                Log.d("Nascimento: ", paciente.printNascimento());
                Log.d("Circunferencia : ", String.valueOf(paciente.getCircunferencia()));
                Log.d("Peso : ", String.valueOf(paciente.getPeso()));
                Log.d("Altura : ", String.valueOf(paciente.getAltura()));
                Log.d("IMC : ", String.valueOf(paciente.getImc()));
                Log.d("GlicoseJejum : ", String.valueOf(paciente.getGlicoseJejum()));
                Log.d("Glicose75g : ", String.valueOf(paciente.getGlicose75g()));
                Log.d("Colesterol : ", String.valueOf(paciente.getColesterol()));

                Intent intent = new Intent(PosLogin.this, Perfil.class);
                finish();
                startActivity(intent);
            }

        });
    }
}