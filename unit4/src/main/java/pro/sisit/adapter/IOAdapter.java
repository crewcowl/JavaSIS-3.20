package pro.sisit.adapter;


public interface IOAdapter<T> {

    Serializable read(int rowIndex);
    int append(T entity);
}
