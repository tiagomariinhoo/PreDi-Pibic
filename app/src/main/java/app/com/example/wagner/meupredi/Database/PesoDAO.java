package app.com.example.wagner.meupredi.Database;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.PesoClass;

public abstract class PesoDAO extends DatabaseHelper {

    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("Pacientes");
    private static final String collectionName = "Pesos";

    /*
    public void atualizarPeso(Paciente paciente){
        db.modelAtualizarPeso(paciente);
    }

    public boolean editPeso(PesoClass peso){return db.modelEditPeso(peso);}

    public double getPeso(Paciente paciente){
        return db.modelGetPeso(paciente);
    }

    public ArrayList<Float> getAllPesos(Paciente paciente){
        return db.modelGetAllPesos(paciente);
    }

    public ArrayList<Float> getAllCircunferencias(Paciente paciente) { return db.modelGetAllCircunferencias(paciente);}

    public double getCircunferencia(Paciente paciente) { return db.modelGetCircunferencia(paciente);}

    public ArrayList<PesoClass> getAllInfos(Paciente paciente) {return db.modelGetAllPesoClass(paciente);}
    */

    private static CollectionReference getReference(String pacienteEmail){
        return myRef.document(pacienteEmail).collection(collectionName);
    }

    public static boolean createPeso(Paciente paciente){

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        PesoClass peso = new PesoClass(0, paciente.getPeso(), paciente.getCircunferencia(),
                                    dateString, 1, paciente.getId(), paciente.getEmail());

        getReference(paciente.getEmail())
             .document(dateString)
             .set(peso)
             .addOnSuccessListener(success)
             .addOnFailureListener(failure);

        return succeeded;
    }

    public static boolean updatePeso(PesoClass peso){
        getReference(peso.getPacienteEmail())
                .document(peso.getDatePeso())
                .set(peso, SetOptions.merge())
                .addOnSuccessListener(success)
                .addOnFailureListener(failure);

        return succeeded;
    }

    public static Task<QuerySnapshot> queryAllPesos(Paciente paciente){
        return getReference(paciente.getEmail()).get();
    }

    public static Task<DocumentSnapshot> queryPeso(Paciente paciente, String datePeso){
        return getReference(paciente.getEmail()).document(datePeso).get();
    }


    // Get the first element in this call to get the latest measurement
    public static Task<QuerySnapshot> queryPeso(Paciente paciente){
        return getReference(paciente.getEmail()).orderBy("datePeso").limit(1).get();
    }

    public static boolean deletePeso(PesoClass peso){
        peso.setFlagPeso(0);
        getReference(peso.getPacienteEmail())
                .document(peso.getDatePeso())
                .set(peso, SetOptions.merge())
                .addOnSuccessListener(success)
                .addOnFailureListener(failure);

        return succeeded;
    }

    //NOT YET IMPLEMENTED
    public static void queryExistsPeso(String email){
        myRef.document(email);
    }

}
