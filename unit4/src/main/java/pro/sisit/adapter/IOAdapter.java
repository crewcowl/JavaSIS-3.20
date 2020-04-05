package pro.sisit.adapter;

import pro.sisit.model.Entity;

public interface IOAdapter<T> {

    Entity read(int rowIndex);
    int append(T entity);
}
