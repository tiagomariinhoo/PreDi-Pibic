package app.com.example.wagner.meupredi.View.Application.MainViews;

import java.util.List;

public interface GraphHelper<T> {
    void onReceiveData(List<T> data);
}
