package app.com.example.wagner.meupredi.Controller;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.AgendaClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 05/03/2018.
 */

public class ControllerAgenda {
    DatabaseHandler db;
    Context context;

    public ControllerAgenda(Context context) {
        db = new DatabaseHandler(context);
        this.context = context;
    }

    public String adicionarEvento(Paciente paciente, AgendaClass evento){
        Log.d("ADICIONANDO PACIENTE : ", paciente.get_nome());
        Log.d("EVENTO INFO: ", evento.getTitulo() + " " + evento.getDate().toString());
        return db.modelAddDate(paciente, evento);
    }

    /**
     * Printa todos os eventos ordenados
     *
     * @param paciente
     * @return
     */

    public ArrayList<AgendaClass> getAllEventos(Paciente paciente){
        ArrayList<AgendaClass> consultas = db.modelGetAllAgendas(paciente);
        Log.d("Consultas do ", paciente.get_nome());
        for(int i=0;i<consultas.size();i++){
            for(int j=0;j<consultas.size()-1;j++){
                int com = consultas.get(j).getDate().compareTo(consultas.get(j+1).getDate());
                if(com == -1){
                    Collections.swap(consultas, j,j+1);
                } else if(com == 0){
                    int com2 = consultas.get(j).getTime().compareTo(consultas.get(j+1).getTime());
                    if(com2 == -1){
                        Collections.swap(consultas, j, j+1);
                    }
                }
            }
        }
        for(int i=0;i<consultas.size();i++){
            Log.d("Consulta : ", consultas.get(i).getTitulo());
            Log.d(consultas.get(i).getDate(), consultas.get(i).getTime());
        }

        return consultas;
    }

    /**
     * Ordena as consultas.
     *
     * @param consultas
     * @return
     */

    public ArrayList<AgendaClass> sortConsultas(ArrayList<AgendaClass> consultas){
        for(int i=0;i<consultas.size();i++){
            for(int j=0;j<consultas.size()-1;j++){
                int com = consultas.get(j).getDate().compareTo(consultas.get(j+1).getDate());
                Log.d(consultas.get(j).getDate(), " -- " + consultas.get(j+1).getDate());
                Log.d("Com", Integer.toString(com));
                if(com > 0){
                    Collections.swap(consultas, j,j+1);
                } else if(com == 0){
                    int com2 = consultas.get(j).getTime().compareTo(consultas.get(j+1).getTime());
                    if(com2 > 0){
                        Collections.swap(consultas, j, j+1);
                    }
                }
            }
        }
        return consultas;
    }

    private Date getDateFromString(String s){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse(s);
            return date;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Passa o objeto paciente
     * retorna a data mais proxima verificando se falta menos de uma semana para a consulta
     * se sim, lança notificação
     *
     * @param paciente
     * @return Data do evento
     */

    public Date eventNotify(Paciente paciente){
        ArrayList<AgendaClass> consultas = db.modelGetAllAgendas(paciente);
        consultas = sortConsultas(consultas);

        Log.d("EventNotify : ", paciente.get_nome());
        for(int i=0;i<consultas.size();i++){
            Log.d("Consulta : ", consultas.get(i).getTitulo());
            Log.d("Data", consultas.get(i).getDate());
        }

        ArrayList<Date> datas = new ArrayList<>();

        for(int i=0;i<consultas.size();i++){
            datas.add(getDateFromString(consultas.get(i).getDate()));
        }

        Log.d("Check array Date ", "xd");

        for(int i=0;i<datas.size();i++){
            Log.d("Date : ", datas.get(i).toString());
        }

        Date prox = new Date();
        Date aux = new Date();
        boolean at = false;

        for(int i=0;i<datas.size();i++){
            long auxi = datas.get(i).getTime() - aux.getTime();

            Log.d("Resultado dif Notify : ", Long.toString(auxi));

            if(datas.get(i).getTime() - aux.getTime() > 0){
                prox = datas.get(i);
                at = true;
                break;
            }
        }

        Log.d("Hoje: ", aux.toString());
        Log.d("Prox: ", prox.toString());

        try{
            if(at == true){
                long diff = prox.getTime() - aux.getTime();
                float days = (diff / (1000*60*60*24));
                Log.d("Days Notify: ", Float.toString(days));
                if(days <= 7){
                    return prox;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
