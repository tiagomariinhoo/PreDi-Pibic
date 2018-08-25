package app.com.example.wagner.meupredi.Controller;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.Database.PesoDAO;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class ControllerPeso {
/*    DatabaseHandler db;

    public ControllerPeso(Context context) {
        db = new DatabaseHandler(context);
    }
*/
    public static void addPeso(Paciente paciente){
        //db.modelAtualizarPeso(paciente);
    }

    public boolean editPeso(Medida peso){
        //return db.modelEditPeso(peso);
        return true;
    }

    public Task<QuerySnapshot> getPeso(Paciente paciente){
        return PesoDAO.getPeso(paciente);
        //return db.modelGetPeso(paciente);
    }

    public ArrayList<Float> getAllPesos(Paciente paciente){
        return db.modelGetAllPesos(paciente);
    }

    public ArrayList<Float> getAllCircunferencias(Paciente paciente) { return db.modelGetAllCircunferencias(paciente);}

    public double getCircunferencia(Paciente paciente) { return db.modelGetCircunferencia(paciente);}

    public ArrayList<Medida> getAllInfos(Paciente paciente) {
        return new ArrayList<>();
        //return db.modelGetAllPesoClass(paciente);
    }

    public boolean eraseLastInfo(Medida peso){
        Log.d("Id peso : ", String.valueOf(peso.getDatePesoString()));
        return true;
        //return PesoDAO.deletePeso(peso);
        //return db.eraseLastInfoPeso(peso);
    }
}
