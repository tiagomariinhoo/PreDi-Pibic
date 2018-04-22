package app.com.example.wagner.meupredi.Controller;

import android.content.Context;

import java.text.ParseException;
import java.util.ArrayList;

import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.ExercicioClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public class ControllerExercicios {
    private DatabaseHandler db;

    public ControllerExercicios(Context context) {
        db = new DatabaseHandler(context);
    }

    public String addExercicio(int tempo, String exercicio, Paciente paciente){
       return db.modelAddExercicio(tempo, exercicio, paciente);
    }

    public ArrayList<ExercicioClass> getAllExercicios(Paciente paciente) throws ParseException {
        return db.modelGetAllExercicios(paciente);
    }

}
