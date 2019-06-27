package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;

public class Dados extends AppCompatActivity {

    private EditText nome;
    private TextView data;
    private EditText altura;
    private Button atualizar;
    private Paciente paciente;
    private Timestamp timestampNasc;
    private DatePickerDialog.OnDateSetListener dataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nome = (EditText) findViewById(R.id.edit_nome_dados);

        data = (TextView) findViewById(R.id.edit_nascimento_dados);
        data.setRawInputType(Configuration.KEYBOARD_QWERTY);

        altura = (EditText) findViewById(R.id.edit_altura_dados);
        altura.setRawInputType(Configuration.KEYBOARD_QWERTY);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        //se o usuario ja preencheu algum dado, preenche como hint
        if(paciente.getNome().length() != 0) {
            nome.setHint(paciente.getNome());
        }

        if(paciente.getNascimento() != null) {
            data.setHint(paciente.printNascimento());
        } else {
            data.setHint("data de nascimento não cadastrada");
        }

        // ABRIR DIALOG DE INSERIR DATA DE NASCIMENTO
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                calendario.setTime(paciente.getNascimento().toDate());
                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Dados.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        dataNascimento, ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dataNascimento = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //mês vem de 0 a 11, então +1 pra corrigir na exibição
                month += 1;
                String dataNasc = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month, year);
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    timestampNasc = new Timestamp(format.parse(dataNasc));
                } catch (ParseException e) {
                    timestampNasc = null;
                    Log.e("Parse Error", "timestampNasc Dados");
                }
                data.setText(dataNasc);
            }
        };

        if(!Double.isNaN(paciente.getAltura())) {
            altura.setHint(paciente.stringAltura() + " m");
        } else {
            altura.setHint("altura não cadastrada");
        }

        findViewById(R.id.tela_dados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(data.getWindowToken(), 0);
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
                String dataNascAtual = data.getText().toString();

                if(dataNascAtual.length() != 0 && timestampNasc != null) {
                    paciente.setNascimento(timestampNasc);
                    data.setHint(String.valueOf(paciente.printNascimento()));
                    data.setText("");
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
                    altura.setHint(paciente.stringAltura() + " m");
                    altura.setText("");
                }

                //atualiza a idade, altura e o imc do paciente no banco
                PacienteController.atualizarPaciente(paciente).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Dados atualizados com sucesso!",Toast.LENGTH_SHORT).show();
                    }
                });

                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
