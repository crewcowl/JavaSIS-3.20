package pro.sisit.adapter;

public interface IOAdapter<T> {

    T read(int rowIndex);
    int append(T entity);

}
