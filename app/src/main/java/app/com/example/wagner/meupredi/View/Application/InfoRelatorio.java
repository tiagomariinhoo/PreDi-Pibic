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
                carregarGlicoseJejum();
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
        //TODO: trocar esses aqui pra TextView
        String caso1 = "Glicemia Normal";
        String caso2 = "Glicemia entre 100 e 125 mg/dL";
        String caso3 = "Glicemia acima de 126 mg/dL";

        List<String> lista = new ArrayList();
        lista.add(caso1);
        lista.add(caso2);
        lista.add(caso3);

        listaInfo.setAdapter(new ArrayAdapter(this, R.layout.lista_condicoes_itens, lista));

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double glicoseAtual = paciente.getGlicoseJejum();
                String valorAtual = "Glicemia atual: " + glicoseAtual;
                lista.add(valorAtual);
                String mensagem;
                if(glicoseAtual < 100){
                    //TODO: ver se essa mensagem ficou boa
                    mensagem = "Sua glicemia atual está boa, mas devem ser feitas novas " +
                            "avaliações a cada 3 anos ou conforme o risco";
                    btnContinuar.setVisibility(View.INVISIBLE);
                } else if(glicoseAtual >= 100 && glicoseAtual <= 125){
                    //TODO: ver se essa mensagem ficou boa
                    mensagem = "Seu valor de glicemia em jejum apresenta possibilidade " +
                            "de diabetes, então iremos avaliar o seu ultimo teste de tolerância a " +
                            "glicose cadastrado";
                } else{
                    //TODO: ver se essa mensagem ficou boa
                    mensagem = "Sua glicemia está muito alta, você deve procurar um médico";
                    btnContinuar.setVisibility(View.INVISIBLE);
                }

                msgBalao.setText(mensagem);
            }
        });

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
