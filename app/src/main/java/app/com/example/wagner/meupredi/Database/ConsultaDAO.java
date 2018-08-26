package app.com.example.wagner.meupredi.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import app.com.example.wagner.meupredi.Model.ModelClass.Consulta;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

public abstract class ConsultaDAO {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("Pacientes");

    private static CollectionReference getRef(String email){
        return myRef.document(email).collection("Consultas");
    }

    public static Task<Void> createConsulta(Paciente paciente, Consulta consulta){
        return getRef(paciente.getEmail()).document(consulta.getTime()+" "+consulta.getDate()).set(consulta);
    }

    public static Task<Void> updateConsulta(Paciente paciente, Consulta consulta){
        return getRef(paciente.getEmail()).document(consulta.getDate()+" "+consulta.getTime()).set(consulta, SetOptions.merge());
    }

    public static Task<QuerySnapshot> getAllConsultas(Paciente paciente){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        return getRef(paciente.getEmail()).orderBy("date", Query.Direction.DESCENDING)
                                          .whereGreaterThan("date", currentDate).get();
    }

    public static Task<QuerySnapshot> getConsulta(Paciente paciente){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        return getRef(paciente.getEmail()).orderBy("date", Query.Direction.DESCENDING)
                                          .whereGreaterThan("date", currentDate).limit(1).get();
    }
}
