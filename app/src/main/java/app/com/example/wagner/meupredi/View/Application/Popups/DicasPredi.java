package app.com.example.wagner.meupredi.View.Application.Popups;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class DicasPredi extends Activity {

    private TextView tituloDica, txtDica, fechar, continuar, voltar, back, status;
    private ImageView image, imgContinuar, imgVoltar;
    private Paciente paciente;

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
        continuar = findViewById(R.id.txt_continuar_predi_geral);
        imgContinuar = findViewById(R.id.img_continuar_predi_geral);
        voltar = findViewById(R.id.txt_voltar_predi_geral);
        imgVoltar = findViewById(R.id.img_voltar_predi_geral);
        status = findViewById(R.id.txt_status_imc);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextViews();
            }
            private void setTextViews(){

                if(state == Paciente.StatusPaciente.DIABETES){
                    status.setText("Diabetes");
                    txtDica.setText("Na prática clínica diária, verificamos a existência de um grande número de pessoas" +
                            " com DM2 que apresentam um significativo descontrole do perfil glicêmico, " +
                            "situação essa que decorre da não utilização da automonitorização glicêmica. ");
                    continuar.setText("Continuar");
                }
                else{
                    status.setText("Pré Diabetes");
                    txtDica.setText(" --------");
                    continuar.setText("Continuar");
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