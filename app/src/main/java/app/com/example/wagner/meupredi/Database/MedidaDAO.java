package app.com.example.wagner.meupredi.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;

public class PesoDAO {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("Pacientes");

    public static Paciente paciente;

    private static CollectionReference getRef(String email){
        return myRef.document(email).collection("Pesos");
    }

    public static Task<Void> createPeso(Paciente paciente){

        Medida peso = new Medida(new Date(), paciente.getPeso(), paciente.getCircunferencia(), 0, paciente.getEmail());
        return getRef(paciente.getEmail()).document(peso.getDatePesoString()).set(peso);
    }

    public static Task<Void> updatePeso(Medida peso){
        return getRef(paciente.getEmail()).document(peso.getDatePesoString()).set(peso, SetOptions.merge());
    }

    public static Task<QuerySnapshot> getAllPesos(Paciente paciente){
        return getRef(paciente.getEmail()).get();
    }

    public static Task<QuerySnapshot> getPeso(Paciente paciente){
        return getRef(paciente.getEmail()).orderBy("datePeso").limit(1).get();
    }

    public static Task<Void> deletePeso(Medida peso){
        peso.setFlagPeso(0);
        return getRef(peso.getEmailPaciente())
                .document(peso.getDatePesoString())
                .set(peso, SetOptions.merge());
    }
}
