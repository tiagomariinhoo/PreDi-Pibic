package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import app.com.example.wagner.meupredi.Controller.MedidaController;
import app.com.example.wagner.meupredi.Controller.PacienteController;
import app.com.example.wagner.meupredi.Controller.TaxasController;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.View.Application.MedidaListener;
import app.com.example.wagner.meupredi.View.Application.PacienteListener;
import app.com.example.wagner.meupredi.View.Application.TaxasListener;

public final class PacienteUpdater {

    private static List<MedidaListener> medidaListeners = new ArrayList<>();
    private static List<TaxasListener> taxasListeners = new ArrayList<>();
    private static List<PacienteListener> pacienteListeners = new ArrayList<>();
    private static ListenerRegistration medidaSnapshot;
    private static ListenerRegistration taxasSnapshot;
    private static ListenerRegistration pacienteSnapshot;
    private static Paciente paciente;
    private static Medida lastMedida;
    private static Taxas lastTaxas;

    public static void onStart(Paciente pac){
        paciente = pac;
        //mantém as medidas do paciente sempre atualizadas
        medidaSnapshot = MedidaController.getLastInfoMedida(paciente)
            .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d("Firebase Error: ", e.getMessage());
                        return;
                    }
                    Log.d("Got Medida", Integer.toString(queryDocumentSnapshots.size()));
                    Medida medida;
                    if (!queryDocumentSnapshots.isEmpty()) {
                        medida = queryDocumentSnapshots.toObjects(Medida.class).get(0);
                    } else {
                        medida = new Medida("1900-01-01_00:00:00", 0, 0, paciente.getEmail());
                    }

                    onUpdate(medida);
                }
            });
        //mantém as taxas do paciente sempre atualizadas
        taxasSnapshot = TaxasController.getLastInfoTaxas(paciente)
            .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(e != null){
                        Log.d("Firebase Error: ", e.getMessage());
                        return;
                    }
                    Log.d("Got Taxas", Integer.toString(queryDocumentSnapshots.size()));
                    Taxas taxas;
                    if (!queryDocumentSnapshots.isEmpty()){
                        taxas = queryDocumentSnapshots.toObjects(Taxas.class).get(0);
                    } else{
                        taxas = new Taxas("1900-01-01_00:00:00", paciente.getEmail(), 0, 0, 0, 0);
                    }

                    onUpdate(taxas);
                }
            });

        pacienteSnapshot = PacienteController.getPacienteListener(paciente)
            .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(e != null){
                        Log.d("Firebase Error: ", e.getMessage());
                        return;
                    }
                    if(documentSnapshot.exists()) {
                        Log.d("Paciente", " Atualizado");
                        onUpdate(documentSnapshot.toObject(Paciente.class));
                    }
                    else pacienteSnapshot.remove();
                }
            });
    }

    public static Paciente getPaciente(){
        return paciente;
    }

    private static void onUpdate(Medida medida){
        lastMedida = medida;
        for(MedidaListener listener : medidaListeners){
            listener.onChangeMedida(medida);
        }
    }

    private static void onUpdate(Paciente pac){
        paciente = pac;
        for(PacienteListener listener : pacienteListeners){
            listener.onChangePaciente(pac);
        }
    }

    private static void onUpdate(Taxas taxas){
        lastTaxas = taxas;
        for(TaxasListener listener : taxasListeners){
            listener.onChangeTaxas(taxas);
        }
    }

    public static void addListener(MedidaListener listener){
        medidaListeners.add(listener);
        if(lastMedida != null) {
            listener.onChangeMedida(lastMedida);
        }
        else {
            MedidaController.getMedida(paciente).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty())
                        lastMedida = queryDocumentSnapshots.toObjects(Medida.class).get(0);
                    else
                        new Medida(0.0, 0.0, paciente.getEmail());
                    listener.onChangeMedida(lastMedida);
                }
            });
        }
    }

    public static void addListener(PacienteListener listener){
        pacienteListeners.add(listener);
        if(paciente != null) {
            listener.onChangePaciente(paciente);
        }
    }

    public static void addListener(TaxasListener listener){
        taxasListeners.add(listener);
        if(lastTaxas != null) {
            listener.onChangeTaxas(lastTaxas);
        }
        else {
            TaxasController.getTaxas(paciente).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty())
                        lastTaxas = queryDocumentSnapshots.toObjects(Taxas.class).get(0);
                    else
                        lastTaxas = new Taxas("1900-01-01_00:00:00", paciente.getEmail(), 0, 0, 0, 0);
                    listener.onChangeTaxas(lastTaxas);
                }
            });
        }
    }

    public static void removeListener(MedidaListener listener){
        medidaListeners.remove(listener);
    }

    public static void removeListener(PacienteListener listener){
        pacienteListeners.remove(listener);
    }

    public static void removeListener(TaxasListener listener){
        taxasListeners.remove(listener);
    }

    public static void onEnd(){
        if(medidaSnapshot != null && taxasSnapshot != null && pacienteSnapshot != null) {
            medidaSnapshot.remove();
            taxasSnapshot.remove();
            pacienteSnapshot.remove();
        }
    }
}
