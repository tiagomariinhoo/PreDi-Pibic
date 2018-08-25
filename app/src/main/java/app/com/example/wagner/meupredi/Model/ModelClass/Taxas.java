package app.com.example.wagner.meupredi.Model.ModelClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pichau on 02/07/2017.
 */

public class Taxa {
    int id;
    int idPac;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String dateTaxas; // serves as ID
    private String emailPaciente;
    private double glicose75g;
    private double glicoseJejum;
    private double colesterol;
    private double hemoglobinaGlico;
    int flagTaxa;

    public Taxa(String dateTaxas, String emailPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
        this(emailPaciente, glicose75g, glicoseJejum, colesterol, hemoglobinaGlico);
        this.dateTaxas = dateTaxas;
    }

    public Taxa(Date dateTaxas, String emailPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
        this(emailPaciente, glicose75g, glicoseJejum, colesterol, hemoglobinaGlico);
        this.dateTaxas = dateFormat.format(dateTaxas);
    }

    private Taxa(String emailPaciente, double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlico){
        this.emailPaciente = emailPaciente;
        this.glicose75g = glicose75g;
        this.glicoseJejum = glicoseJejum;
        this.colesterol = colesterol;
        this.hemoglobinaGlico = hemoglobinaGlico;
    }

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

    public int getId() {
        return id;
    }

    public int getIdPac() {
        return idPac;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }

    public void setIdPac(int idPac) {
        this.idPac = idPac;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDateTaxasString() {
        return dateTaxas.toString();
    }

    public Date getDateTaxas(){
        try {
            return dateFormat.parse(dateTaxas);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
