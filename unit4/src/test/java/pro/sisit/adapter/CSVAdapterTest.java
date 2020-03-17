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

    @Before
    public void createFile() {

        File bookFile = new File("test-book-file.csv");
        try {
            if(!bookFile.exists()) bookFile.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File authorFile = new File("test-author-file.csv");
        try {
            if(!authorFile.exists()) authorFile.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteFile() {
        File authorFile = new File("test-author-file.csv");
        if(authorFile.exists()) authorFile.delete();

        File bookFile = new File("test-book-file.csv");
        if(bookFile.exists()) bookFile.delete();

    }

    @Test
    public void testMethods() throws IOException {
        int index = 0;
        //book test
        Path bookFilePath = Paths.get("test-book-file.csv");

        BufferedReader bookReader = new BufferedReader(
                new FileReader(bookFilePath.toFile()));

        BufferedWriter bookWriter = new BufferedWriter(
                new FileWriter(bookFilePath.toFile(), true));

        IOAdapter<Book> bookCsvAdapter =
                new CSVAdapter<>(Book.class, bookReader, bookWriter);

        Book book1 = new Book("JoyLand", "Stephen King","Horror", "978-5-17-118366-0");
        Book book2 = new Book("Stephen King", "Lovecraft","Horror", "876-5-91-118366-0");

        bookReader.mark((int) (bookFilePath.toFile().length()+book1.setCSVLine().length()+1));
        index = bookCsvAdapter.append(book1);
        bookReader.reset();

        bookReader.mark((int) (bookFilePath.toFile().length()+book2.setCSVLine().length()+1));
        index = bookCsvAdapter.append(book2);
        bookReader.reset();


        bookReader.mark((int)bookFilePath.toFile().length());
        Book bookAtIndex1 = bookCsvAdapter.read(1);
        bookReader.reset();
        if (bookAtIndex1.equals(book1)) System.out.print("checked\n");

        Book bookAtIndex2 = bookCsvAdapter.read(index);
        bookReader.reset();
        if (bookAtIndex2.equals(book2)) System.out.print("checked\n");

        bookAtIndex2 = bookCsvAdapter.read(1);
        bookReader.reset();
        if (bookAtIndex2.equals(book1)) System.out.print("checked\n");

        bookWriter.close();
        bookReader.close();

        //author test
        Path authorFilePath = Paths.get("test-author-file.csv");

        BufferedReader authorReader = new BufferedReader(
                new FileReader(authorFilePath.toFile()));

        BufferedWriter authorWriter = new BufferedWriter(
                new FileWriter(authorFilePath.toFile(), true));

        IOAdapter<Author> authorCsvAdapter =
                new CSVAdapter<>(Author.class, authorReader, authorWriter);

        Author Author1 = new Author("Stephen King","Portland");
        Author Author2 = new Author ("Lovecraft","Providence");

        authorReader.mark((int) (authorFilePath.toFile().length()+Author1.setCSVLine().length()+1));
        index = authorCsvAdapter.append(Author1);
        authorReader.reset();

        authorReader.mark((int) (authorFilePath.toFile().length()+Author2.setCSVLine().length()+1));
        index = authorCsvAdapter.append(Author2);
        authorReader.reset();


        authorReader.mark((int)authorFilePath.toFile().length());
        Author AuthorAtIndex1 = authorCsvAdapter.read(1);
        authorReader.reset();
        if (AuthorAtIndex1.equals(Author1)) System.out.print("checked\n");

        Author AuthorAtIndex2 = authorCsvAdapter.read(index);
        authorReader.reset();
        if (AuthorAtIndex2.equals(Author2)) System.out.print("checked\n");

        AuthorAtIndex2 = authorCsvAdapter.read(1);
        authorReader.reset();
        if (AuthorAtIndex2.equals(Author1)) System.out.print("checked\n");

        authorWriter.close();
        authorReader.close();
    }
}
