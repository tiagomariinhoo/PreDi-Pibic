package app.com.example.wagner.meupredi.Controller;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import javax.annotation.Nullable;

import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;
import app.com.example.wagner.meupredi.Model.Taxas;
import app.com.example.wagner.meupredi.Model.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class TaxasController {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("pacientes");

    private static CollectionReference getRef(String email){
        return myRef.document(email).collection("taxas");
    }

    public static Task<Void> addTaxas(Paciente paciente){
        PacienteController.atualizarPaciente(paciente);
        Taxas taxas = new Taxas(paciente.getUid(), paciente.getGlicose75g(),
                paciente.getGlicoseJejum(), paciente.getColesterol(), paciente.getHemoglobinaGlicolisada());
        DocumentReference doc = getRef(paciente.getUid()).document();
        taxas.setId(doc.getId());
        return doc.set(taxas);
    }

    public static Task<Void> editTaxas(Taxas taxas){

        return getRef(taxas.getUidPaciente()).document(taxas.getId()).set(taxas,SetOptions.merge());
    }

    public static Task<QuerySnapshot> getAllTaxas(Paciente paciente){
        return getRef(paciente.getUid()).whereEqualTo("deleted", false).get();
    }

    public static Query graphTaxas(Paciente paciente){
        //sempre dar reverse nesse resultado, pq ele é ordenado pela data ao contrário de como o gráfico deve receber
        return getRef(paciente.getUid()).whereEqualTo("deleted", false).orderBy("dateTaxas", Query.Direction.DESCENDING).limit(5);
    }

    public static ListenerRegistration getDadosGrafico(LiveUpdateHelper<Taxas> current, Paciente paciente){
        return graphTaxas(paciente).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.e("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Taxas.class));
                }
            }
        });
    }

    public static Task<QuerySnapshot> getTaxas(Paciente paciente){
        return getLastInfoTaxas(paciente).get();
    }

    public static Query getLastInfoTaxas(Paciente paciente){
        return getRef(paciente.getUid()).whereEqualTo("deleted", false).orderBy("dateTaxas", Query.Direction.DESCENDING).limit(1);
    }

    public static Task<DocumentSnapshot> getSpecificTaxas(Paciente paciente, String id){
        return getRef(paciente.getUid()).document(id).get();
    }

    public static Task<Void> eraseLastInfo(Taxas taxas){
        Log.d("Id taxas : ", taxas.getId());
        taxas.setDeleted(true);
        return getRef(taxas.getUidPaciente())
                .document(taxas.getId())
                .set(taxas, SetOptions.merge());
    }

}
