package app.com.example.wagner.meupredi.View.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

/**
 * Created by LeandroDias1 on 05/10/2017.
 */

public class TelaLoginSplash extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int SPLASH_TIME_OUT = 2000;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("pt-BR");
        FirebaseUser user = auth.getCurrentUser();

        SharedPreferences prefs = getSharedPreferences("Preferences", 0);
        boolean autoLogin = prefs.getBoolean("Manter conectado", false);

        if(user != null && autoLogin){
            //checa se o usuário já está logado
            PacienteController.getPaciente(user.getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        Paciente paciente = documentSnapshot.toObject(Paciente.class);
                        login(paciente);
                    } else {
                        Toast.makeText(TelaLoginSplash.this, "Email não cadastrado no banco de dados", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else {
            if(user != null && !autoLogin){
                auth.signOut();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(TelaLoginSplash.this, TelaLogin.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    private void login(Paciente paciente){
        PacienteUpdater.onStart(paciente);

        Intent it = new Intent(TelaLoginSplash.this, PosLogin.class);
        startActivity(it);
        finish();
    }

}
