package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.util.Log;

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
import app.com.example.wagner.meupredi.View.Application.TaxasListener;

public final class PacienteUpdater {

    private static List<MedidaListener> medidaListeners = new ArrayList<>();
    private static List<TaxasListener> taxasListeners = new ArrayList<>();
    private static ListenerRegistration medidaSnapshot;
    private static ListenerRegistration taxasSnapshot;
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
                        medida = new Medida("01-01-1900", 0, 0, paciente.getEmail());
                    }

                    paciente.setPeso(medida.getPeso());
                    paciente.setCircunferencia(medida.getCircunferencia());
                    paciente.setImc(imcAtualizado());
                    PacienteController.atualizarPaciente(paciente);
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
                    Taxas taxas;
                    if (!queryDocumentSnapshots.isEmpty()){
                        taxas = queryDocumentSnapshots.toObjects(Taxas.class).get(0);
                    } else{
                        taxas = new Taxas("01-01-1900", paciente.getEmail(), 0, 0, 0, 0);
                    }
                    paciente.setTaxas(taxas);
                    PacienteController.atualizarPaciente(paciente);
                    onUpdate(taxas);
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

    private static void onUpdate(Taxas taxas){
        lastTaxas = taxas;
        for(TaxasListener listener : taxasListeners){
            listener.onChangeTaxas(taxas);
        }
    }

    public static void addListener(MedidaListener listener){
        medidaListeners.add(listener);
        listener.onChangeMedida(lastMedida);
    }

    public static void addListener(TaxasListener listener){
        taxasListeners.add(listener);
        listener.onChangeTaxas(lastTaxas);
    }

    public static void removeListener(MedidaListener listener){
        medidaListeners.remove(listener);
    }

    public static void removeListener(TaxasListener listener){
        taxasListeners.remove(listener);
    }

    public static void onEnd(){
        if(medidaSnapshot != null && taxasSnapshot != null) {
            medidaSnapshot.remove();
            taxasSnapshot.remove();
        }
    }

    //recalcula imc
    private static double imcAtualizado(){
        double imc;
        if (paciente.getPeso() > 0 && paciente.getAltura() > 0) {
            imc = paciente.getPeso()/(paciente.getAltura()*paciente.getAltura());
            String imcFormatado = String.format(Locale.ENGLISH, "%.2f", imc);
            imc = Double.parseDouble(imcFormatado);
        } else {
            imc = 0;
        }
        return imc;
    }
}
