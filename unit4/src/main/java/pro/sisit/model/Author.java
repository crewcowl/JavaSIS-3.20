package pro.sisit.model;

import pro.sisit.adapter.CSVImpl;

import java.util.Objects;

public class Author implements CSVImpl {

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
    public void getCSVLine(String text) {
        String[] newAuthor = text.split(", ");
        this.name = newAuthor[0];
        this.birthPlace = newAuthor[1];
    }

    @Override
    public String setCSVLine() {
        return name + ", " + birthPlace + "\n";
    }
}
