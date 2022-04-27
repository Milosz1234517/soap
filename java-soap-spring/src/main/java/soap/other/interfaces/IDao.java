package soap.other.interfaces;

import java.util.ArrayList;

public interface IDao<T> {
    ArrayList<T> getAll();
    boolean save(T t);
}
