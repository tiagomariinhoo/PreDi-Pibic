package app.com.example.wagner.meupredi.Controller;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import app.com.example.wagner.meupredi.Model.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class PacienteController {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("pacientes");

    public static Task<Void> addPaciente(Paciente paciente){
        MedidaController.addMedida(paciente);
        TaxasController.addTaxas(paciente);
        return myRef.document(paciente.getUid())
                    .set(paciente);

    }

    public static Task<Void> atualizarPaciente(Paciente paciente){

        return myRef.document(paciente.getUid())
                    .set(paciente, SetOptions.merge());

    }

    public static DocumentReference getPacienteListener(Paciente paciente){
        return myRef.document(paciente.getUid());
    }

    public static Task<QuerySnapshot> getAllPacientes(){
        return myRef.get();
    }

    public static Task<DocumentSnapshot> getPaciente(String uid){
        return myRef.document(uid).get();
    }

}
