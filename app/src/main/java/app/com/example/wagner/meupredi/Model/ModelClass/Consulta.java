package app.com.example.wagner.meupredi.Model.ModelClass;

import android.util.Log;

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
    private String date;
    private String time;

    public Consulta(){}

    public Consulta(String titulo, String local, Date date) {
        this(titulo, local);
        this.date = dateFormat.format(date);
        this.time = timeFormat.format(date);
    }

    public Consulta(String titulo, String local, String date, String time) {
        this(titulo, local);
        this.date = date;
        this.time = time;
    }

    private Consulta(String titulo, String local) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String printingDate(){
        SimpleDateFormat printDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return printDateFormat.format(dateFormat.parse(date));
        } catch (ParseException e) {
            Log.d("Parsing Error", e.getMessage());
        }
        return "";
        //(new StringBuilder(this.date).reverse().toString().replace("-", "/"));
    }

    @Override
    public String toString(){
        return this.titulo + " - " + this.printingDate() + " - " + this.time + " - " + this.local;
    }
}
