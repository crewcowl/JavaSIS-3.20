package pro.sisit.model;

import pro.sisit.adapter.CSVConverter;
import pro.sisit.service.ObjectParser;


import java.util.Objects;

public class Author extends ObjectParser implements CSVConverter {

    private String name;
    private String birthPlace;

    public Author() {
        this.name = null;
        this.birthPlace = null;
    }

    public Author(String name, String birthPlace) {
        this.name = name;
        this.birthPlace = birthPlace;
    }

    public String getName() {
        return name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return getName().equals(author.getName()) &&
            getBirthPlace().equals(author.getBirthPlace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthPlace());
    }

    @Override
    public void setCSVLine(String text) {
        CSVStringToAuthor(text,this);
    }

    @Override
    public String  getCSVLine() {
        return authorToCSVString(this);
    }
}
