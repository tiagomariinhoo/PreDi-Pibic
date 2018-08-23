package app.com.example.wagner.meupredi.Model.ModelClass;

import com.google.firebase.firestore.ServerTimestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PesoClass {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String datePeso; // serves as ID
    double peso;
    double circunferencia;
    int flagPeso;
    private String emailPaciente;

    public PesoClass(String datePeso, double peso, double circunferencia, int flagPeso, String emailPaciente) {
        this(peso, circunferencia, flagPeso, emailPaciente);
        this.datePeso = datePeso;
    }
    //yyyy-MM-dd HH:mm:ss.S
    public PesoClass(Date datePeso, double peso, double circunferencia, int flagPeso, String emailPaciente) {
        this(peso, circunferencia, flagPeso, emailPaciente);
        String stringDate = dateFormat.format(datePeso);
        this.datePeso = stringDate;
    }

    private PesoClass(double peso, double circunferencia, int flagPeso, String emailPaciente) {
        this.peso = peso;
        this.circunferencia = circunferencia;
        this.flagPeso = flagPeso;
        this.emailPaciente = emailPaciente;
    }


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

    public String getDatePesoString() {
        return datePeso.toString();
    }

    public Date getDatePeso(){
        try {
            return dateFormat.parse(datePeso);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //date is automatically set by the database, so no setter allowed
    /*public void setDatePeso(String datePeso) {
        this.datePeso = datePeso;
    }*/

    public int getFlagPeso() {
        return flagPeso;
    }

    public void setFlagPeso(int flagPeso) {
        this.flagPeso = flagPeso;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }

    @Override
    public String toString() {
        return String.format("Peso: %.2f kg -- Circunferência: %.2f cm", this.getPeso(), this.getCircunferencia());
    }

}
