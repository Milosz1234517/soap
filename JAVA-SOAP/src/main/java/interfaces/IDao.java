package interfaces;

import java.util.ArrayList;

public interface IDao<T> {
    ArrayList<T> getAll();
    void save(T t);
}
