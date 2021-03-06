package app.com.example.wagner.meupredi.View.Account;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 10/04/2017.
 */

public class EsqueceuSenha extends AppCompatActivity {

    private Button enviar;
    private TextView cancelar;
    private EditText email;
    private ConstraintLayout tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);

        enviar = (Button) findViewById(R.id.btn_enviar_esqueceu);
        cancelar = (TextView) findViewById(R.id.btn_cancelar_esqueceu);
        email = (EditText) findViewById(R.id.edit_email_esqueceu);
        tela = (ConstraintLayout) findViewById(R.id.tela_esqueceu_senha);

        FirebaseAuth auth = FirebaseAuth.getInstance();


        findViewById(R.id.tela_esqueceu_senha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
                }
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(email.getText().toString().toLowerCase());
                Toast.makeText(getApplicationContext(), "Email de recuperação de senha enviado!", Toast.LENGTH_LONG).show();

                finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
