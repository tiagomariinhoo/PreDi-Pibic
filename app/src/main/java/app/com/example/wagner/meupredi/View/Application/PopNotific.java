package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import app.com.example.wagner.meupredi.Model.InferenceMotor.Motor;
import app.com.example.wagner.meupredi.Model.InferenceMotor.VariableMap;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

public class PopNotific extends Activity {

    private TextView relatorio;
    private ImageView chamadaExplicativoDiagnostico;
    private Map<String, Double> userVariables;
    private Paciente paciente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_notific);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Log.d("Teste glicose jejum: ", String.valueOf(paciente.getGlicoseJejum()));
        Log.d("Teste glicose 2h: ", String.valueOf(paciente.getGlicose75g()));

        chamadaExplicativoDiagnostico = findViewById(R.id.image_informacao_diagnostico);

        userVariables = new HashMap<String, Double>();
        userVariables.put("glicemiaJejum", paciente.getGlicoseJejum());
        userVariables.put("glicemia2h", paciente.getGlicose75g());
        userVariables.put("idade", Double.parseDouble("50"));

        String msg = "teste";

            Motor.setMap(new VariableMap(userVariables), getApplicationContext());
            try{
                Motor.readDatabase();
            } catch (Exception e){
                e.printStackTrace();
            }

            msg = Motor.askQuestions();
            Log.d("Msg atual: ", msg);

        getWindow().setLayout((int) ( width*.8), (int) (height*.50));

        relatorio = (TextView) findViewById(R.id.text_pop_notific_relatorio);

        relatorio.setText(msg);

        chamadaExplicativoDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopNotific.this, StartRelatorio.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
