package app.com.example.wagner.meupredi.Model.ModelClass;


/**
 * Created by LeandroDias1 on 27/09/2017.
 */

public class LipidogramaClass{
    int idPacienteLipidograma;
    double HDL;
    double LDL;
    double ColesterolTotal;
    double Triglicerides;
    String dataLipidograma;
    String localLipidograma;

    public int getIdPacienteLipidograma() {
        return idPacienteLipidograma;
    }

    public void setIdPacienteLipidograma(int idPacienteLipidograma) {
        this.idPacienteLipidograma = idPacienteLipidograma;
    }

    public double getHDL() {
        return HDL;
    }

    public void setHDL(double HDL) {
        this.HDL = HDL;
    }

    public double getLDL() {
        return LDL;
    }

    public void setLDL(double LDL) {
        this.LDL = LDL;
    }

    public double getColesterolTotal() {
        return ColesterolTotal;
    }

    public void setColesterolTotal(double colesterolTotal) {
        ColesterolTotal = colesterolTotal;
    }

    public double getTriglicerides() {
        return Triglicerides;
    }

    public void setTriglicerides(double triglicerides) {
        Triglicerides = triglicerides;
    }

    public String getDataLipidograma() {
        return dataLipidograma;
    }

    public void setDataLipidograma(String dataLipidograma) {
        this.dataLipidograma = dataLipidograma;
    }

    public String getLocalLipidograma() {
        return localLipidograma;
    }

    public void setLocalLipidograma(String localLipidograma) {
        this.localLipidograma = localLipidograma;
    }
}
