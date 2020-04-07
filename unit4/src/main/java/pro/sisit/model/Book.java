package pro.sisit.model;

import pro.sisit.adapter.Serializable;

import java.util.ArrayList;
import java.util.Objects;

public class Book implements Serializable {

    private String name;
    private String author;
    private String genre;
    private String isbn;

    public Book () {
        this.name = null;
        this.author = null;
        this.genre = null;
        this.isbn = null;
    }

    public Book(String name, String author, String genre, String isbn) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() { return isbn; }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return getName().equals(book.getName()) &&
            getAuthor().equals(book.getAuthor()) &&
            getGenre().equals(book.getGenre()) &&
            getIsbn().equals(book.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getGenre(), getIsbn());
    }


    @Override
    public void setLine(ArrayList<String> text) {
        if(text.size() != 4 || text == null) {
            throw new RuntimeException("Book class set data is null");
        }
        this.name = text.get(0);
        this.author = text.get(1);
        this.genre = text.get(2);
        this.isbn = text.get(3);
    }

    @Override
    public ArrayList<String> getLine() {
        ArrayList<String> text = new ArrayList<>();
        text.add(this.name);
        text.add(this.author);
        text.add(this.genre);
        text.add(this.isbn);
        return text;
    }
}
