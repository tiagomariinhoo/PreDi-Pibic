package app.com.example.wagner.meupredi.Model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Medida implements Serializable {

    private String id;
    private String uidPaciente;
    private double peso;
    private double circunferencia;
    private Timestamp dateMedida;
    private boolean deleted = false;

    public Medida(String uidPaciente, double peso, double circunferencia) {
        this.dateMedida = Timestamp.now();
        this.peso = peso;
        this.circunferencia = circunferencia;
        this.uidPaciente = uidPaciente;
    }

    public Medida(){}

    public double getPeso() {
        return peso;
    }

    public String stringPeso(){
        if(Double.isNaN(peso)) {
            return "--";
        } else {
            return String.format(Locale.getDefault(), "%.2f", peso);
        }
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getCircunferencia() {
        return circunferencia;
    }

    public String stringCircunferencia(){
        if(Double.isNaN(circunferencia)) {
            return "--";
        } else {
            return String.format(Locale.getDefault(), "%.2f", circunferencia);
        }
    }

    public void setCircunferencia(double circunferencia) {
        this.circunferencia = circunferencia;
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
