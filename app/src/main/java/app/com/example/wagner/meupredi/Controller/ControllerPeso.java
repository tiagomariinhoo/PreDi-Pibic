package app.com.example.wagner.meupredi.Controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.PesoClass;

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

    public boolean editPeso(PesoClass peso){return db.modelEditPeso(peso);}

    public double getPeso(Paciente paciente){
        return db.modelGetPeso(paciente);
    }

    public ArrayList<Float> getAllPesos(Paciente paciente){
        return db.modelGetAllPesos(paciente);
    }

    public ArrayList<Float> getAllCircunferencias(Paciente paciente) { return db.modelGetAllCircunferencias(paciente);}

    public double getCircunferencia(Paciente paciente) { return db.modelGetCircunferencia(paciente);}

    public ArrayList<PesoClass> getAllInfos(Paciente paciente) {return db.modelGetAllPesoClass(paciente);}

    public boolean eraseLastInfo(PesoClass peso){
        Log.d("Id peso : ", String.valueOf(peso.getIdPeso()));
        return db.eraseLastInfoPeso(peso); }
}
