package app.com.example.wagner.meupredi.View.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class TelaLogin extends AppCompatActivity {

    public static final String PREFS_NAME = "Preferences";
    EditText usuario, senha;
    Button btnLogin;
    TextView textCriar;
    Switch manterConectado;
    CheckBox mostrarSenha;
    //CheckBox mostrarSenha, manterConectado;
    ConstraintLayout tela;
    TextView esqueceuSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PacienteUpdater.onEnd(); // makes sure PacienteUpdater stops when going to the login screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        usuario = findViewById(R.id.edit_usuario_login);
        senha = findViewById(R.id.edit_senha_login);
        //manterConectado = findViewById(R.id.checkBox_manter_conectado_login);
        manterConectado = findViewById(R.id.checkBox_manter_conectado_login);
        btnLogin = findViewById(R.id.btn_login);
        textCriar = findViewById(R.id.text_criar_conta);
        mostrarSenha = findViewById(R.id.checkBox_senha_login);
        tela = findViewById(R.id.tela_login);
        esqueceuSenha = findViewById(R.id.text_esqueceu_senha_login);

        //pega informacoes salvas no sharedpreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        usuario.setText(settings.getString("PrefUsuario", ""));
        senha.setText(settings.getString("PrefSenha", ""));

        manterConectado.setChecked(usuario.getText().length() > 0 && senha.getText().length() > 0);

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

                //abre o sharedpreferences para edicao

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                //salva dados no sharedpreferences
                if(manterConectado.isChecked()) {
                    editor.putString("PrefUsuario", usuario.getText().toString());
                    editor.putString("PrefSenha", senha.getText().toString());
                    editor.apply();
                }

                //TODO: trocar cor do botão quando ele fica desativado (talvez um cinza fique bom)
                btnLogin.setEnabled(false); // Evitar 2 ou + cliques no botão de login

                //TODO: verificar se vale mais a pena colocar esse processamento em outra...
                //TODO: ...tela q seja só de carregamento já q demora tanto
                //verifica credenciais do usuario
                String user,pass;
                user = usuario.getText().toString();
                pass = senha.getText().toString();
                PacienteController.verificarLogin(user, pass).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Usuário inválido!", Toast.LENGTH_LONG).show();
                                btnLogin.setEnabled(true);
                            } else {
                                //se estiverem corretas, faz o login
                                Paciente paciente = queryDocumentSnapshots.toObjects(Paciente.class).get(0);
                                setInfoAndFinish(paciente);
                            }
                        }
                    });
            }
        });

        //se existirem informacoes salvas, tenta fazer login
        if(manterConectado.isChecked()) {
            btnLogin.post(new Runnable() {
                @Override
                public void run() {
                    btnLogin.performClick();
                }
            });
        }

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

        Intent it = new Intent(TelaLogin.this, PosLogin.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

}