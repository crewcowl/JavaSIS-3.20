package pro.sisit.factory;

import pro.sisit.model.Author;
import pro.sisit.model.Book;
import pro.sisit.model.Entity;

public class ClassFactory<T> {

    public Entity newT(T obj) {
        if(obj.toString().contains("Book")){
            return new Book();
        } if (obj.toString().contains("Author")) {
            return new Author();
        } else
            return null;
    }
}
