package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class PopConquista extends Activity {

    private ImageView imgPopUp, closePopUp;
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

        getWindow().setLayout((int) ( width*.65), (int) (height*.6));

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

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
            imgPopUp.setImageResource(R.drawable.exclamacao);
            msgPopUp.setText("Seu índice de Massa Corporal está fora do ideal para seu porte físico!");
        }

        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

