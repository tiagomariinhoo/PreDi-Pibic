package app.com.example.wagner.meupredi.View.Application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import app.com.example.wagner.meupredi.R;

public class InfoRelatorio extends AppCompatActivity {

    private TextView msgBalao, variavelAnalisada, btnContinuar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_relatorio);

    }
}
