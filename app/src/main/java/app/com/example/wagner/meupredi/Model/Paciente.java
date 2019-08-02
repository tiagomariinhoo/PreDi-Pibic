package app.com.example.wagner.meupredi.Model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by wagne on 31/03/2017.
 */

public class Paciente implements Serializable {

    private String uid;
    private String nome;
    private String sexo;
    private Timestamp nascimento;
    private double circunferencia;
    private double peso;
    private double altura;
    private double imc;
    private double glicoseJejum;
    private double glicose75g;
    private double hemoglobinaGlicolisada;
    private double colesterol;

    public Paciente() {}

    public Paciente(String uid, String nome, String sexo, Timestamp nascimento) {

        this.uid = uid;
        this.nome = nome;
        this.sexo = sexo;
        this.nascimento = nascimento;
        this.circunferencia = Double.NaN;
        this.peso = Double.NaN;
        this.altura = Double.NaN;
        this.imc = Double.NaN;
        this.glicose75g = Double.NaN;
        this.glicoseJejum = Double.NaN;
        this.colesterol = Double.NaN;
        this.hemoglobinaGlicolisada = Double.NaN;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Timestamp getNascimento() {
        return nascimento;
    }

    public void setNascimento(Timestamp nascimento) {
        this.nascimento = nascimento;
    }

    public String printNascimento(){
        SimpleDateFormat printFormat = new SimpleDateFormat("dd/MM/yyyy");
        return printFormat.format(nascimento.toDate());
    }

    public int idade(){
        Calendar today = Calendar.getInstance();
        Calendar nasc = Calendar.getInstance();
        nasc.setTime(nascimento.toDate());

        int years = today.get(Calendar.YEAR) - nasc.get(Calendar.YEAR);
        if(today.get(Calendar.MONTH) < nasc.get(Calendar.MONTH)
        || today.get(Calendar.DAY_OF_MONTH) < nasc.get(Calendar.DAY_OF_MONTH)){
            years--;
        }
        
        return years;
    }

    public double getCircunferencia() {
        return circunferencia;
    }

    public void setCircunferencia(double circunferencia) {
        this.circunferencia = circunferencia;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
        updateImc();
    }

    public double getAltura() {
        return altura;
    }

    public String stringAltura(){
        if(Double.isNaN(altura)) {
            return "--";
        } else {
            return String.format(Locale.getDefault(), "%.2f", altura);
        }
    }

    public void setAltura(double altura) {
        this.altura = altura;
        updateImc();
    }

    public double getImc() {
        return imc;
    }

    public String stringImc(){
        if(Double.isNaN(imc)) {
            return "--";
        } else {
            return String.format(Locale.getDefault(), "%.2f", imc);
        }
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double getGlicoseJejum() {
        return glicoseJejum;
    }

    public void setGlicoseJejum(double glicosejejum) {
        this.glicoseJejum = glicosejejum;
    }

    public double getHemoglobinaGlicolisada() {
        return hemoglobinaGlicolisada;
    }

    public void setHemoglobinaGlicolisada(double hemoglobinaGlicolisada) {
        this.hemoglobinaGlicolisada = hemoglobinaGlicolisada;
    }

    public double getGlicose75g() {
        return glicose75g;
    }

    public void setGlicose75g(double glicose75g) {
        this.glicose75g = glicose75g;
    }

    public double getColesterol() {
        return colesterol;
    }

    public void setColesterol(double _colesterol) {
        this.colesterol = _colesterol;
    }

    public void setTaxas(Taxas taxas){
        this.glicose75g = taxas.getGlicose75g();
        this.glicoseJejum = taxas.getGlicoseJejum();
        this.colesterol = taxas.getColesterol();
        this.hemoglobinaGlicolisada = taxas.getHemoglobinaGlico();
    }

    public void setMedida(Medida medida){
        this.setPeso(medida.getPeso());
        this.circunferencia = medida.getCircunferencia();
    }

    private void updateImc(){
        if(altura != 0 || !Double.isNaN(altura)) this.imc = peso/(altura*altura);
        else this.imc = peso; //assume altura como 1, caso seja 0
    }

    public StatusPaciente calculoStatus() {
        StatusPaciente status = StatusPaciente.SEM_DADOS;

        if(getGlicoseJejum() < 100){

            status = StatusPaciente.GLICOSE_JEJUM_BOA;

        } else if(getGlicoseJejum() >= 100 && getGlicoseJejum() <= 125){

            if(getGlicose75g() <= 199){

                if(getHemoglobinaGlicolisada() < 5.7){

                    status = StatusPaciente.GLICOSE_JEJUM_ALTERADA;

                } else if(getHemoglobinaGlicolisada() >= 5.7 && getHemoglobinaGlicolisada() <= 6.4){

                    status = StatusPaciente.PRE_DIABETES;

                } else if(!Double.isNaN(getHemoglobinaGlicolisada())){

                    status = StatusPaciente.DIABETES;

                }

            } else if(!Double.isNaN(getGlicose75g())){

                status = StatusPaciente.GLICOSE_75G_ALTA;

            }

        } else if(!Double.isNaN(getGlicoseJejum())){

            status = StatusPaciente.GLICOSE_JEJUM_ALTA;

        }

        return status;

    }

    public enum StatusPaciente {
        SEM_DADOS("Você precisa cadastrar taxas para que possamos fazer o diagnóstico, " +
                "isso pode ser feito na tela de taxas"),

        GLICOSE_JEJUM_BOA("Sua glicemia de jejum atual está boa, mas devem ser feitas " +
                            "novas avaliações a cada 3 anos ou conforme o risco."),

        GLICOSE_JEJUM_ALTERADA("Seus dados indicam um quadro de glicemia de jejum alterada, " +
                            "recomenda-se, nesse caso, ir ao médico assim que possível."),

        GLICOSE_JEJUM_ALTA("Sua glicemia em jejum está muito alta, recomenda-se, " +
                            "nesse caso, que procure um médico assim que possível."),

        GLICOSE_75G_ALTA("Sua glicemia 2 horas após sobrecarga com 75g de glicose está " +
                            "muito alta, você deve procurar um médico."),

        PRE_DIABETES("Há um alto risco que você se encontre no quadro de pré-diabetes, recomenda-se ir ao médico."),

        DIABETES("Há alto risco de diabetes, recomenda-se ir a um médico especialista para acompanhamento.");

        private String frase;

        StatusPaciente(String frase){
            this.frase = frase;
        }

        public String getFrase() {
            return frase;
        }
    }
}
