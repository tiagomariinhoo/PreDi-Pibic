package app.com.example.wagner.meupredi.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;

public abstract class MedidaDAO {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("Pacientes");

    private static CollectionReference getRef(String email){
        return myRef.document(email).collection("Pesos");
    }

    public static Task<Void> createMedida(Paciente paciente){
        Medida medida = new Medida(new Date(), paciente.getPeso(), paciente.getCircunferencia(), paciente.getEmail());
        return getRef(paciente.getEmail()).document(medida.getDateMedida()).set(medida);
    }

    public static Task<Void> updateMedida(Medida medida){
        return getRef(medida.getEmailPaciente()).document(medida.getDateMedida()).set(medida, SetOptions.merge());
    }

    public static Task<QuerySnapshot> getAllMedidas(Paciente paciente){
        return getRef(paciente.getEmail()).get();
    }

    public static Query graphMedidas(Paciente paciente){
        return getRef(paciente.getEmail()).orderBy("datePeso");
    }

    public static Task<QuerySnapshot> getMedida(Paciente paciente){
        return getRef(paciente.getEmail()).orderBy("datePeso").limit(1).get();
    }

    public static Task<Void> deleteMedida(Medida medida){
        medida.setFlagPeso(0);
        return getRef(medida.getEmailPaciente())
                .document(medida.getDateMedida())
                .set(medida, SetOptions.merge());
    }
}
