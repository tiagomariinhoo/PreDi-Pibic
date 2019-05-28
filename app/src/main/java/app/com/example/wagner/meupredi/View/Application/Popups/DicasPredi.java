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

    private TextView tituloDica, txtDica, fechar, continuar, back, status;
    private ImageView image;
    private Paciente paciente;
    private RadioButton r1, r2, r3;

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

        fechar = findViewById(R.id.txt_fechar_imc);
        tituloDica = findViewById(R.id.txt_titulo_imc);
        txtDica = findViewById(R.id.txt_imc);
        back = findViewById(R.id.txt_backgroud_geral_dicas_predi);
        image = findViewById(R.id.img_peso_ideal);
        r1 = findViewById(R.id.radio_btn_dicas_predi_1);
        r2 = findViewById(R.id.radio_btn_dicas_predi_2);
        r3 = findViewById(R.id.radio_btn_dicas_predi_3);
        continuar = findViewById(R.id.txt_continuar_predi_geral);
        status = findViewById(R.id.txt_status_imc);

        r1.setChecked(true);

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            r2.setChecked(false);
            r3.setChecked(false);
            image.setImageAlpha(R.mipmap.blue_alert);
            image.setBackgroundResource(R.mipmap.blue_alert);
            back.setBackgroundResource(R.drawable.borda_curvada_cima_amarela);
            status.setText("Pré Diabetes" );
            txtDica.setText("  Modificações positivas do estilo de vida podem ter papel decisivo na prevenção do diabetes!");
            continuar.setText("Continuar");
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r3.setChecked(false);
                image.setImageAlpha(R.mipmap.green_alert);
                image.setBackgroundResource(R.mipmap.green_alert);
                back.setBackgroundResource(R.drawable.borda_curvada_cima_verde);
                status.setText("Saudável" );
                txtDica.setText("Hábitos de vida mais saudáveis, como fazer uma dieta balanceada, rica em fibras, visando " +
                        "um peso corporal cada vez mais próximo do ideal e ...");
                continuar.setText("Continuar");
            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1.setChecked(false);
                r2.setChecked(false);
                image.setImageAlpha(R.mipmap.ic_diabete_status);
                image.setBackgroundResource(R.mipmap.ic_diabete_status);
                back.setBackgroundResource(R.drawable.borda_curvada_cima_vermelha);
                status.setText("Diabetes" );
                txtDica.setText(" associada à atividade física de" +
                        ", pelo menos, 150 minutos semanais, são capazes de reduzir seu risco de Diabetes em 58%!");
                continuar.setText("");
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r1.isChecked()){
                  r1.setChecked(false);
                  r2.setChecked(true);
                  txtDica.setText(" Hábitos de vida mais saudáveis, como fazer uma dieta balanceada, rica em fibras, visando " +
                          "um peso corporal cada vez mais próximo do ideal e ...");
                }
                else if(r2.isChecked()){
                    r2.setChecked(false);
                    r3.setChecked(true);
                    txtDica.setText("associada à atividade física de" +
                            ", pelo menos, 150 minutos semanais, são capazes de reduzir seu risco de Diabetes em 58%!");
                    continuar.setText("");
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