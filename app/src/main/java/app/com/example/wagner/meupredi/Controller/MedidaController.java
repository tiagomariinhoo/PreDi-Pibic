package app.com.example.wagner.meupredi.Controller;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import javax.annotation.Nullable;

import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class MedidaController {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("pacientes");

    private static CollectionReference getRef(String email){
        return myRef.document(email).collection("medidas");
    }

    public static Task<Void> addMedida(Paciente paciente){
        PacienteController.atualizarPaciente(paciente);
        Medida medida = new Medida(paciente.getPeso(), paciente.getCircunferencia(), paciente.getEmail());
        return getRef(paciente.getEmail()).document(medida.stringDate()).set(medida);
    }

    public static Task<Void> editMedida(Medida medida){
        return getRef(medida.getEmailPaciente()).document(medida.stringDate()).set(medida, SetOptions.merge());
    }

    public static Task<QuerySnapshot> getAllMedidas(Paciente paciente){
        return getRef(paciente.getEmail()).whereEqualTo("flagMedida", 1).get();
    }

    public static Query graphMedidas(Paciente paciente){
        //sempre dar reverse nesse resultado, pq ele é ordenado pela data ao contrário de como o gráfico deve receber
        return getRef(paciente.getEmail()).whereEqualTo("flagMedida", 1).orderBy("dateMedida", Query.Direction.DESCENDING).limit(5);
    }

    public static ListenerRegistration getDadosGrafico(LiveUpdateHelper<Medida> current, Paciente paciente){
        return graphMedidas(paciente).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.d("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Medida.class));
                }
            }
        });
    }

    public static Task<QuerySnapshot> getMedida(Paciente paciente){
        return getRef(paciente.getEmail()).whereEqualTo("flagMedida", 1).orderBy("dateMedida", Query.Direction.DESCENDING).limit(1).get();
    }

    public static Query getLastInfoMedida(Paciente paciente){
        return getRef(paciente.getEmail()).whereEqualTo("flagMedida", 1).orderBy("dateMedida", Query.Direction.DESCENDING).limit(1);
    }

    public static Task<Void> eraseLastInfo(Medida medida){
        Log.d("Id peso : ", String.valueOf(medida.stringDate()));
        medida.setFlagMedida(0);
        return getRef(medida.getEmailPaciente())
                .document(medida.stringDate())
                .set(medida, SetOptions.merge());
    }
}
