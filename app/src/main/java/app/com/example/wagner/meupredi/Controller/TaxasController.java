package app.com.example.wagner.meupredi.Controller;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;
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

    public static Task<QuerySnapshot> getTaxas(Paciente paciente){
        return TaxasDAO.getTaxas(paciente);
    }

    public static ListenerRegistration getDadosGrafico(LiveUpdateHelper<Taxas> current, Paciente paciente){
        return TaxasDAO.graphMedidas(paciente).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.d("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Taxas.class));
                }
            }
        });
    }

    public static Query getLastInfoTaxas(Paciente paciente){
        return TaxasDAO.getLastTaxas(paciente);
    }

    public static Task<QuerySnapshot> getAllTaxas(Paciente paciente){
        return TaxasDAO.getAllTaxas(paciente);
    }

    public static Task<Void> eraseLastInfoTaxas(Taxas taxas){
        return TaxasDAO.deleteTaxas(taxas);
    }

}
