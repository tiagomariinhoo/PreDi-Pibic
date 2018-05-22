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

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.TelaLogin;

/**
 * Created by LeandroDias1 on 18/04/2017.
 */

public class Sair extends Activity {

    CheckBox manterConectado;
    Button sair;
    TextView voltar;
    Paciente paciente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sair);

        sair = (Button) findViewById(R.id.btn_sair_sim_fragment);
        //manterConectado = (CheckBox) findViewById(R.id.checkBox_manter_conectado_login);
        //manterConectado.setChecked(false);

        final SharedPreferences settings = this.getSharedPreferences(TelaLogin.PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                editor.clear();
                editor.commit();

                Intent intent = new Intent(Sair.this, TelaLogin.class);
                startActivity(intent);
            }
        });
    }

}
