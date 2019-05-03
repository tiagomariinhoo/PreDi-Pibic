package app.com.example.wagner.meupredi.Model;

import android.util.Log;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LeandroDias1 on 05/03/2018.
 */
public class Consulta {

    private String id;
    private String emailPaciente;
    private String titulo;
    private String local;
    private Timestamp date;


    public Consulta(){}

    public Consulta(String emailPaciente, String titulo, String local, Date date) {
        this.date = new Timestamp(date);
        this.emailPaciente = emailPaciente;
        this.titulo = titulo;
        this.local = local;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date){
        this.date = date;
    }

    public String printingDate(){
        SimpleDateFormat printDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return printDateFormat.format(date.toDate());
    }

    @Override
    public String toString(){
        return this.titulo + " - " + this.printingDate() + " - " + this.local;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }
}
