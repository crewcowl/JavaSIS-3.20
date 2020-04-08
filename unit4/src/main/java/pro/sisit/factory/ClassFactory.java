package pro.sisit.factory;

import pro.sisit.adapter.Serializable;
import pro.sisit.model.Author;
import pro.sisit.model.Book;
import pro.sisit.model.ClassType;

public class ClassFactory {

    public Serializable newT(ClassType type) {
        if(type == ClassType.Book){
            return new Book();
        } if (type == ClassType.Author) {
            return new Author();
        } else
            throw new RuntimeException("not supported type");
    }
}
