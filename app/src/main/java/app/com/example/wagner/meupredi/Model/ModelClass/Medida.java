package app.com.example.wagner.meupredi.Model.ModelClass;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Medida implements Serializable {
    //private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
    private Timestamp dateMedida; // serves as ID
    private double peso;
    private double circunferencia;
    private int flagMedida = 1;
    private String emailPaciente;
    private String id;

    public Medida(double peso, double circunferencia, String emailPaciente) {
        this.dateMedida = Timestamp.now();
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

    public String stringDate() {
        return Long.toString(dateMedida.getSeconds())+Integer.toString(dateMedida.getNanoseconds());
    }

    public Timestamp getDateMedida(){
        return dateMedida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String printDate(){
        SimpleDateFormat printDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return printDateFormat.format(dateMedida.toDate());
    }

    public String printTime(){
        SimpleDateFormat printTimeFormat = new SimpleDateFormat("HH:mm:ss");
        return printTimeFormat.format(dateMedida.toDate());
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
