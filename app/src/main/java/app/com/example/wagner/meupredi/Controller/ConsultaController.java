package app.com.example.wagner.meupredi.Controller;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Nullable;

import app.com.example.wagner.meupredi.Model.Consulta;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;
import app.com.example.wagner.meupredi.View.Application.MainViews.Perfil;

/**
 * Created by LeandroDias1 on 05/03/2018.
 */

public abstract class ConsultaController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("pacientes");

    private static CollectionReference getRef(String email){
        return myRef.document(email).collection("consultas");
    }

    public static Task<Void> addEvento(Paciente paciente, Consulta consulta){
        DocumentReference doc = getRef(paciente.getUid()).document();
        consulta.setId(doc.getId());
        return doc.set(consulta);
    }

    public static Task<Void> editConsulta(Paciente paciente, Consulta consulta){
        return getRef(paciente.getUid()).document(consulta.getId())
                .set(consulta, SetOptions.merge());
    }

    /**
     * Printa todos os eventos ordenados
     *
     * @param paciente
     * @return
     */

    public static Task<QuerySnapshot> getAllConsultas(Paciente paciente){
        Timestamp currentDate = Timestamp.now();
        return getRef(paciente.getUid()).orderBy("date", Query.Direction.ASCENDING)
                .whereGreaterThanOrEqualTo("date", currentDate)
                .get();
    }

    public static Query getFutureConsultasListener(Paciente paciente){
        Timestamp currentDate = Timestamp.now();
        return getRef(paciente.getUid()).orderBy("date", Query.Direction.ASCENDING)
                .whereGreaterThanOrEqualTo("date", currentDate);
    }

    public static Query getPastConsultasListener(Paciente paciente){
        Timestamp currentDate = Timestamp.now();
        return getRef(paciente.getUid()).orderBy("date", Query.Direction.ASCENDING)
                .whereLessThan("date", currentDate);
    }


    public static Task<QuerySnapshot> getConsulta(Paciente paciente){
        Timestamp currentDate = Timestamp.now();
        return getRef(paciente.getUid()).orderBy("date", Query.Direction.ASCENDING)
                .whereGreaterThanOrEqualTo("date", currentDate)
                .limit(1).get();
    }

    public static ListenerRegistration getLiveConsultas(LiveUpdateHelper<Consulta> current, Paciente paciente){
        return getFutureConsultasListener(paciente).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.d("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Consulta.class));
                }
            }
        });
    }

    public static ListenerRegistration getLivePastConsultas(LiveUpdateHelper<Consulta> current, Paciente paciente){
        return getPastConsultasListener(paciente).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.d("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Consulta.class));
                }
            }
        });
    }

    /**
     * Recebe o objeto paciente
     * retorna a data mais proxima verificando se falta menos de uma semana para a consulta
     * se sim, lança notificação
     *
     * @param paciente
     * @return Data do evento
     */

    public static void notifyConsulta(Perfil current, Paciente paciente){
        getConsulta(paciente).addOnSuccessListener(current, new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null){
                    if(!queryDocumentSnapshots.isEmpty()) {
                        Consulta mostRecent = queryDocumentSnapshots.toObjects(Consulta.class).get(0);

                        Date sevenDaysFromNow = new Date();
                        long plusOneDay = (1000 * 60 * 60 * 24);
                        sevenDaysFromNow.setTime(sevenDaysFromNow.getTime() + plusOneDay * 7);

                        if (mostRecent.getDate().toDate().before(sevenDaysFromNow)) {
                            current.onNotify(mostRecent);
                        }
                    }
                }
            }
        });
    }

    public static Task<Void> delete(Consulta consulta){
        Log.d("Id peso : ", String.valueOf(consulta.getId()));
        return getRef(consulta.getUidPaciente())
                .document(consulta.getId())
                .delete();
    }

}
