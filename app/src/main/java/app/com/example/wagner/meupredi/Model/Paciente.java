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

    public void setTaxas(double glicose75g, double glicoseJejum, double colesterol, double hemoglobinaGlicolisada){
        this.glicose75g = glicose75g;
        this.glicoseJejum = glicoseJejum;
        this.colesterol = colesterol;
        this.hemoglobinaGlicolisada = hemoglobinaGlicolisada;
    }

    private void updateImc(){
        if(altura != 0) this.imc = peso/(altura*altura);
        else this.imc = peso; //assume altura como 1, caso seja 0
    }

    //metodo chamado na classe MenuPrincipal para verificar situacao do paciente
    //TODO: mudar retorno pra string para ser usano no resultado do relatório
    public String calculoDiabetes() {

        String mensagem;

        if(getGlicoseJejum() < 100){
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Sua glicemia em jejum atual está boa, mas devem ser feitas novas " +
                    "avaliações a cada 3 anos ou conforme o risco.";
        } else if(getGlicoseJejum() >= 100 && getGlicoseJejum() <= 125){

            if(getGlicose75g() <= 199){

                if(getHemoglobinaGlicolisada() < 5.7){
                    //TODO: ver se essa mensagem ficou boa
                    mensagem = "Há chance de Glicemia de Jejum Alterada, " +
                            "você deve procurar um médico.";
                } else if(getHemoglobinaGlicolisada() >= 5.7 && getHemoglobinaGlicolisada() <= 6.4){
                    //TODO: ver se essa mensagem ficou boa
                    mensagem = "Há um alto risco de pré-diabetes, você deve procurar um médico.";
                } else{
                    //TODO: ver se essa mensagem ficou boa
                    mensagem = "Há alto risco de diabetes, você deve procurar um médido.";
                }

            } else{
                //TODO: ver se essa mensagem ficou boa
                mensagem = "Sua glicose após 75g está muito alta, você deve procurar um médico.";
            }

        } else{
            //TODO: ver se essa mensagem ficou boa
            mensagem = "Sua glicemia em jejum está muito alta, você deve procurar um médico.";
        }

        return mensagem;
    }
}
