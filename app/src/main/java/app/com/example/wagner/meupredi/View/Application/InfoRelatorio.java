package app.com.example.wagner.meupredi.View.Application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class InfoRelatorio extends AppCompatActivity {

    private TextView msgBalao, btnContinuar, btnVoltar;
    private ListView listaInfo;
    private Paciente paciente;

    private enum Variaveis{
        //Obs: se alguma dessas for desnecessária para o informativo, pode retirar
        //isso vai servir pra checar a variável que está sendo analisada atualmente
        //e para saber qual é a próxima variável ao clicar no botão
        Glicose75g, GlicoseJejum, Hemoglobina, Peso, Circunferencia
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_relatorio);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        msgBalao = findViewById(R.id.msg_tutorial_relatorio_info);
        btnContinuar = findViewById(R.id.btn_continuar_tutorial2);

        Variaveis variavelAtual = Variaveis.GlicoseJejum; // a variável que está sendo analisada

        carregarGlicoseJejum();

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });

    }

    /*
    * Carrega os dados necessários para o diagnóstico da glicose em jejum
    * nos componentes da tela.
    * TODO: completar esse método e criar métodos similares para as outras variáveis
    * */
    private void carregarGlicoseJejum(){
        String mensagem = "Inicialmente, o sistema precisa analisar sua taxa de Glicemia em Jejum," +
                " de acordo com o valor dela, vamos definir o próximo passo.";
        msgBalao.setText(mensagem);
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
