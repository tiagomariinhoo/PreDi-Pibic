package app.com.example.wagner.meupredi.Controller;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import app.com.example.wagner.meupredi.Database.GraphHelper;
import app.com.example.wagner.meupredi.Database.TaxasDAO;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class TaxasController {

    public static Task<Void> addTaxas(Paciente paciente){
        //db.modelAtualizarTaxas(paciente);
        return TaxasDAO.createTaxas(paciente);
    }

    public static Task<Void> editTaxas(Taxas taxas){
        return TaxasDAO.updateTaxas(taxas);
    }

    public static Task<QuerySnapshot> getLastInfoTaxas(Paciente paciente){
        return TaxasDAO.getTaxas(paciente);
    }

    public static <T extends Activity & GraphHelper<Taxas>> void getDadosGrafico(T current, Paciente paciente){
        TaxasDAO.graphMedidas(paciente).addSnapshotListener(current, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.d("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Taxas.class));
                }
            }
        });
    }

    public static Task<QuerySnapshot> getAllTaxas(Paciente paciente){
        return TaxasDAO.getAllTaxas(paciente);
    }

    public static Task<Void> eraseLastInfoTaxas(Taxas taxas){
        return TaxasDAO.deleteTaxas(taxas);
    }

}
