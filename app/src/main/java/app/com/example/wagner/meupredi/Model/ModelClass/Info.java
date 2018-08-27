package app.com.example.wagner.meupredi.Model.ModelClass;

public class Info {
    private String nome;
    private String dateNascimento;
    private double altura;
    private double imc;

    public Info(String nome, String dateNascimento, double altura, double imc) {
        this.nome = nome;
        this.dateNascimento = dateNascimento;
        this.altura = altura;
        this.imc = imc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDateNascimento() {
        return dateNascimento;
    }

    public void setDateNascimento(String dateNascimento) {
        this.dateNascimento = dateNascimento;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }
}
