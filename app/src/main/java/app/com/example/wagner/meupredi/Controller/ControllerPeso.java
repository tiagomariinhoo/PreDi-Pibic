package app.com.example.wagner.meupredi.Controller;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import app.com.example.wagner.meupredi.Database.PesoDAO;
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

    public boolean addPeso(Paciente paciente){
        return PesoDAO.createPeso(paciente);
        //db.modelAtualizarPeso(paciente);
    }

    public boolean editPeso(PesoClass peso){
        return PesoDAO.updatePeso(peso);
        //return db.modelEditPeso(peso);
    }

    public Task<QuerySnapshot> getPeso(Paciente paciente){
        return PesoDAO.queryPeso(paciente);
        //return db.modelGetPeso(paciente);
    }

    public ArrayList<Float> getAllPesos(Paciente paciente){
        return db.modelGetAllPesos(paciente);
    }

    public ArrayList<Float> getAllCircunferencias(Paciente paciente) { return db.modelGetAllCircunferencias(paciente);}

    public double getCircunferencia(Paciente paciente) { return db.modelGetCircunferencia(paciente);}

    public Task<QuerySnapshot> getAllInfos(Paciente paciente) {
        return PesoDAO.queryAllPesos(paciente);
        //return db.modelGetAllPesoClass(paciente);
    }

    public boolean eraseLastInfo(PesoClass peso){
        Log.d("Id peso : ", String.valueOf(peso.getIdPeso()));
        return PesoDAO.deletePeso(peso);
        //return db.eraseLastInfoPeso(peso);
    }
}
