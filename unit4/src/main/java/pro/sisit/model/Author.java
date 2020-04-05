package pro.sisit.model;

import pro.sisit.adapter.CSVConverter;


import java.util.ArrayList;
import java.util.Objects;

public class Author extends Entity implements CSVConverter {

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
    public void setCSVLine(ArrayList<String> text) {
        this.name = text.get(0);
        this.birthPlace = text.get(1);
    }

    @Override
    public ArrayList<String>  getCSVLine() {
        ArrayList<String> text = new ArrayList<>();
        text.add(this.name);
        text.add(this.birthPlace);
        return text;
    }
}
