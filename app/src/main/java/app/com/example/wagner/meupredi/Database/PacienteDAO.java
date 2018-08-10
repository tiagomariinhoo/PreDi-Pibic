package app.com.example.wagner.meupredi.Database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

public abstract class PacienteDAO extends DatabaseHelper {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("Pacientes");

    public static boolean createPaciente(Paciente paciente){

        myRef.document(paciente.getEmail())
             .set(paciente)
             .addOnSuccessListener(success)
             .addOnFailureListener(failure);

        return succeeded;
    }

    public static boolean updatePaciente(Paciente paciente){
        myRef.document(paciente.getEmail())
             .set(paciente, SetOptions.merge())
             .addOnSuccessListener(success)
             .addOnFailureListener(failure);

        return succeeded;
    }

    public static Task<QuerySnapshot> queryAllPacientes(){
        return myRef.get();
    }

    public static Task<DocumentSnapshot> queryPaciente(String email){
        return myRef.document(email).get();
    }

    public static void queryExistsPaciente(String email){
        myRef.document(email);
    }

}
