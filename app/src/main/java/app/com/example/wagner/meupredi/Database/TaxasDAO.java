package app.com.example.wagner.meupredi.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;

import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

public abstract class TaxasDAO {
    private static CollectionReference myRef = FirebaseFirestore.getInstance().collection("Pacientes");

    private static CollectionReference getRef(String email){
        return myRef.document(email).collection("Taxas");
    }

    public static Task<Void> createTaxas(Paciente paciente){
        Taxas taxas = new Taxas(new Date(), paciente.getEmail(), paciente.getGlicose75g(),
                paciente.getGlicoseJejum(), paciente.getColesterol(), paciente.getHemoglobinaGlicolisada());
        return getRef(paciente.getEmail()).document(taxas.getDateTaxas()).set(taxas);
    }

    public static Task<Void> updateTaxas(Taxas taxas){
        return getRef(taxas.getEmailPaciente()).document(taxas.getDateTaxas()).set(taxas, SetOptions.merge());
    }

    public static Task<QuerySnapshot> getAllTaxas(Paciente paciente){
        return getRef(paciente.getEmail()).whereEqualTo("flagTaxa", 1).get();
    }

    public static Query graphMedidas(Paciente paciente){
        //sempre dar reverse nesse resultado, pq ele é ordenado pela data ao contrário de como o gráfico deve receber
        return getRef(paciente.getEmail()).whereEqualTo("flagTaxa", 1).orderBy("dateTaxas", Query.Direction.DESCENDING).limit(5);
    }

    public static Task<QuerySnapshot> getTaxas(Paciente paciente){
        return getRef(paciente.getEmail()).whereEqualTo("flagTaxa", 1).orderBy("dateTaxas", Query.Direction.DESCENDING).limit(1).get();
    }

    public static Task<Void> deleteTaxas(Taxas taxas){
        taxas.setFlagTaxa(0);
        return getRef(taxas.getEmailPaciente())
                .document(taxas.getDateTaxas())
                .set(taxas, SetOptions.merge());
    }
}
