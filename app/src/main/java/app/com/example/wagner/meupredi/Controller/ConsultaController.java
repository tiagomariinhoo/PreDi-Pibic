package app.com.example.wagner.meupredi.Controller;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Nullable;

import app.com.example.wagner.meupredi.Database.ConsultaDAO;
import app.com.example.wagner.meupredi.Model.ModelClass.Consulta;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;
import app.com.example.wagner.meupredi.View.Application.MainViews.Perfil;

/**
 * Created by LeandroDias1 on 05/03/2018.
 */

public abstract class ConsultaController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public static Task<Void> addEvento(Paciente paciente, Consulta consulta){
        return ConsultaDAO.createConsulta(paciente, consulta);
    }

    /**
     * Printa todos os eventos ordenados
     *
     * @param paciente
     * @return
     */

    public static Task<QuerySnapshot> getAllConsultas(Paciente paciente){
        return ConsultaDAO.getAllConsultas(paciente);
    }

    public static ListenerRegistration getLiveConsultas(LiveUpdateHelper<Consulta> current, Paciente paciente){
        return ConsultaDAO.getLiveConsultas(paciente).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) Log.d("Firebase Error: ", e.getMessage());
                if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    current.onReceiveData(queryDocumentSnapshots.toObjects(Consulta.class));
                }
            }
        });
    }

    /**
     * Recebe o objeto paciente
     * retorna a data mais proxima verificando se falta menos de uma semana para a consulta
     * se sim, lança notificação
     *
     * @param paciente
     * @return Data do evento
     */

    public static void notifyConsulta(Perfil current, Paciente paciente){
        ConsultaDAO.getConsulta(paciente).addOnSuccessListener(current, new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()){
                    if(!queryDocumentSnapshots.isEmpty()) {
                        Consulta mostRecent = queryDocumentSnapshots.toObjects(Consulta.class).get(0);

                        Date sevenDaysFromNow = new Date();
                        long plusOneDay = (1000 * 60 * 60 * 24);
                        sevenDaysFromNow.setTime(sevenDaysFromNow.getTime() + plusOneDay * 7);

                        if (getDateObject(mostRecent).before(sevenDaysFromNow)) {
                            current.onNotify(mostRecent);
                        }
                    }
                }
            }
        });
    }

    public static Date getDateObject(Consulta consulta) {
        try {
            return dateFormat.parse(consulta.printingDate());
        } catch (ParseException e) {
            Log.d("DataParseError: ", e.getMessage());
        }
        return null;
    }

}
