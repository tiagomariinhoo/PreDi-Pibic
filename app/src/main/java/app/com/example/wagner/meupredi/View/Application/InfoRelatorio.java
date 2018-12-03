package app.com.example.wagner.meupredi.View.Application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class InfoRelatorio extends AppCompatActivity {

    private TextView msgBalao, variavelAtual, btnContinuar, btnVoltar;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_relatorio);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        msgBalao = findViewById(R.id.msg_tutorial_relatorio_info);
        //variavelAtual = findViewById(R.id.text_variavel_relatorio);

        String mensagem = "Inicialmente, o sistema precisa analisar sua taxa de Glicemia em Jejum," +
                " de acordo com o valor dela, vamos definir o próximo passo.";

        msgBalao.setText(mensagem);

        String nomeVariavelAtual = "Glicose em Jejum";
        //variavelAtual.setText(nomeVariavelAtual);

        double glicoseJejum = paciente.getGlicoseJejum();

    }

    public void onCardClick(View view) {
        flipCard();
    }

    private void flipCard() {
        View rootLayout = findViewById(R.id.main_activity_card);
        View cardFace = findViewById(R.id.main_activity_card_front);
        View cardBack = findViewById(R.id.main_activity_card_back);

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }
}
