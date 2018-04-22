package app.com.example.wagner.meupredi.Model.ModelClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pichau on 02/07/2017.
 */

public class ExameClass {
    int id;
    int idPac;
    double glicose75g;
    double glicoseJejum;
    double colesterol;
    Date dataExame;

    public int getId() {
        return id;
    }

    public int getIdPac() {
        return idPac;
    }

    public void setIdPac(int idPac) {
        this.idPac = idPac;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGlicose75g() {
        return glicose75g;
    }

    public void setGlicose75g(double glicose75g) {
        this.glicose75g = glicose75g;
    }

    public double getGlicoseJejum() {
        return glicoseJejum;
    }

    public void setGlicoseJejum(double glicoseJejum) {
        this.glicoseJejum = glicoseJejum;
    }

    public double getColesterol() {
        return colesterol;
    }

    public void setColesterol(double colesterol) {
        this.colesterol = colesterol;
    }

    public void setDataExame(Date dataExame) {
        this.dataExame = dataExame;
    }

    public Date getDataExame() {
        return dataExame;
    }

    public void setDataExame(String dataExame) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date data = (Date) format.parse(dataExame);
        this.dataExame = data;
    }
}
