package app.com.example.wagner.meupredi.View.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Controller.ControllerExames;
import app.com.example.wagner.meupredi.Controller.ControllerPaciente;
import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.PesoClass;
import app.com.example.wagner.meupredi.R;

public class TelaLogin extends AppCompatActivity {

    public static final String PREFS_NAME = "Preferences";
    EditText usuario, senha;
    Button btnLogin;
    TextView textCriar;
    CheckBox mostrarSenha, manterConectado;
    ConstraintLayout tela;
    TextView esqueceuSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tela_login);


        usuario = (EditText) findViewById(R.id.edit_usuario_login);
        senha = (EditText) findViewById(R.id.edit_senha_login);
        manterConectado = (CheckBox) findViewById(R.id.checkBox_manter_conectado_login);
        btnLogin = (Button) findViewById(R.id.btn_login);
        textCriar = (TextView) findViewById(R.id.text_criar_conta);
        mostrarSenha = (CheckBox) findViewById(R.id.checkBox_senha_login);
        tela = (ConstraintLayout) findViewById(R.id.tela_login);
        esqueceuSenha = (TextView) findViewById(R.id.text_esqueceu_senha_login);

        //pega informacoes salvas no sharedpreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        usuario.setText(settings.getString("PrefUsuario", ""));
        senha.setText(settings.getString("PrefSenha", ""));

        manterConectado.setChecked(false);

        //se existirem informacoes salvas, tenta fazer login
        if(usuario.getText().length() > 0 && senha.getText().length() > 0) {
            btnLogin.post(new Runnable() {
                @Override
                public void run() {
                    btnLogin.performClick();
                }
            });
        }

        findViewById(R.id.tela_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(senha.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(usuario.getWindowToken(), 0);
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

                //abre o banco e o sharedpreferences para edicao
               // DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                ControllerPeso controllerPeso = new ControllerPeso(getApplicationContext());

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                //DEBUG: imprime lista de pacientes cadastrados
                //List<Paciente> pacList = new ArrayList<Paciente> ();
                //pacList = controllerPaciente.getAllUsers();

                //Log.d("Tamanho pacList LOGIN: ", String.valueOf(pacList.size()));
               /* if(pacList.size() > 0){
                    for(int i=0;i<pacList.size();i++){
                        Log.d(pacList.get(i).get_nome()," -> Nome do paciente");
                        Log.d(pacList.get(i).get_email()," -> Email do paciente");
                        Log.d(pacList.get(i).get_senha(), " -> Senha do paciente");
                    }
                }*/

                //salva dados no sharedpreferences
                if(manterConectado.isChecked()) {
                    editor.putString("PrefUsuario", usuario.getText().toString());
                    editor.putString("PrefSenha", senha.getText().toString());

                    editor.commit();
                }

                //verifica credenciais do usuario
                String user,pass;
                user = usuario.getText().toString();
                pass = senha.getText().toString();

                ControllerPaciente.verificarLogin(user, pass)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshots) {
                        ControllerExames controllerExames = new ControllerExames(getApplicationContext());
                        //se estiverem corretas, faz o login
                        final Paciente paciente = controllerExames.getUltimasTaxas(querySnapshots.toObjects(Paciente.class).get(0));

                        //pega peso atual do paciente na tabela correspondente
                        controllerPeso.getPeso(paciente).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                PesoClass peso = queryDocumentSnapshots.toObjects(PesoClass.class).get(0);
                                Log.d("PESO NA TELA LOGIN  : ", String.valueOf(peso));
                                paciente.setPeso(peso.getPeso());
                            }
                        });

                        //Paciente pacTaxas = controllerExames.getUltimasTaxas(paciente);
                        //paciente = controllerExames.getUltimasTaxas(paciente);


                        Intent it = new Intent(TelaLogin.this, PosLogin.class);
                        it.putExtra("Paciente", paciente);

                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(it);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Usuário inválido!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        esqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, EsqueceuSenha.class);
                finish();
                startActivity(intent);
            }
        });
    }
}