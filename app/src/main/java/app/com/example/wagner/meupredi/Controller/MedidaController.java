package app.com.example.wagner.meupredi.Controller;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import app.com.example.wagner.meupredi.Database.MedidaDAO;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class MedidaController {

    public static Task<Void> addMedida(Paciente paciente){
        //db.modelAtualizarPeso(paciente);
        return MedidaDAO.createMedida(paciente);
    }

    public static Task<Void> editMedida(Medida medida){
        //return db.modelEditPeso(peso);
        return MedidaDAO.updateMedida(medida);
    }

    public static Task<QuerySnapshot> getMedida(Paciente paciente){
        return MedidaDAO.getMedida(paciente);
        //return db.modelGetPeso(paciente);
    }

    public static Query getDadosGrafico(Paciente paciente){
        return MedidaDAO.graphMedidas(paciente);
    }

    public static Task<QuerySnapshot> getAllMedidas(Paciente paciente) {
        return MedidaDAO.getAllMedidas(paciente);
        //return db.modelGetAllPesoClass(paciente);
    }

    public static Task<Void> eraseLastInfo(Medida medida){
        Log.d("Id peso : ", String.valueOf(medida.getDateMedida()));
        return MedidaDAO.deleteMedida(medida);
        //return db.eraseLastInfoPeso(peso);
    }
}
