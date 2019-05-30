package app.com.example.wagner.meupredi.Model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wagne on 31/03/2017.
 */

public class Paciente implements Serializable {

    private String nome;
    private String email;
    private String sexo;
    private Timestamp nascimento;
    private int ultimaDica;
    private double circunferencia;
    private double peso;
    private double altura;
    private double imc;
    private double glicoseJejum;
    private double glicose75g;
    private double hemoglobinaGlicolisada;
    private double colesterol;

    public Paciente() {}

    public Paciente(String nome, String email, String sexo, Timestamp nascimento, int ultimaDica) {

        this.nome = nome;
        this.email = email;
        this.sexo = sexo;
        this.nascimento = nascimento;
        this.circunferencia = 0;
        this.peso = 0;
        this.altura = 0;
        this.imc = 0;
        this.glicose75g = 0;
        this.glicoseJejum = 0;
        this.colesterol = 0;
        this.hemoglobinaGlicolisada = 0;
        this.ultimaDica = ultimaDica;
    }

    public int getUltimaDica() {
        return ultimaDica;
    }

    public void setUltimaDica(int ultimaDica) {
        this.ultimaDica = ultimaDica;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setAltura(double altura) {
        this.altura = altura;
        updateImc();
    }

    public double getImc() {
        return imc;
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
        if(altura != 0) this.imc = peso/(altura*altura);
        else this.imc = peso; //assume altura como 1, caso seja 0
    }

    public StatusPaciente calculoStatus() {

        if(getGlicoseJejum() < 100){

            return StatusPaciente.GLICOSE_JEJUM_BOA;

        } else if(getGlicoseJejum() >= 100 && getGlicoseJejum() <= 125){

            if(getGlicose75g() <= 199){

                if(getHemoglobinaGlicolisada() < 5.7){

                    return StatusPaciente.GLICOSE_JEJUM_ALTERADA;

                } else if(getHemoglobinaGlicolisada() >= 5.7 && getHemoglobinaGlicolisada() <= 6.4){

                    return StatusPaciente.PRE_DIABETES;

                } else{

                    return StatusPaciente.DIABETES;

                }

            } else{

                return StatusPaciente.GLICOSE_75G_ALTA;

            }

        } else{

            return StatusPaciente.GLICOSE_JEJUM_ALTA;

        }

    }

    public void checkStatus(){
        switch(this.calculoStatus()){
            case DIABETES:

                break;
        }
        if(this.calculoStatus() == StatusPaciente.DIABETES){

        }
    }

    public enum StatusPaciente {
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
