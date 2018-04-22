package app.com.example.wagner.meupredi.Model.ModelClass;

import java.util.Date;

/**
 * Created by LeandroDias1 on 05/03/2018.
 */

public class AgendaClass {
    private String titulo;
    private String local;
    private String date;
    private String time;

    public AgendaClass(String titulo, String local, String date, String time) {
        this.titulo = titulo;
        this.local = local;
        this.date = date;
        this.time = time;
    }

    public AgendaClass(){

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
}
