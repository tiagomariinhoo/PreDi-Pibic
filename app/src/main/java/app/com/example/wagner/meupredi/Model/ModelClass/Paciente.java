package app.com.example.wagner.meupredi.Model.ModelClass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import java.io.Serializable;

/**
 * Created by wagne on 31/03/2017.
 */

public class Paciente implements Serializable {

    private String nome;
    private String senha;
    private String email;
    private String sexo;
    private String nascimento;
    private Timestamp dataNascimento;
    private int idade;
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

    public Paciente(String nome, String senha, String email, String sexo, int idade, int ultimaDica) {

        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.sexo = sexo;
        this.idade = idade;
        this.circunferencia = 0;
        this.peso = 0;
        this.altura = 0;
        this.imc = -1;
        this.glicose75g = -1;
        this.glicoseJejum = -1;
        this.colesterol = -1;
        this.hemoglobinaGlicolisada = -1;
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

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
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
    public void calculo_diabetes(Context context) {

        //TODO: ajustar este método para os novos atributos

        Log.d("Começando ", "O CALCULOO");
        if (getGlicoseJejum() >= 100 && getGlicoseJejum() <= 125) {
            //Log.d("TTG!","");
            Toast.makeText(context, "TTG", Toast.LENGTH_LONG).show();
            if (getGlicose75g() < 140) {
                //Log.d("GJA","");
                Toast.makeText(context, "GJA!", Toast.LENGTH_LONG).show();
            } else if (getGlicose75g() >= 140 && getGlicose75g() < 199) {
                //Log.d("TDG"," Pré Diabetes");
                //Log.d("MEV", "Por 6 meses");
                Toast.makeText(context, "TDG Pré Diabetes, MEV por 6 meses!", Toast.LENGTH_LONG).show();

                if (getPeso() != -1 && getImc() >= 25) {
                    double pct = (getPeso() * 100) / getPeso();
                    pct = 100 - pct;

                    boolean metas;

                    if (pct > 5) metas = true;
                    else metas = false;

                    if (!metas) {
                        boolean risco = false;

                        if (getImc() >= 25 && getGlicose75g() >= 200 && getGlicoseJejum() >= 200)
                            risco = true;

                        if (!risco) {
                            Log.d("Reforçar", "MEV por 6 meses");
                            Toast.makeText(context, "Reforçar MEV por 6 meses", Toast.LENGTH_LONG).show();
                            boolean metas2 = false;

                            // TODO: 09/05/2017 Verificar esse metas2 pois será a parte do paciente de risco
                            if (!metas2) {
                                Log.d("MEV+", "Metformina");
                            } else {
                                Log.d("Acompanhamento", "A cada 6 meses");
                            }
                        } else {
                            Toast.makeText(context, "Você está correndo risco! Acompanhamento a cada 6 meses com medida do HbA1c", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        //Log.d("Acompanhamento", "A cada 6 meses com rastreamento anual");
                        Toast.makeText(context, "Parabéns por conseguir perder mais do que 5% do seu peso! A cada 6 meses com rastreamento anual!", Toast.LENGTH_LONG).show();
                    }

                }

            } else if (getGlicose75g() >= 200) {
                //Log.d("DM2 : ", "Avaliação e manejo do DM2");
                Toast.makeText(context, "Sua glicose está muito alta! Avaliação e manejo do DM2", Toast.LENGTH_LONG).show();
            }
        } else if (getGlicoseJejum() >= 126 || getGlicoseJejum() >= 200) {
            Toast.makeText(context, "Sua glicose está muito alta! Avaliação de manejo do DM2", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Sua glicose está normal! Avaliação a cada 3 anos ou conforme o risco.", Toast.LENGTH_LONG).show();
        }

        Log.d("glicose75 g : ", String.valueOf(getGlicose75g()));
    }
}
