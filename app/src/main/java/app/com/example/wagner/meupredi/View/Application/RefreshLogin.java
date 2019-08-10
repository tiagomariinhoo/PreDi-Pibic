package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.TelaLogin;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class RefreshLogin extends Activity {

    private EditText email, senha, confSenha;
    private TextView emailLabel, senhaLabel, confSenhaLabel;
    private CheckBox mostrarSenha, mostrarConfSenha;
    private ImageView confSenhaImage;
    private Button atualizar;
    private TextView title;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_login_info);

        auth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.edit_endereco_email_dados);
        senha = (EditText) findViewById(R.id.edit_senha_dados);
        mostrarSenha = (CheckBox) findViewById(R.id.checkBox_mostrar_senha_dados);
        confSenha = (EditText) findViewById(R.id.edit_conf_senha_dados);
        mostrarConfSenha = (CheckBox) findViewById(R.id.checkBox_mostrar_conf_senha_dados);
        emailLabel = (TextView) findViewById(R.id.text_endereco_email_dados);
        senhaLabel = (TextView) findViewById(R.id.text_senha_dados);
        confSenhaLabel = (TextView) findViewById(R.id.text_conf_senha_dados);
        confSenhaImage = (ImageView) findViewById(R.id.image_conf_senha);
        title = (TextView) findViewById(R.id.background_dados_header);
        atualizar = (Button) findViewById(R.id.btn_enviar_email_senha);

        findViewById(R.id.tela_editar_email_senha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(senha.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(confSenha.getWindowToken(), 0);
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

        mostrarConfSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    confSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    confSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        FirebaseUser user = auth.getCurrentUser();

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailLogin = email.getText().toString().toLowerCase();
                String senhaLogin = senha.getText().toString();

                if(!emailLogin.isEmpty() && !senhaLogin.isEmpty()) {
                    disableLogin();

                    AuthCredential credential = EmailAuthProvider.getCredential(emailLogin, senhaLogin);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            enableLogin();
                            if (task.isSuccessful()) {
                                changeToUpdateScreen(user);
                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                //email digitado inválido
                                Toast.makeText(RefreshLogin.this, "Credenciais inválidas", Toast.LENGTH_LONG).show();
                            } else {
                                Log.e("ERROR", task.getException().getClass().getName());
                                Toast.makeText(RefreshLogin.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RefreshLogin.this, "Por favor, preencha os campos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void enableLogin(){
        atualizar.setBackground(ContextCompat.getDrawable(this, R.drawable.borda_curvada_verde));
        atualizar.setEnabled(true);
    }

    private void disableLogin(){
        atualizar.setBackground(ContextCompat.getDrawable(this, R.drawable.borda_curvada_cinza));
        atualizar.setEnabled(false);
    }

    private void changeToUpdateScreen(FirebaseUser user){
        title.setText("Informe os novos dados");
        emailLabel.setText("Novo " + emailLabel.getText());
        senhaLabel.setText("Nova " + senhaLabel.getText());
        email.setText("");
        senha.setText("");
        senha.setNextFocusDownId(R.id.edit_conf_senha_dados);
        confSenhaImage.setVisibility(View.VISIBLE);
        confSenhaLabel.setVisibility(View.VISIBLE);
        confSenha.setVisibility(View.VISIBLE);
        mostrarConfSenha.setVisibility(View.VISIBLE);
        atualizar.setText("Atualizar");
        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableLogin();

                String novoEmail = email.getText().toString().toLowerCase();
                String novaSenha = senha.getText().toString();
                String novaConfSenha = confSenha.getText().toString();

                email.setText("");
                senha.setText("");
                confSenha.setText("");

                if(!novaSenha.isEmpty() && novaSenha.equals(novaConfSenha)){
                    updatePassword(user, novaSenha).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser user = auth.getCurrentUser();

                            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), novaSenha);

                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!novoEmail.isEmpty()){
                                        updateEmail(user, novoEmail);
                                        enableLogin();
                                        finish();
                                    }
                                }
                            });
                        }
                    });
                } else if(!novoEmail.isEmpty()){
                    updateEmail(user, novoEmail);
                    enableLogin();
                    finish();
                }

                if(!novaSenha.equals(novaConfSenha)){
                    Toast.makeText(getApplicationContext(), "Insira senhas iguais", Toast.LENGTH_SHORT).show();
                    enableLogin();
                }


            }
        });
    }

    private Task<Void> updateEmail(FirebaseUser user, String novoEmail){
        return user.updateEmail(novoEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        //email já faz parte de uma conta
                        Toast.makeText(RefreshLogin.this, "Email já possui uma conta associada", Toast.LENGTH_LONG).show();
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        //email digitado inválido
                        Toast.makeText(RefreshLogin.this, "Email inválido", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RefreshLogin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else{
                    user.sendEmailVerification();
                    Toast.makeText(RefreshLogin.this, "Confirmação de email enviada, verifique sua caixa de entrada", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Task<Void> updatePassword(FirebaseUser user, String novaSenha){
        return user.updatePassword(novaSenha).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                        //senha fraca (menos de 6 digitos)
                        String reason = ((FirebaseAuthWeakPasswordException) task.getException()).getReason();
                        Toast.makeText(RefreshLogin.this, reason, Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(RefreshLogin.this, "Senha alterada com sucesso", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
