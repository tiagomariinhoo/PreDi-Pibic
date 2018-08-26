package app.com.example.wagner.meupredi.Database;

import java.util.List;

public interface GraphHelper<T> {
    void onReceiveData(List<T> data);
}
