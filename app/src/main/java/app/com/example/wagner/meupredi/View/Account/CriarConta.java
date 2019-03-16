package app.com.example.wagner.meupredi.View.Account;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

public class CriarConta extends AppCompatActivity {

    private CheckBox boxSenha;
    private EditText nome, email, senha, conSenha;
    private Spinner sexo;
    private TextView data;
    private DatePickerDialog.OnDateSetListener dataNascimento;
    private Timestamp timestampNasc;
    TextView cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_criar_conta);

        boxSenha = (CheckBox) findViewById(R.id.checkedConSenha);
        nome = (EditText) findViewById(R.id.edit_nome_completo);
        email = (EditText) findViewById(R.id.edit_endereco_email);
        sexo = (Spinner) findViewById(R.id.spinner_sexo_postlogin);
        data = (TextView) findViewById(R.id.edit_nascimento_criar);
        data.setRawInputType(Configuration.KEYBOARD_QWERTY);
        senha = (EditText) findViewById(R.id.edit_senha_cadastro);
        conSenha = (EditText) findViewById(R.id.edit_novamente_senha);

        Button criarConta = (Button) findViewById(R.id.btn_criar_conta);
        cancelar = (TextView) findViewById(R.id.btn_cancelar);
        Calendar.getInstance();

        findViewById(R.id.tela_criar_conta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(nome.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(data.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(senha.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(conSenha.getWindowToken(), 0);
                    }
                }
            }
        });

        //lista de opcoes de sexo
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("M");
        spinnerArray.add("F");

        //configura o spinner do sexo
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sexo.setAdapter(adapter);

        boxSenha.setChecked(false);

        // ABRIR DIALOG DE INSERIR DATA DE NASCIMENTO
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CriarConta.this,
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
                    Log.e("Parse Error", "timestampNasc CriarConta");
                }
                data.setText(dataNasc);
            }
        };

        conSenha.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String senhaCadastro = senha.getText().toString();
                String conSenhaCadastro = conSenha.getText().toString();

                //verifica se as senhas sao iguais nos dois campos de cadastro
                if(senhaCadastro.length()==0){
                    Toast.makeText(getApplicationContext(),"Insira uma senha válida!", Toast.LENGTH_SHORT).show();
                    boxSenha.setChecked(false);
                    return false;
                }

                if(senhaCadastro.equals(conSenhaCadastro)){
                    boxSenha.setChecked(true);

                    //esconde teclado
                    try {
                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch(NullPointerException e) {
                        //caso o teclado ja esteja escondido
                    }
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(),"Insira senhas iguais!", Toast.LENGTH_SHORT).show();
                    boxSenha.setChecked(false);
                    return false;
                }
            }
        });

        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: verificar email ao cadastrar (enviar email de confirmacao)

                String nomeCompleto = nome.getText().toString();
                String emailCadastro = email.getText().toString();
                String dataCadastro = data.getText().toString();
                String senhaCadastro = senha.getText().toString();
                String conSenhaCadastro = conSenha.getText().toString();
                //fazer checagem de email antes evita erro do firebase
                if(emailCadastro.length() == 0){
                    Toast.makeText(getApplicationContext(), "Insira um email válido!", Toast.LENGTH_SHORT).show();
                } else {
                    //verificando se email ja foi cadastrado
                    PacienteController.getPaciente(emailCadastro)
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot != null && !documentSnapshot.exists()) {
                                    //email não cadastrado
                                    novoPaciente(nomeCompleto, emailCadastro, dataCadastro, senhaCadastro, conSenhaCadastro);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Email já cadastrado!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void novoPaciente(String nomeCompleto, String emailCadastro, String dataCadastro, String senhaCadastro, String conSenhaCadastro){
        //verifica se todos os campos estao preenchidos
        if(nomeCompleto.length() == 0) {
            Toast.makeText(getApplicationContext(), "Insira um nome válido!", Toast.LENGTH_SHORT).show();
        } else if(timestampNasc == null){
            Toast.makeText(getApplicationContext(), "Data em formato inválido!\nPor favor, digite no formato dd/mm/aaaa.", Toast.LENGTH_SHORT).show();
        } else if(senhaCadastro.length() == 0) {
            Toast.makeText(getApplicationContext(), "Insira uma senha válida!", Toast.LENGTH_SHORT).show();
        } else if(senhaCadastro.equals(conSenhaCadastro)) {
            Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

            String sexoCadastro = sexo.getSelectedItem().toString();

            if (!sexoCadastro.equals("M") && !sexoCadastro.equals("F")) {
                sexoCadastro = "";
            }

            //configuracao padrao de usuario
            Paciente paciente = new Paciente(nomeCompleto, senhaCadastro, emailCadastro, sexoCadastro, timestampNasc, -1);

            //DEBUG: imprime todos os dados do paciente
            Log.d("Criando", "criar conta");
            Log.d("Nome", paciente.getNome());
            Log.d("Senha", paciente.getSenha());
            Log.d("Email", paciente.getEmail());
            Log.d("Sexo", String.valueOf(paciente.getSexo()));
            Log.d("Nascimento", paciente.printNascimento());
            Log.d("Circunferencia", String.valueOf(paciente.getCircunferencia()));
            Log.d("Peso", String.valueOf(paciente.getPeso()));
            Log.d("Altura", String.valueOf(paciente.getAltura()));
            Log.d("IMC", String.valueOf(paciente.getImc()));
            Log.d("GlicoseJejum", String.valueOf(paciente.getGlicoseJejum()));
            Log.d("Glicose75g", String.valueOf(paciente.getGlicose75g()));
            Log.d("Colesterol", String.valueOf(paciente.getColesterol()));

            PacienteController.addPaciente(paciente)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Registro inserido com sucesso!", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erro ao inserir o registro!", Toast.LENGTH_LONG).show();
                    }
                });

            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Insira senhas iguais!", Toast.LENGTH_LONG).show();
        }
    }
}
