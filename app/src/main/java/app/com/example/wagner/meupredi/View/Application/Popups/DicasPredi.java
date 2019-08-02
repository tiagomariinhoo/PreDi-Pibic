package app.com.example.wagner.meupredi.View.Application.Popups;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class DicasPredi extends Activity {

    private TextView tituloDica, txtDica, fechar, continuar, voltar, back, status;
    private ImageView image, imgContinuar, imgVoltar;
    private Paciente paciente;
    private int position;
    private String[] dicas;

    private String[] setDicas(Paciente.StatusPaciente state){

        String[] dicaEstavel = {"Ser e estar saudável é uma condição na qual o " +
                "funcionamento do organismo propicia ao homem viver " +
                "em plena disposição, seja ela física ou mental.", "Neste sentido, o controle diário de certos hábitos e " +
                "a busca constante pela qualidade de vida são itens " +
                "indispensáveis para a manutenção da saúde do corpo e da mente.",
                "Procure manter ou melhorar seu ritmo atual para aproveitar ao máximo o que a vida lhe oferece!"};
        String[] dicaPreDiabetes = {
                "O DM2 pode ser prevenido ou, pelo menos, retardado, através de intervenção em portadores de pré-diabetes. \n",
                "É preciso alterar seu estilo de vida, com modificação dos hábitos alimentares, redução do peso, de 5 a 10%,  caso  apresentem sobrepeso ou obesidade,",
                "bem como aumento da atividade física, por exemplo, caminhadas, pelo menos 150 minutos por semana."};
        String[] dicaDiabetes = {
                "A prática da automonitorização glicêmica no diabetes tipo 2 desempenha um " +
                        "papel de grande importância no conjunto  de  ações  dirigidas  ao  bom  controle  do  diabetes.\n",
                "Na prática clínica diária, verificamos a existência de um grande número de pessoas com DM2 que apresentam um significativo descontrole do" +
                        " perfil glicêmico, situação essa que decorre da não utilização da automonitorização glicêmica. \n",
                "Na verdade, a necessidade de uma frequência maior ou menor" +
                        " de testes glicêmicos é a recomendação mais inteligente para a prática desse importante recurso. \n"};
        String[] dicaSemDados = {
                "Não pudemos fazer um diagnóstico com os dados disponíveis\n",
                "A ausência de dados pode estar mascarando problemas com a sua saúde\n",
                "Isso pode ter acontecido por conta da ausência de taxas cadastradas no seu perfil\n"
        };

        if(state == Paciente.StatusPaciente.DIABETES){
            image.setImageAlpha(R.mipmap.ic_diabete_status);
            image.setBackgroundResource(R.mipmap.ic_diabete_status);
            back.setBackgroundResource(R.drawable.borda_curvada_cima_vermelha);
            status.setText("Diabetes");
            tituloDica.setText("Cuidado!");
            return dicaDiabetes;
        }
        else if(state == Paciente.StatusPaciente.PRE_DIABETES){
            image.setImageAlpha(R.mipmap.blue_alert);
            image.setBackgroundResource(R.mipmap.blue_alert);
            back.setBackgroundResource(R.drawable.borda_curvada_cima_amarela);
            status.setText("Pré-Diabetes");
            tituloDica.setText("Atenção!");
            return dicaPreDiabetes;
        }
        else if(state == Paciente.StatusPaciente.SEM_DADOS){
            image.setImageAlpha(R.mipmap.ic_dica_sem_dados);
            image.setBackgroundResource(R.mipmap.ic_dica_sem_dados);
            back.setBackgroundResource(R.drawable.borda_curvada_cima_cinza);
            status.setText("Sem Dados");
            tituloDica.setText("Atenção!");
            return dicaSemDados;
        }
        else{
            //image.setImageAlpha(R.mipmap.green_alert);
            //image.setBackgroundResource(R.mipmap.green_alert);
            status.setText("Estável!");
            tituloDica.setText("Atenção!");
            back.setBackgroundResource(R.drawable.borda_curvada_cima_verde);
            return dicaEstavel;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dica_predi_geral1);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) ( width*.80), (int) (height*.6));

        paciente = PacienteUpdater.getPaciente();
        Paciente.StatusPaciente state = paciente.calculoStatus();

        fechar = findViewById(R.id.txt_fechar_imc);
        tituloDica = findViewById(R.id.txt_titulo_imc);
        txtDica = findViewById(R.id.txt_imc);
        back = findViewById(R.id.txt_backgroud_geral_dicas_predi);
        image = findViewById(R.id.img_peso_ideal);
        imgContinuar = findViewById(R.id.img_continuar_predi_geral);
        imgVoltar = findViewById(R.id.img_voltar_predi_geral);
        status = findViewById(R.id.txt_status_imc);
        position = 0;

        imgVoltar.setVisibility(View.INVISIBLE);
        dicas = setDicas(state);
        txtDica.setText(dicas[position]);

        imgContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position < 3) {
                    position += 1;
                    txtDica.setText(dicas[position]);
                    if(position == 2){
                        imgContinuar.setVisibility(View.INVISIBLE);
                    } else if (position == 0) {
                        imgVoltar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        imgVoltar.setVisibility(View.VISIBLE);
                        imgContinuar.setVisibility(View.VISIBLE);
                    }

                }
            }

        });

        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position > 0) {
                    position -= 1;
                    txtDica.setText(dicas[position]);
                    imgVoltar.setVisibility(View.VISIBLE);
                    if(position == 2){
                        imgContinuar.setVisibility(View.INVISIBLE);
                    } else if (position == 0) {
                        imgVoltar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        imgVoltar.setVisibility(View.VISIBLE);
                        imgContinuar.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    imgVoltar.setVisibility(View.INVISIBLE);
                    imgContinuar.setVisibility(View.VISIBLE);
                }
            }
        });

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}