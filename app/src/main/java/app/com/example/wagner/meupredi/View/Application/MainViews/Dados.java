package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

public class Dados extends AppCompatActivity {

    EditText nome;
    EditText idade;
    EditText altura;
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

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        //se o usuario ja preencheu algum dado, preenche como hint
        if(paciente.getNome().length() != 0) {
            nome.setHint(paciente.getNome());
        }

        if(paciente.getIdade() != -1) {
            idade.setHint(String.valueOf(paciente.getIdade()) + " anos");
        } else {
            idade.setHint("idade não cadastrada");
        }

        if(paciente.getAltura() != -1.0) {
            altura.setHint(String.valueOf(paciente.getAltura()) + " m");
        } else {
            altura.setHint("altura não cadastrada");
        }

        findViewById(R.id.tela_dados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(idade.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(altura.getWindowToken(), 0);
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
                    paciente.setNome(nomeAtual);
                    nome.setHint(String.valueOf(paciente.getNome()));
                    nome.setText("");
                }

                //atualizar idade
                String idadeAtual = idade.getText().toString();

                if(idadeAtual.length() != 0) {
                    try {
                        paciente.setIdade(Integer.parseInt(idadeAtual));
                        idade.setHint(String.valueOf(paciente.getIdade()));
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
                    paciente.setAltura(alturaDoPaciente);

                    //atualiza valor na tela
                    altura.setHint(String.valueOf(paciente.getAltura()) + " m");
                    altura.setText("");

                    //recalcula imc
                    if(paciente.getPeso() > 0 && paciente.getAltura() > 0) {

                        double imc = (paciente.getPeso()/(paciente.getAltura()*paciente.getAltura()));
                        String imcFormatado = String.format(Locale.ENGLISH, "%.2f", imc);
                        imc = Double.parseDouble(imcFormatado);
                        paciente.setImc(imc);
                    } else {
                        paciente.setImc(0);
                    }
                }

                //atualiza a idade, altura e o imc do paciente no banco
                PacienteController.atualizarPaciente(paciente).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Dados atualizados com sucesso!",Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(Dados.this, Perfil.class);
                intent.putExtra("Paciente", paciente);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
