package pro.sisit.adapter;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pro.sisit.adapter.impl.CSVAdapter;
import pro.sisit.model.Book;
import pro.sisit.model.Author;

public class CSVAdapterTest {

    private int bookIndex = 0;
    private int authorIndex = 0;

    @Before
    public void createFile() {

        File bookFile = new File("test-book-file.csv");
        try {
            if (!bookFile.exists()) {
                bookFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("File is not created",e);
        }

        File authorFile = new File("test-author-file.csv");
        try {
            if (!authorFile.exists()) {
                authorFile.createNewFile();
            }

        } catch (IOException e) {
            throw new RuntimeException("File is not created",e);
        }
    }

    @After
    public void deleteFile() {
        File authorFile = new File("test-author-file.csv");
        if (authorFile.exists()) {
            if(!authorFile.delete()) {
                throw new RuntimeException("File is not deleted");
            }
        }

        File bookFile = new File("test-book-file.csv");
        if (bookFile.exists()) {
            if(!bookFile.delete()) {
                throw new RuntimeException("File is not deleted");
            }
        }
    }

    @Test
    public void testAppend() throws IOException {
        //book test
        Path bookFilePath = Paths.get("test-book-file.csv");
        Path authorFilePath = Paths.get("test-author-file.csv");

        Book book1 = new Book("JoyLand", "Stephen King", "Horror", "978-5-17-118366-0");
        Book book2 = new Book("Stephen King", "Lovecraft", "Horror", "876-5-91-118366-0");

        Author Author1 = new Author("Stephen King", "Portland");
        Author Author2 = new Author("Lovecraft", "Providence");

        try (BufferedReader bookReader = new BufferedReader(new FileReader(bookFilePath.toFile()));
             BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookFilePath.toFile(), true))) {

            IOAdapter<Book> bookCsvAdapter =
                    new CSVAdapter<>(Book.class, bookReader, bookWriter);

            bookIndex = bookCsvAdapter.append(book1);
            bookIndex = bookCsvAdapter.append(book2);
        }

        //author test
        try (BufferedReader authorReader = new BufferedReader(new FileReader(authorFilePath.toFile()));
                BufferedWriter authorWriter = new BufferedWriter(new FileWriter(authorFilePath.toFile(), true))) {

            IOAdapter<Author> authorCsvAdapter =
                    new CSVAdapter<>(Author.class, authorReader, authorWriter);

            authorIndex = authorCsvAdapter.append(Author1);
            authorIndex = authorCsvAdapter.append(Author2);
        }
    }


    @Test
    public void testRead() throws IOException {
        Path bookFilePath = Paths.get("test-book-file.csv");
        Path authorFilePath = Paths.get("test-author-file.csv");

        Book book1 = new Book("JoyLand", "Stephen King", "Horror", "978-5-17-118366-0");
        Author Author1 = new Author("Stephen King", "Portland");

        try (BufferedReader bookReader = new BufferedReader(new FileReader(bookFilePath.toFile()));
             BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookFilePath.toFile(), true))) {

            IOAdapter<Book> bookCsvAdapter =
                    new CSVAdapter<>(Book.class, bookReader, bookWriter);

            bookIndex = bookCsvAdapter.append(book1);
            Book bookAtIndex1 = bookCsvAdapter.read(bookIndex);
            assertEquals(bookAtIndex1,book1);
        }

        try (BufferedReader authorReader = new BufferedReader(new FileReader(authorFilePath.toFile()));
             BufferedWriter authorWriter = new BufferedWriter(new FileWriter(authorFilePath.toFile(), true))) {

            IOAdapter<Author> authorCsvAdapter =
                    new CSVAdapter<>(Author.class, authorReader, authorWriter);

            authorIndex = authorCsvAdapter.append(Author1);
            Author AuthorAtIndex1 = authorCsvAdapter.read(authorIndex);
            assertEquals(Author1,AuthorAtIndex1);
        }
    }
}