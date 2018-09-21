package app.com.example.wagner.meupredi.Model.ModelClass;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Medida implements Serializable {
    //private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
    private String dateMedida; // serves as ID
    private double peso;
    private double circunferencia;
    private int flagMedida = 1;
    private String emailPaciente;

    public Medida(String dateMedida, double peso, double circunferencia, String emailPaciente) {
        this(peso, circunferencia, emailPaciente);
        this.dateMedida = dateMedida;
    }
    //yyyy-MM-dd HH:mm:ss.S
    public Medida(Date dateMedida, double peso, double circunferencia, String emailPaciente) {
        this(peso, circunferencia, emailPaciente);
        this.dateMedida = dateFormat.format(dateMedida);
    }

    private Medida(double peso, double circunferencia, String emailPaciente) {
        this.peso = peso;
        this.circunferencia = circunferencia;
        this.emailPaciente = emailPaciente;
    }

    public Medida(){}

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getCircunferencia() {
        return circunferencia;
    }

    public void setCircunferencia(double circunferencia) {
        this.circunferencia = circunferencia;
    }

    public String getDateMedida() {
        return dateMedida;
    }

    public String printDate(){
        SimpleDateFormat printDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return printDateFormat.format(dateFormat.parse(dateMedida));
        } catch (ParseException e) {
            Log.d("Parsing Error", e.getMessage());
        }
        return "";
    }

    public String printTime(){
        SimpleDateFormat printTimeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            return printTimeFormat.format(dateFormat.parse(dateMedida));
        } catch (ParseException e) {
            Log.d("Parsing Error", e.getMessage());
        }
        return "";
    }

    public int getFlagMedida() {
        return flagMedida;
    }

    public void setFlagMedida(int flagMedida) {
        this.flagMedida = flagMedida;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }

    public String printPeso(){
        return String.format("%.2f kg", peso);
    }
    public String printCircunferencia(){
        return String.format("%.2f cm", circunferencia);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "Peso: %.2f kg -- Circunferência: %.2f cm", this.peso, this.circunferencia);
    }



}
