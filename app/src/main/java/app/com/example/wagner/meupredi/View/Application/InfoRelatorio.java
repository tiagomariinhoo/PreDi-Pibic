package app.com.example.wagner.meupredi.View.Application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class InfoRelatorio extends AppCompatActivity {

    private TextView msgBalao, btnContinuar, btnVoltar;
    private Paciente paciente;
    private List<CardFace> estados = new ArrayList<>();
    private int flipCount = 0;
    boolean testarGlicose75g = false;
    boolean testarHemoglobina = false;
    private boolean firstBack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_relatorio);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        msgBalao = findViewById(R.id.msg_tutorial_relatorio_info); // Vai indicar o contexto
        btnContinuar = findViewById(R.id.btn_continuar_tutorial2); // Proxima pagina (nó da arvore)
        btnVoltar = findViewById(R.id.text_btn_voltar_info_relatorio);

        carregarEstados();
        estados.get(0).setFront();

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardFace newCard = estados.get(flipCount+1);
                if(flipCount%2 == 0) {
                    newCard.setBack();
                } else{
                    newCard.setFront();
                }
                if(flipCount+1 >= estados.size()-1) btnContinuar.setVisibility(View.INVISIBLE);
                flipCard(true);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flipCount-1 < 0) finish();
                else {
                    //TODO: verificar pq o botão de voltar ta colocando a face pra a qual ele deveria voltar no lugar da atual antes de voltar
                    CardFace newCard = estados.get(flipCount - 1);
                    if(!firstBack) {
                        if (flipCount % 2 == 0) {
                            newCard.setBack();
                        } else {
                            newCard.setFront();
                        }
                    }
                    if (flipCount <= estados.size() - 1) btnContinuar.setVisibility(View.VISIBLE);
                    flipCard(false);
                }
            }
        });
    }

    private void carregarEstados(){
        if(Double.isNaN(paciente.getGlicoseJejum())){
            carregarInfoFaltando("Glicose em Jejum");
        } else carregarGlicoseJejum();
        if(testarGlicose75g) carregarGlicose75g();
        if(testarHemoglobina) carregarHemoglobina();
    }

    /*
    * Carrega os estados necessários para o diagnóstico da glicose em jejum
    * e coloca eles no array de estados.
    * */
    private void carregarGlicoseJejum(){
        String mensagem = "Inicialmente, o sistema precisa analisar sua taxa de Glicemia em Jejum," +
                " de acordo com o valor dela, vamos definir o próximo passo.";
        String variavel = "Glicose Jejum";
        msgBalao.setText(mensagem);
        String caso1 = "Glicemia Normal";
        String caso2 = "Glicemia entre 100 e 125 mg/dL";
        String caso3 = "Glicemia acima de 126 mg/dL";

        List<String> lista = new ArrayList<>();

        lista.add(caso1);
        lista.add(caso2);
        lista.add(caso3);

        CardFace first = new CardFace(mensagem, variavel, lista);

        estados.add(first);

        double glicoseAtual = paciente.getGlicoseJejum();
        if(Double.isNaN(glicoseAtual)) glicoseAtual = 0.0;
        String valorAtual = "Glicemia atual: " + String.valueOf(glicoseAtual).replace(".", ",") + " mg/dL";
        lista.add(valorAtual);
        if(glicoseAtual < 100){
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Sua glicemia em jejum atual está boa, mas devem ser feitas novas " +
                    "avaliações a cada 3 anos ou conforme o risco.";
        } else if(glicoseAtual >= 100 && glicoseAtual <= 125){
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Seu valor de glicemia em jejum apresenta possibilidade de diabetes, então " +
                    "iremos avaliar os dados do seu ultimo Teste de Tolerância a Glicose (TTG) " +
                    "cadastrado.";

            if(Double.isNaN(paciente.getGlicose75g())){
                carregarInfoFaltando("Glicose Após 75g");
            } else testarGlicose75g = true;
        } else{
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Sua glicemia em jejum está muito alta, você deve procurar um médico.";
        }
        CardFace second = new CardFace(mensagem, variavel, lista);

        estados.add(second);

    }

    /*
     * Carrega os estados necessários para o diagnóstico da glicose após 75g
     * e coloca eles no array de estados.
     * */
    private void carregarGlicose75g(){
        String mensagem = "Nesse passo, o sistema vai analisar sua taxa do Teste de Intolerância a " +
                "Glicose, que é medido 2 horas após ingestão de 75g de glicose, de acordo com o " +
                "resultado dele, vamos definir o próximo passo.";
        String variavel = "Glicose Após 75g";
        msgBalao.setText(mensagem);

        String caso1 = "Glicose Normal";
        String caso2 = "Glicose entre 140 e 199 mg/dL";
        String caso3 = "Glicose acima de 200 mg/dL";

        List<String> lista = new ArrayList<>();
        lista.add(caso1);
        lista.add(caso2);
        lista.add(caso3);

        CardFace first = new CardFace(mensagem, variavel, lista);

        estados.add(first);

        double glicoseAtual = paciente.getGlicose75g();
        if(Double.isNaN(glicoseAtual)) glicoseAtual = 0.0;
        String valorAtual = "Glicose atual: " + String.valueOf(glicoseAtual).replace(".", ",") + " mg/dL";
        lista.add(valorAtual);
        if(glicoseAtual < 140){
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Há chance de Glicemia de Jejum Alterada, você deve procurar um médico.\n" +
                    "No próximo passo, avaliaremos sua ultima taxa de Hemoglobina Glicada cadastrada.";

            if(Double.isNaN(paciente.getHemoglobinaGlicolisada())){
                carregarInfoFaltando("Hemoglobina Glicada");
            } else testarHemoglobina = true;
        } else if(glicoseAtual >= 140 && glicoseAtual <= 199){
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Há chance de Tolerância Diminuída à Glicose, você deve procurar um médico.\n" +
                    "No próximo passo, avaliaremos sua ultima taxa de Hemoglobina Glicada cadastrada.";

            if(Double.isNaN(paciente.getHemoglobinaGlicolisada())){
                carregarInfoFaltando("Hemoglobina Glicada");
            } else testarHemoglobina = true;
        } else{
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Sua glicose após 75g está muito alta, você deve procurar um médico.";
        }
        CardFace second = new CardFace(mensagem, variavel, lista);

        estados.add(second);

    }

    /*
     * Carrega os estados necessários para o diagnóstico da hemoglobina glicada
     * e coloca eles no array de estados.
     * */
    void carregarHemoglobina(){
        String mensagem = "Nesse passo, o sistema analisa sua taxa de Hemoglobina Glicada(HbA1c), " +
                "de acordo com o resultado dele, vamos definir o próximo passo.";
        String variavel = "Hemoglobina Glicada";
        msgBalao.setText(mensagem);

        String caso1 = "HbA1c Normal";
        String caso2 = "HbA1c entre 5,7% e 6,4%";
        String caso3 = "HbA1c acima de 6,5%";

        List<String> lista = new ArrayList<>();
        lista.add(caso1);
        lista.add(caso2);
        lista.add(caso3);

        CardFace first = new CardFace(mensagem, variavel, lista);

        estados.add(first);

        double hemoglobinaAtual = paciente.getHemoglobinaGlicolisada();
        if(Double.isNaN(hemoglobinaAtual)) hemoglobinaAtual = 0.0;
        String valorAtual = "HbA1c atual: " + String.valueOf(hemoglobinaAtual).replace(".", ",") + "%";
        lista.add(valorAtual);
        if(hemoglobinaAtual < 5.7){
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Sua Hemoglobina Glicada está boa, mas há chance de " +
                    "Glicemia de Jejum Alterada, você deve procurar um médico.";
        } else if(hemoglobinaAtual >= 5.7 && hemoglobinaAtual <= 6.4){
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Há um alto risco de pré-diabetes, você deve procurar um médico.";
        } else{
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Há alto risco de diabetes, você deve procurar um médico.";
        }
        CardFace second = new CardFace(mensagem, variavel, lista);

        estados.add(second);
    }

    private void carregarInfoFaltando(String variavelFaltando){
        String mensagem = "É necessário que você cadastre a sua taxa de "+variavelFaltando+" para que possamos fazer o diagnóstico completo";
        String titulo = "Taxas ainda não preenchidas";
        msgBalao.setText(mensagem);
        List<String> lista = new ArrayList<>();
        if(Double.isNaN(paciente.getGlicoseJejum())){
            lista.add("Glicose em Jejum");
        }
        if(Double.isNaN(paciente.getGlicose75g())){
            lista.add("Glicose Após 75g");
        }
        if(Double.isNaN(paciente.getHemoglobinaGlicolisada())){
            lista.add("Hemoglobina Glicada");
        }

        CardFace first = new CardFace(mensagem, titulo, lista);

        estados.add(first);
    }

    private void flipCard(boolean forward) {
        View rootLayout = findViewById(R.id.main_activity_card);
        View cardFront;
        View cardBack;
        if((flipCount%2 == 0 && forward) || (flipCount%2 == 1 && !forward)) {
            cardFront = findViewById(R.id.main_activity_card_front);
            cardBack = findViewById(R.id.main_activity_card_back);
        } else{
            cardFront = findViewById(R.id.main_activity_card_back);
            cardBack = findViewById(R.id.main_activity_card_front);
        }

        FlipAnimation flipAnimation = new FlipAnimation(cardFront, cardBack);

        if (!forward) {
            flipAnimation.reverse();
            --flipCount;
            firstBack = false;
        } else{
            ++flipCount;
            firstBack = true;
        }

        rootLayout.startAnimation(flipAnimation);
    }

    private class CardFace {
        private String mensagem;
        private String variavel;
        private ArrayAdapter<String> adapter = new ArrayAdapter<String>(InfoRelatorio.this, R.layout.lista_condicoes_itens,
                R.id.text_condicao_item, new ArrayList<String>());

        CardFace(String mensagem, String variavel, List<String> lista){
            this.mensagem = mensagem;
            this.variavel = variavel;
            this.adapter.addAll(lista);
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getVariavel() {
            return variavel;
        }

        public void setVariavel(String variavel) {
            this.variavel = variavel;
        }

        public ArrayAdapter<String> getAdapter() {
            return adapter;
        }

        public void addListElement(String element){
            adapter.add(element);
        }

        private void setFront(){
            msgBalao.setText(this.mensagem);
            TextView variavel = findViewById(R.id.text_variavel_relatorio_front);
            variavel.setText(this.variavel);
            ListView list = findViewById(R.id.lista_relatorio_variavel_front);
            list.setAdapter(this.adapter);
        }

        private void setBack(){
            msgBalao.setText(this.mensagem);
            TextView variavel = findViewById(R.id.text_variavel_relatorio_back);
            variavel.setText(this.variavel);
            ListView list = findViewById(R.id.lista_relatorio_variavel_back);
            list.setAdapter(this.adapter);
        }
    }
}
