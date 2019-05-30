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
    private String[] dicaEscolhida;


    private String[] setDica(Paciente.StatusPaciente state){

        String[] dicaSaudavel = {"valor1", "valor2", "valor3"};
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

        if(state == Paciente.StatusPaciente.DIABETES){
            return dicaDiabetes;
        }
        else if(state == Paciente.StatusPaciente.PRE_DIABETES){
            return dicaPreDiabetes;
        }
        else{
            return dicaSaudavel;
        }
    }

    private void setTextViews(Paciente.StatusPaciente state, int position,  String[] dica){

        if(state == Paciente.StatusPaciente.DIABETES){
            status.setText("Diabetes");
            tituloDica.setText("Cuidado!");
            txtDica.setText(dica[position]);
        }
        else if(state == Paciente.StatusPaciente.PRE_DIABETES){
            status.setText("Pré Diabetes");
            tituloDica.setText("Atenção!");
            txtDica.setText(dica[position]);
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
        dicaEscolhida = setDica(state);

        fechar = findViewById(R.id.txt_fechar_imc);
        tituloDica = findViewById(R.id.txt_titulo_imc);
        txtDica = findViewById(R.id.txt_imc);
        back = findViewById(R.id.txt_backgroud_geral_dicas_predi);
        image = findViewById(R.id.img_peso_ideal);
        continuar = findViewById(R.id.txt_continuar_predi_geral);
        imgContinuar = findViewById(R.id.img_continuar_predi_geral);
        voltar = findViewById(R.id.txt_voltar_predi_geral);
        imgVoltar = findViewById(R.id.img_voltar_predi_geral);
        status = findViewById(R.id.txt_status_imc);
        position = 0;

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextViews(state, position, dicaEscolhida);
                position += 1;
            }

        });


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position -= 1;
                setTextViews(state, position, dicaEscolhida);
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