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
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.Model.Medida;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class MedidaController {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("pacientes");

    private static CollectionReference getRef(String uid){
        return myRef.document(uid).collection("medidas");
    }

    public static Task<Void> addMedida(Paciente paciente){
        PacienteController.atualizarPaciente(paciente);
        Medida medida = new Medida(paciente.getUid(), paciente.getPeso(), paciente.getCircunferencia());
        DocumentReference doc = getRef(paciente.getUid()).document();
        medida.setId(doc.getId());
        return doc.set(medida);
    }

    public static Task<Void> editMedida(Medida medida){

        return getRef(medida.getUidPaciente()).document(medida.getId()).set(medida,SetOptions.merge());
    }

    public static Task<QuerySnapshot> getAllMedidas(Paciente paciente){
        return getRef(paciente.getUid()).whereEqualTo("deleted", false).get();
    }

    public static Query graphMedidas(Paciente paciente){
        //sempre dar reverse nesse resultado, pq ele é ordenado pela data ao contrário de como o gráfico deve receber
        return getRef(paciente.getUid()).whereEqualTo("deleted", false).orderBy("dateMedida", Query.Direction.DESCENDING).limit(5);
    }

    public static ListenerRegistration getDadosGrafico(LiveUpdateHelper<Medida> current, Paciente paciente){
        return graphMedidas(paciente).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.e("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Medida.class));
                }
            }
        });
    }

    public static Task<QuerySnapshot> getMedida(Paciente paciente){
        return getLastInfoMedida(paciente).get();
    }

    public static Query getLastInfoMedida(Paciente paciente){
        return getRef(paciente.getUid()).whereEqualTo("deleted", false).orderBy("dateMedida", Query.Direction.DESCENDING).limit(1);
    }

    public static Task<DocumentSnapshot> getSpecificMedida(Paciente paciente, String id){
        return getRef(paciente.getUid()).document(id).get();
    }

    public static Task<Void> eraseLastInfo(Medida medida){
        Log.d("Id peso : ", String.valueOf(medida.getId()));
        medida.setDeleted(true);
        return getRef(medida.getUidPaciente())
                .document(medida.getId())
                .set(medida, SetOptions.merge());
    }
}
