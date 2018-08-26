package app.com.example.wagner.meupredi.Model.ModelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Medida {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
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

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "Peso: %.2f kg -- Circunferência: %.2f cm", this.peso, this.circunferencia);
    }

}
