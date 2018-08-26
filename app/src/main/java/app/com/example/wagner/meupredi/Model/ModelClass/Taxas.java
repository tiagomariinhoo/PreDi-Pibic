package app.com.example.wagner.meupredi.Model.ModelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Pichau on 02/07/2017.
 */

public class Taxas {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
    private String dateTaxas; // serves as ID
    private String emailPaciente;
    private double glicose75g;
    private double glicoseJejum;
    private double colesterol;
    private double hemoglobinaGlico;
    private int flagTaxa = 1;

    public Taxas(String dateTaxas, String emailPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
        this(emailPaciente, glicose75g, glicoseJejum, colesterol, hemoglobinaGlico);
        this.dateTaxas = dateTaxas;
    }

    public Taxas(Date dateTaxas, String emailPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
        this(emailPaciente, glicose75g, glicoseJejum, colesterol, hemoglobinaGlico);
        this.dateTaxas = dateFormat.format(dateTaxas);
    }

    private Taxas(String emailPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
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

    public String getDateTaxas() {
        return dateTaxas;
    }


}
