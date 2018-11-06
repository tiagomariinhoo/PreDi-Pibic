package app.com.example.wagner.meupredi.View.Application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.R;

public class StartRelatorio extends AppCompatActivity {

    private TextView msgBalao, btnContinuar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_start_relatorio);

        msgBalao = findViewById(R.id.msg_tutorial_relatorio);
        btnContinuar = findViewById(R.id.btn_continuar_tutorial1);
        btnVoltar = findViewById(R.id.text_voltar_tutorial1);

        String mensagem = "Bem vindo ao Tutorial do meuPredi, vamos explicar e diagnósticar seu status físico " +
                "com relação a doença Prédiabetes, por favor aperte em Continuar";

        msgBalao.setText(mensagem);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartRelatorio.this, InfoRelatorio.class);
                startActivity(intent);
            }
        });

    }
}