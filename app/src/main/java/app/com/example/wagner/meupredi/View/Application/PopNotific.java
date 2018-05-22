package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import app.com.example.wagner.meupredi.Model.InferenceMotor.Motor;
import app.com.example.wagner.meupredi.Model.InferenceMotor.VariableMap;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

public class PopNotific extends Activity {

    private TextView relatorio;
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

        Log.d("Teste glicose jejum: ", String.valueOf(paciente.get_glicosejejum()));
        Log.d("Teste glicose 2h: ", String.valueOf(paciente.get_glicose75g()));

        userVariables = new HashMap<String, Double>();
        userVariables.put("glicemiaJejum", paciente.get_glicosejejum());
        userVariables.put("glicemia2h", paciente.get_glicose75g());
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

    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
