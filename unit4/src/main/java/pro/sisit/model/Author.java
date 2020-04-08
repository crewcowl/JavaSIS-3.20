package pro.sisit.model;

import pro.sisit.adapter.Serializable;


import java.util.ArrayList;
import java.util.Objects;

public class Author implements Serializable {

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
    public void setLine(ArrayList<String> text) {
        if(text.size() != 2 || text == null) {
            throw new RuntimeException("Author class set data is null");
        }
        this.name = text.get(0);
        this.birthPlace = text.get(1);
    }

    @Override
    public ArrayList<String>  getLine() {
        ArrayList<String> text = new ArrayList<>();
        text.add(this.name);
        text.add(this.birthPlace);
        return text;
    }
}
