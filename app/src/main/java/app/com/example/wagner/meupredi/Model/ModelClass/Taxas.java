package app.com.example.wagner.meupredi.Model.ModelClass;

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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
    private Timestamp dateTaxas; // serves as ID
    private String emailPaciente;
    private String id;
    private double glicose75g;
    private double glicoseJejum;
    private double colesterol;
    private double hemoglobinaGlico;
    private int flagTaxa = 1;


    public Taxas(String emailPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
        this.dateTaxas = Timestamp.now();
        this.emailPaciente = emailPaciente;
        this.glicose75g = glicose75g;
        this.glicoseJejum = glicoseJejum;
        this.colesterol = colesterol;
        this.hemoglobinaGlico = hemoglobinaGlico;
    }

    public Taxas(){}

    public int getFlagTaxa() {
        return flagTaxa;
    }

    public void setFlagTaxa(int flagTaxa) {
        this.flagTaxa = flagTaxa;
    }

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

    public String stringDate() {
        return Long.toString(dateTaxas.getSeconds())+Integer.toString(dateTaxas.getNanoseconds());
    }

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

}
