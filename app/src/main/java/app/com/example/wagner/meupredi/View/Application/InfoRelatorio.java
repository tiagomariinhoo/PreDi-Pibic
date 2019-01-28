package app.com.example.wagner.meupredi.View.Application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        msgBalao = findViewById(R.id.msg_tutorial_relatorio_info); // Vai indicar o contexto
        btnContinuar = findViewById(R.id.btn_continuar_tutorial2); // Proxima pagina (nó da arvore)
        listaInfo = findViewById(R.id.lista_relatorio_variavel_front);
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

        String caso1 = "Glicemia Normal";
        String caso2 = "Glicemia entre 100 e 125 mg/dL";
        String caso3 = "Glicemia acima de 126 mg/dL";

        List<String> lista = new ArrayList();
        lista.add(caso1);
        lista.add(caso2);
        lista.add(caso3);

        listaInfo.setAdapter(new ArrayAdapter(this, R.layout.lista_condicoes_itens, lista));

        double glicoseJejum = paciente.getGlicoseJejum();

    }

    private void carregarGlicose75g(){

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
