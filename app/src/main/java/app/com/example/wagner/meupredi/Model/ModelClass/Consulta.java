package app.com.example.wagner.meupredi.Model.ModelClass;

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
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private String titulo;
    private String local;
    private Timestamp date;


    public Consulta(){}

    public Consulta(String titulo, String local, Date date) {
        this.date = new Timestamp(date);
        Log.e("TEST NANOSEOND", Integer.toString(this.date.getNanoseconds()));
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

    public String stringDate() {
        return String.format("%010d%09d", date.getSeconds(), date.getNanoseconds());
    }

    public Timestamp getDate() {
        return date;
    }

    public String printingDate(){
        SimpleDateFormat printDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return printDateFormat.format(date.toDate());
    }

    @Override
    public String toString(){
        return this.titulo + " - " + this.printingDate() + " - " + this.local;
    }
}
