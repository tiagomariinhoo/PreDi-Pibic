package app.com.example.wagner.meupredi.View.Account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
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

                //verificando existencia do email no banco de dados
                PacienteController.getPaciente(email.getText().toString().trim())
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot != null && documentSnapshot.exists()) {
                                Paciente paciente = documentSnapshot.toObject(Paciente.class);
                                enviarEmail(paciente);
                            } else {
                                Toast.makeText(getApplicationContext(), "Email não cadatrado ou inválido!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void enviarEmail(Paciente paciente){
        //setando dados da mensagem
        String sender = email.getText().toString().trim();
        String subject = "MeuPreDi: recuperar senha";
        String message = "Sr(a) " + paciente.getNome().toString()
                + ", a opção de reenvio de senha foi solicitada com seu email. "
                + "Se você não fez essa solicitação, desconsidere esta mensagem.\n\n"
                + "Senha: " + paciente.getSenha().toString()
                + "\n\nEquipe MeuPreDi";

        //criando objeto do email
        SendMail sm = new SendMail(getApplicationContext(), sender, subject, message);

        //enviando email
        sm.execute();

        Toast.makeText(getApplicationContext(), "Email de recuperação de senha enviado!", Toast.LENGTH_LONG).show();

        finish();
    }
}
