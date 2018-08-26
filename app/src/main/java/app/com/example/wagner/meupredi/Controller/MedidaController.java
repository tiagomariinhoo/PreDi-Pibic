package app.com.example.wagner.meupredi.Controller;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import app.com.example.wagner.meupredi.View.Application.MainViews.GraphHelper;
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

    public static <T extends Activity & GraphHelper<Medida>> void getDadosGrafico(T current, Paciente paciente){
        MedidaDAO.graphMedidas(paciente).addSnapshotListener(current, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.d("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Medida.class));
                }
            }
        });
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
