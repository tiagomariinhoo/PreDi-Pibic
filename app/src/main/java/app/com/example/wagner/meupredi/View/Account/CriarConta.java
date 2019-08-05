package app.com.example.wagner.meupredi.View.Account;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;

public class CriarConta extends AppCompatActivity {

    private EditText nome, email, senha, conSenha;
    private CheckBox mostrarSenha, mostrarConSenha;
    private Spinner sexo;
    private TextView data;
    private DatePickerDialog.OnDateSetListener dataNascimento;
    private Timestamp timestampNasc;
    private TextView cancelar;
    private FirebaseAuth auth;

    //TODO: senha não ta aparecendo escondida por padrão e checkbox de esconder não está funcionando
    //TODO: talvez fazer cadastro que não seja só email e senha após o primeiro login seja melhor

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_criar_conta);

        auth = FirebaseAuth.getInstance();

        nome = (EditText) findViewById(R.id.edit_nome_completo);
        email = (EditText) findViewById(R.id.edit_endereco_email);
        sexo = (Spinner) findViewById(R.id.spinner_sexo_postlogin);
        data = (TextView) findViewById(R.id.edit_nascimento_criar);
        data.setRawInputType(Configuration.KEYBOARD_QWERTY);
        senha = (EditText) findViewById(R.id.edit_senha_cadastro);
        mostrarSenha = (CheckBox) findViewById(R.id.checkBox_mostrar_senha);
        conSenha = (EditText) findViewById(R.id.edit_novamente_senha);
        mostrarConSenha = (CheckBox) findViewById(R.id.checkBox_mostrar_conf_senha);

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

        mostrarSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        mostrarConSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    conSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    conSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

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

        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: verificar email ao cadastrar (enviar email de confirmacao)?

                String nomeCompleto = nome.getText().toString();
                String emailCadastro = email.getText().toString().toLowerCase();
                String senhaCadastro = senha.getText().toString();
                String conSenhaCadastro = conSenha.getText().toString();

                //fazer checagem de email antes evita erro do firebase
                if(emailCadastro.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Insira um email válido!", Toast.LENGTH_SHORT).show();
                } else if(senhaCadastro.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Insira uma senha válida!", Toast.LENGTH_SHORT).show();
                } else if(!senhaCadastro.equals(conSenhaCadastro)){
                    Toast.makeText(getApplicationContext(), "Insira senhas iguais", Toast.LENGTH_SHORT).show();
                } else if(nomeCompleto.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Insira um nome válido!", Toast.LENGTH_SHORT).show();
                } else if(timestampNasc == null) {
                    Toast.makeText(getApplicationContext(), "Data em formato inválido!\nPor favor, digite no formato dd/MM/aaaa.", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(emailCadastro, senhaCadastro)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("createUserWithEmail", "success, user: " + emailCadastro);
                                    FirebaseUser user = task.getResult().getUser();
                                    novoPaciente(user, nomeCompleto, timestampNasc);
                                    user.sendEmailVerification();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                        //senha fraca (menos de 6 digitos)
                                        String reason = ((FirebaseAuthWeakPasswordException) task.getException()).getReason();
                                        Toast.makeText(CriarConta.this, reason, Toast.LENGTH_LONG).show();
                                    } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        //email já faz parte de uma conta
                                        Toast.makeText(CriarConta.this, "Email já possui uma conta associada", Toast.LENGTH_LONG).show();
                                    } else {
                                        //email digitado inválido
                                        Toast.makeText(CriarConta.this, "Email inválido", Toast.LENGTH_LONG).show();
                                    }
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

    private void novoPaciente(FirebaseUser user, String nomeCompleto, Timestamp dataNasc){
        //verifica se todos os campos estao preenchidos
        String sexoCadastro = sexo.getSelectedItem().toString();

        if (!sexoCadastro.equals("M") && !sexoCadastro.equals("F")) {
            sexoCadastro = "";
        }

        //configuracao padrao de usuario
        Paciente paciente = new Paciente(user.getUid(), nomeCompleto, sexoCadastro, dataNasc);

        //DEBUG: imprime todos os dados do paciente
        Log.d("Criando", "criar conta");
        Log.d("UID", paciente.getUid());
        Log.d("Nome", paciente.getNome());
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
                    Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("SignUpError", e.getMessage());
                    user.delete();
                    Toast.makeText(getApplicationContext(), "Erro ao cadastrar usuário!", Toast.LENGTH_LONG).show();
                }
            });

        finish();
    }
}
