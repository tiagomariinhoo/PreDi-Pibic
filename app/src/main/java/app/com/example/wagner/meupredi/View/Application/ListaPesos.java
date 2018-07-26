package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

public class ListaPesos extends Activity {

    private Paciente paciente;
    private ArrayAdapter<String> adapter;
    private String[] items = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pesos);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        ControllerPeso pesoController = new ControllerPeso(getApplicationContext());
        ArrayList<Float> pesos = pesoController.getAllPesos(paciente);


    }
}
