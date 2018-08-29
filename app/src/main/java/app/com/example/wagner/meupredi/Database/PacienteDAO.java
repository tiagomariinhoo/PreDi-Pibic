package app.com.example.wagner.meupredi.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

public abstract class PacienteDAO {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("pacientes");

    public static Task<Void> createPaciente(Paciente paciente){

        return myRef.document(paciente.getEmail())
                    .set(paciente);

    }

    public static Task<Void> updatePaciente(Paciente paciente){

        return myRef.document(paciente.getEmail())
                    .set(paciente, SetOptions.merge());

    }

    public static Task<QuerySnapshot> getAllPacientes(){
        return myRef.get();
    }

    public static Task<DocumentSnapshot> getPaciente(String email){
        return myRef.document(email).get();
    }

    public static Query authPaciente(String email, String senha){
        return myRef.whereEqualTo("senha", senha).whereEqualTo("email", email);
    }

}
