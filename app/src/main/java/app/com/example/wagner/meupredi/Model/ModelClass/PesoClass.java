package app.com.example.wagner.meupredi.Model.ModelClass;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class PesoClass {
    int idPeso;
    double peso;
    double circunferencia;
    String datePeso;
    String pacienteEmail;
    int flagPeso;
    int idPaciente;

    public PesoClass() {}

    public PesoClass(int idPeso, double peso, double circunferencia, String datePeso, int flagPeso, int idPaciente) {
        this(idPeso, peso, circunferencia, datePeso, flagPeso, idPaciente, null);
    }

    public PesoClass(int idPeso, double peso, double circunferencia, String datePeso, int flagPeso, int idPaciente, String pacienteEmail) {
        this.idPeso = idPeso;
        this.peso = peso;
        this.circunferencia = circunferencia;
        this.datePeso = datePeso;
        this.flagPeso = flagPeso;
        this.idPaciente = idPaciente;
        this.pacienteEmail = pacienteEmail;
    }

    public int getIdPeso() {
        return idPeso;
    }

    public void setIdPeso(int idPeso) {
        this.idPeso = idPeso;
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

    public String getDatePeso() {
        return datePeso;
    }

    public void setDatePeso(String datePeso) {
        this.datePeso = datePeso;
    }

    public int getFlagPeso() {
        return flagPeso;
    }

    public void setFlagPeso(int flagPeso) {
        this.flagPeso = flagPeso;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getPacienteEmail(){
        return pacienteEmail;
    }

    public void setPacienteEmail(String pacienteEmail){
        this.pacienteEmail = pacienteEmail;
    }

    @Override
    public String toString() {
        return String.format("Peso: %.2f kg -- Circunferência: %.2f cm", this.getPeso(), this.getCircunferencia());
    }
}
