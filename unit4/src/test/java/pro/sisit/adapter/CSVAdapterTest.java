package pro.sisit.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Before
    public void createFile() throws IOException {

        Path bookFilePath = Paths.get("test-book-file.csv");
        Path authorFilePath = Paths.get("test-author-file.csv");

        File bookFile = new File("test-book-file.csv");
        try {
            if (!bookFile.exists()) {
                bookFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("File is not created", e);
        }

        File authorFile = new File("test-author-file.csv");
        try {
            if (!authorFile.exists()) {
                authorFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("File is not created", e);
        }

        try (BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookFilePath.toFile(), true));
             BufferedWriter authorWriter = new BufferedWriter(new FileWriter(authorFilePath.toFile(), true))) {
            bookWriter.write("JoyLand;Stephen King;Horror;978-5-17-118366-0\n");
            authorWriter.write("Stephen King;Portland\n");
        } catch (IOException e) {
            throw new RuntimeException("first writing error", e);
        }
    }

    @After
    public void deleteFile() {
        File authorFile = new File("test-author-file.csv");
        if (authorFile.exists()) {
            assertTrue(authorFile.delete());
        }

        File bookFile = new File("test-book-file.csv");
        if (bookFile.exists()) {
            assertTrue(bookFile.delete());
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

            Book bookAtIndex1 = bookCsvAdapter.read(1);
            assertEquals(bookAtIndex1, book1);
        }

        try (BufferedReader authorReader = new BufferedReader(new FileReader(authorFilePath.toFile()));
             BufferedWriter authorWriter = new BufferedWriter(new FileWriter(authorFilePath.toFile(), true))) {

            IOAdapter<Author> authorCsvAdapter =
                    new CSVAdapter<>(Author.class, authorReader, authorWriter);

            Author AuthorAtIndex1 = authorCsvAdapter.read(1);
            assertEquals(Author1, AuthorAtIndex1);
        }
    }

    @Test
    public void testAppend() throws IOException {
        //book test
        Path bookFilePath = Paths.get("test-book-file.csv");
        Path authorFilePath = Paths.get("test-author-file.csv");

        int index = 0;

        Book book2 = new Book("Call of Cthulhu", "Lovecraft", "Horror", "876-5-91-118366-0");
        Author Author2 = new Author("Lovecraft", "Providence");

        try (BufferedReader bookReader = new BufferedReader(new FileReader(bookFilePath.toFile()));
             BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookFilePath.toFile(), true))) {

            IOAdapter<Book> bookCsvAdapter =
                    new CSVAdapter<>(Book.class, bookReader, bookWriter);

            index = bookCsvAdapter.append(book2);
            Book bookAtIndex1 = bookCsvAdapter.read(index);
            assertEquals(bookAtIndex1, book2);
        }

        //author test
        try (BufferedReader authorReader = new BufferedReader(new FileReader(authorFilePath.toFile()));
             BufferedWriter authorWriter = new BufferedWriter(new FileWriter(authorFilePath.toFile(), true))) {

            IOAdapter<Author> authorCsvAdapter =
                    new CSVAdapter<>(Author.class, authorReader, authorWriter);

            index = authorCsvAdapter.append(Author2);
            Author AuthorAtIndex1 = authorCsvAdapter.read(index);
            assertEquals(Author2, AuthorAtIndex1);
        }
    }
}