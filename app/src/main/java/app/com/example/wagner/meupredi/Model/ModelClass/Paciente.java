        package app.com.example.wagner.meupredi.Model.ModelClass;

        import android.content.Context;
        import android.util.Log;
        import android.widget.Toast;
        import java.io.Serializable;
        import java.util.ArrayList;

        /**
 * Created by wagne on 31/03/2017.
 */

public class Paciente implements Serializable {

            private int id;
            private String nome;
            private String senha;
            private String email;
            private String sexo;
            private String nascimento;
            private int idade;
            private int exTotal; //Total atual
            private int exMax; //Meta da semana
            private int ultimaDica;
            private double circunferencia;
            private double peso;
            private double altura;
            private double imc;
            private double hba1c;
            private double glicoseJejum;
            private double glicose75g;
            private double hemoglobinaGlicolisada;
            private double colesterol;
            private double lipidograma; // Não está sendo usado
            private double hemograma; // Não está sendo usado
            private double tireoide;
            private ArrayList<Float> pesos = new ArrayList<Float>();

            public ArrayList<Float> get_pesos() {
                return pesos;
            }

            public void set_pesos(float p) {
                this.pesos.add(p);
            }
            public Paciente() {
            }

            public Paciente(int id, String nome, String senha, String email, String sexo, int idade, double circunferencia, double peso, double altura, int ultimaDica) {

                //valores com -1 serão setados ou calculados após o cadastro inicial
                this.id = id;
                this.nome = nome;
                this.senha = senha;
                this.email = email;
                this.sexo = sexo;
                this.idade = idade;
                this.circunferencia = circunferencia;
                this.peso = peso;
                this.altura = altura;
                this.imc = -1;
                this.hba1c = -1;
                this.glicose75g = -1;
                this.glicoseJejum = -1;
                this.colesterol = -1;
                this.lipidograma = -1;
                this.hemograma = -1;
                this.tireoide = -1;
                this.hemoglobinaGlicolisada = -1;
                this.ultimaDica = ultimaDica;
            }

            public void getInfo(){
                Log.d("Get info: ", getNome());
                Log.d("Nome: ", getNome());
                Log.d("Peso: ", String.valueOf(getPeso()));
            }

            public Integer getUltimaDica() {
                return ultimaDica;
            }

            public void setUltimaDica(int ultimaDica) {
                this.ultimaDica = ultimaDica;
            }

            public Integer getId() { return id; }

            public void setId(int id) {
                this.id = id;
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

            public Integer getIdade() {
                return idade;
            }

            public void setIdade(int idade) {
                this.idade = idade;
            }

            public Double getCircunferencia() {
                return circunferencia;
            }

            public void setCircunferencia(double circunferencia) {
                this.circunferencia = circunferencia;
            }

            public Double getPeso() {
                return peso;
            }

            public void setPeso(double peso) {
                this.peso = peso;
            }

            public Double getAltura() {
                return altura;
            }

            public void setAltura(double altura) {
                this.altura = altura;
            }

            public Double getImc() {
                return imc;
            }

            public void setImc(double imc) {
                this.imc = imc;
            }

            public Double getHba1c() {
                return hba1c;
            }

            public void setHba1c(double hba1c) {
                this.hba1c = hba1c;
            }

            public Double getGlicoseJejum() {
                return glicoseJejum;
            }

            public void setGlicoseJejum(double glicosejejum) {
                this.glicoseJejum = glicosejejum;
            }

            public Double getHemoglobinaGlicolisada() {
                return hemoglobinaGlicolisada;
            }

            public void setHemoglobinaGlicolisada(double _hemoglobinaglicolisada) {
                this.hemoglobinaGlicolisada = _hemoglobinaglicolisada;
            }

            public Double getGlicose75g() {
                return glicose75g;
            }

            public void setGlicose75g(double glicose75g) {
                this.glicose75g = glicose75g;
            }

            public Double getColesterol() {
                return colesterol;
            }

            public void setColesterol(double _colesterol) {
                this.colesterol = _colesterol;
            }

            public Double getLipidograma() {
                return lipidograma;
            }

            public void setLipidograma(double lipidograma) {
                this.lipidograma = lipidograma;
            }

            public Double getHemograma() {
                return hemograma;
            }

            public void setHemograma(double hemograma) {
                this.hemograma = hemograma;
            }

            public Double getTireoide() {
                return tireoide;
            }

            public void setTireoide(double tireoide) {
                this.tireoide = tireoide;
            }

            public Integer getExTotal() {
                return exTotal;
            }

            public void setExTotal(int exTotal) {
                this.exTotal = exTotal;
            }

            public Integer getExMax() {
                return exMax;
            }

            public void setExMax(int exMax) {
                this.exMax = exMax;
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
