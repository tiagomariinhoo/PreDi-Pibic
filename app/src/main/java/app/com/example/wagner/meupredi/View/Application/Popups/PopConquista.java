package app.com.example.wagner.meupredi.View.Application.Popups;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class PopConquista extends Activity {

    private ImageView imgPopUp;
    private Button closePopUp;
    private TextView tituloPopUp, msgPopUp;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_conquista);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) ( width*.80), (int) (height*.6));

        paciente = PacienteUpdater.getPaciente();

        imgPopUp = findViewById(R.id.image_pop_up_conquista);
        tituloPopUp = findViewById(R.id.text_titulo_pop_up_conquista);
        msgPopUp = findViewById(R.id.text_msg_conquista);
        closePopUp = findViewById(R.id.image_close_popup_conquista);

        if(paciente.getPeso() <= (24.9 * paciente.getAltura() * paciente.getAltura())
                && paciente.getPeso() >= (18.6 * paciente.getAltura() * paciente.getAltura())){
            tituloPopUp.setText("Parabéns!");
            imgPopUp.setImageResource(R.drawable.trofeu);
            msgPopUp.setText("Seu índice de Massa Corporal se encontra em ideal para seu porte físico!");
        }
        else{
            tituloPopUp.setText("Fique atento!");
            imgPopUp.setImageResource(R.mipmap.ic_exclamacao);
            msgPopUp.setText("Seu índice de Massa Corporal está fora do ideal para seu porte físico!");
            double ideal = 24.9 * paciente.getAltura() * paciente.getAltura();
            double diff_kg = Math.abs(paciente.getPeso() - ideal);
            String msgFormat = String.format(Locale.getDefault(), "%.2f", diff_kg);

            msgPopUp.setText("Você está a " + msgFormat + " do seu peso ideal, procure mudar sua rotina de alimentação.");
        }

        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

