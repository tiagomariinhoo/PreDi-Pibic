package app.com.example.wagner.meupredi.Model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Pichau on 02/07/2017.
 */

public class Taxas implements Serializable {

    private String id;
    private String emailPaciente;
    private double glicose75g;
    private double glicoseJejum;
    private double colesterol;
    private double hemoglobinaGlico;
    private Timestamp dateTaxas;
    private boolean deleted = false;

    public Taxas(String emailPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
        this.dateTaxas = Timestamp.now();
        this.emailPaciente = emailPaciente;
        this.glicose75g = glicose75g;
        this.glicoseJejum = glicoseJejum;
        this.colesterol = colesterol;
        this.hemoglobinaGlico = hemoglobinaGlico;
    }

    public Taxas(){}

    public double getHemoglobinaGlico() {
        return hemoglobinaGlico;
    }

    public void setHemoglobinaGlico(double hemoglobinaGlico) {
        this.hemoglobinaGlico = hemoglobinaGlico;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }

    public double getGlicose75g() {
        return glicose75g;
    }

    public void setGlicose75g(double glicose75g) {
        this.glicose75g = glicose75g;
    }

    public double getGlicoseJejum() {
        return glicoseJejum;
    }

    public void setGlicoseJejum(double glicoseJejum) {
        this.glicoseJejum = glicoseJejum;
    }

    public double getColesterol() {
        return colesterol;
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
        return String.format("%.2f mg/dL", glicoseJejum);
    }
    public String printGlicose75g(){
        return String.format("%.2f mg/dL", glicose75g);
    }

    public String printHemoglobinaGlicada(){
        return String.format("%.2f %%", hemoglobinaGlico);
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
}