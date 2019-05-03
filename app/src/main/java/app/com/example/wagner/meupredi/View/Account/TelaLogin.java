package app.com.example.wagner.meupredi.View.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class TelaLogin extends AppCompatActivity {

    private EditText usuario, senha;
    private Button btnLogin;
    private TextView textCriar;
    private Switch manterConectado;
    private CheckBox mostrarSenha;
    private TextView esqueceuSenha;
    private FirebaseAuth auth;

    //TODO: remover botão de manterConectado aqui e no xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PacienteUpdater.onEnd(); // makes sure PacienteUpdater stops when going to the login screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        auth = FirebaseAuth.getInstance();

        usuario = findViewById(R.id.edit_usuario_login);
        senha = findViewById(R.id.edit_senha_login);
        manterConectado = findViewById(R.id.checkBox_manter_conectado_login);
        btnLogin = findViewById(R.id.btn_login);
        textCriar = findViewById(R.id.text_criar_conta);
        mostrarSenha = findViewById(R.id.checkBox_senha_login);
        esqueceuSenha = findViewById(R.id.text_esqueceu_senha_login);

        findViewById(R.id.tela_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!= null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(senha.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(usuario.getWindowToken(), 0);
                    }
                }
            }
        });

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

        textCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreCriarConta = new Intent(TelaLogin.this, CriarConta.class);
                startActivity(abreCriarConta);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                disableLogin();

                String email,pass;
                //toLower é usado pois email só utiliza caracteres minúsculos
                email = usuario.getText().toString().toLowerCase();
                pass = senha.getText().toString();
                if(email.isEmpty()){
                    showLoginError("Insira um email");
                } else if(pass.isEmpty()) {
                    showLoginError("Insira uma senha");
                } else {
                    auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();
                                    if(user.isEmailVerified()) {
                                        PacienteController.getPaciente(user.getEmail()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    Paciente paciente = documentSnapshot.toObject(Paciente.class);
                                                    setInfoAndFinish(paciente);
                                                } else {
                                                    showLoginError("Email não cadastrado no banco de dados");
                                                }
                                            }
                                        });
                                    } else {
                                        showLoginError("Email não confirmado\nFavor checar a sua caixa de entrada");
                                    }
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                        showLoginError("Email não cadastrado");
                                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        showLoginError("Email inválido ou Senha incorreta");
                                    }
                                }
                            }
                        });
                }
            }
        });

        esqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, EsqueceuSenha.class);
                startActivity(intent);
            }
        });
    }

    private void setInfoAndFinish(Paciente paciente){
        PacienteUpdater.onStart(paciente);

        SharedPreferences prefs = getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Manter conectado", manterConectado.isChecked());
        editor.apply();

        Intent it = new Intent(TelaLogin.this, PosLogin.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

    private void showLoginError(String error){
        Toast.makeText(TelaLogin.this, error, Toast.LENGTH_LONG).show();
        enableLogin();
    }

    private void enableLogin(){
        btnLogin.setBackground(ContextCompat.getDrawable(this, R.drawable.borda_curvada_verde));
        btnLogin.setEnabled(true);
    }

    private void disableLogin(){
        btnLogin.setBackground(ContextCompat.getDrawable(this, R.drawable.borda_curvada_cinza));
        btnLogin.setEnabled(false);
    }

}