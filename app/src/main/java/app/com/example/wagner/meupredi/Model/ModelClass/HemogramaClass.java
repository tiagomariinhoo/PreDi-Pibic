package app.com.example.wagner.meupredi.Model.ModelClass;

/**
 * Created by LeandroDias1 on 27/09/2017.
 */

public class HemogramaClass {
    int idPacienteHemograma;
    double hemoglobina;
    double hematocrito;
    double vgm;
    double chcm;
    double chgm;
    double rwd;
    String dataHemograma;

    public int getIdPacienteHemograma() {
        return idPacienteHemograma;
    }

    public void setIdPacienteHemograma(int idPacienteHemograma) {
        this.idPacienteHemograma = idPacienteHemograma;
    }

    public double getHemoglobina() {
        return hemoglobina;
    }

    public void setHemoglobina(double hemoglobina) {
        this.hemoglobina = hemoglobina;
    }

    public double getHematocrito() {
        return hematocrito;
    }

    public void setHematocrito(double hematocrito) {
        this.hematocrito = hematocrito;
    }

    public double getVgm() {
        return vgm;
    }

    public void setVgm(double vgm) {
        this.vgm = vgm;
    }

    public double getChcm() {
        return chcm;
    }

    public void setChcm(double chcm) {
        this.chcm = chcm;
    }

    public double getChgm() {
        return chgm;
    }

    public void setChgm(double chgm) {
        this.chgm = chgm;
    }

    public double getRwd() {
        return rwd;
    }

    public void setRwd(double rwd) {
        this.rwd = rwd;
    }

    public String getDataHemograma() {
        return dataHemograma;
    }

    public void setDataHemograma(String dataHemograma) {
        this.dataHemograma = dataHemograma;
    }
}
