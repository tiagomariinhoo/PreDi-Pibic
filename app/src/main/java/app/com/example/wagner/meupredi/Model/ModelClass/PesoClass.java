package app.com.example.wagner.meupredi.Model.ModelClass;

public class PesoClass {
    int idPeso;
    double peso;
    double circunferencia;
    String datePeso;
    int flagPeso;
    int idPaciente;

    public PesoClass(int idPeso, double peso, double circunferencia, String datePeso, int flagPeso, int idPaciente) {
        this.idPeso = idPeso;
        this.peso = peso;
        this.circunferencia = circunferencia;
        this.datePeso = datePeso;
        this.flagPeso = flagPeso;
        this.idPaciente = idPaciente;
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
}
