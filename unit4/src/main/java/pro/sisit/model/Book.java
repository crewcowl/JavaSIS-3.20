package pro.sisit.model;

import pro.sisit.adapter.CSVConverter;

import java.util.Objects;

public class Book implements CSVConverter {

    private String name;
    private String author;
    private String genre;
    private String isbn;

    private final String DELIMITER = ";";

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

    public String getIsbn() {
        return isbn;
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
    public void setCSVLine(String text) {
        String[] newBook = text.split(DELIMITER);
        this.name = newBook[0];
        this.author = newBook[1];
        this.genre = newBook[2];
        this.isbn = newBook[3];
    }

    @Override
    public String getCSVLine() {
        return name + DELIMITER + author + DELIMITER + genre + DELIMITER + isbn;
    }
}
