package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.TelaLogin;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;
import app.com.example.wagner.meupredi.View.Application.MainViews.Perfil;

/**
 * Created by LeandroDias1 on 18/04/2017.
 */

public class Sair extends Activity {

    Button sairSIM;
    TextView sairNAO;
    Paciente paciente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sair);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        sairSIM = (Button) findViewById(R.id.btn_sair_sim_fragment);
        sairNAO = (TextView) findViewById(R.id.btn_sair_nao_fragment);

        sairNAO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sairSIM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("Preferences", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                PacienteUpdater.onEnd();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Sair.this, TelaLogin.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
