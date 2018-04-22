package app.com.example.wagner.meupredi.Controller;

import android.content.Context;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public class ControllerPeso {
    DatabaseHandler db;

    public ControllerPeso(Context context) {
        db = new DatabaseHandler(context);
    }

    public void atualizarPeso(Paciente paciente){
        db.modelAtualizarPeso(paciente);
    }

    public double getPeso(Paciente paciente){
        return db.modelGetPeso(paciente);
    }

    public ArrayList<Float> getAllPesos(Paciente paciente){
        return db.modelGetAllPesos(paciente);
    }
}
