package app.com.example.wagner.meupredi.Model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Pichau on 02/07/2017.
 */

public class Taxas implements Serializable {

    private String id;
    private String uidPaciente;
    private double glicose75g;
    private double glicoseJejum;
    private double colesterol;
    private double hemoglobinaGlico;
    private Timestamp dateTaxas;
    private boolean deleted = false;

    public Taxas(String uidPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
        this.dateTaxas = Timestamp.now();
        this.uidPaciente = uidPaciente;
        this.glicose75g = glicose75g;
        this.glicoseJejum = glicoseJejum;
        this.colesterol = colesterol;
        this.hemoglobinaGlico = hemoglobinaGlico;
    }

    public Taxas(){}

    public double getHemoglobinaGlico() {
        return hemoglobinaGlico;
    }

    public String stringHemoglobinaGlico(){
        if(Double.isNaN(hemoglobinaGlico)) {
            return "--";
        } else {
            return String.format(Locale.getDefault(), "%.2f", hemoglobinaGlico);
        }
    }

    public void setHemoglobinaGlico(double hemoglobinaGlico) {
        this.hemoglobinaGlico = hemoglobinaGlico;
    }

    public double getGlicose75g() {
        return glicose75g;
    }

    public String stringGlicose75g(){
        if(Double.isNaN(glicose75g)) {
            return "--";
        } else {
            return String.format(Locale.getDefault(), "%.2f", glicose75g);
        }
    }

    public void setGlicose75g(double glicose75g) {
        this.glicose75g = glicose75g;
    }

    public double getGlicoseJejum() {
        return glicoseJejum;
    }

    public String stringGlicoseJejum(){
        if(Double.isNaN(glicoseJejum)) {
            return "--";
        } else {
            return String.format(Locale.getDefault(), "%.2f", glicoseJejum);
        }
    }

    public void setGlicoseJejum(double glicoseJejum) {
        this.glicoseJejum = glicoseJejum;
    }

    public double getColesterol() {
        return colesterol;
    }

    public String stringColesterol(){
        if(Double.isNaN(colesterol)) {
            return "--";
        } else {
            return String.format(Locale.getDefault(), "%.2f", colesterol);
        }
    }

    public void setColesterol(double colesterol) {
        this.colesterol = colesterol;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Timestamp getDateTaxas() {
        return dateTaxas;
    }

    public String printGlicoseJejum(){
        return stringGlicoseJejum()+" mg/dL";
    }
    public String printGlicose75g(){
        return stringGlicose75g()+" mg/dL";
    }

    public String printHemoglobinaGlico(){
        return stringHemoglobinaGlico()+" %";
    }


    public String printDate(){
        SimpleDateFormat printFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return printFormat.format(dateTaxas.toDate());

    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUidPaciente() {
        return uidPaciente;
    }

    public void setUidPaciente(String uidPaciente) {
        this.uidPaciente = uidPaciente;
    }
}
