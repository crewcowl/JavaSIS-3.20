package pro.sisit.service;


import pro.sisit.model.Author;
import pro.sisit.model.Book;

/*Не знаю корректо ли так сделать других вариантов не придумал честно говоря, точнее придумал но читать их
* будет страшнее чем книги из тестов. В любом случае буду благодарен за наводку на наиболее правильный вариант*/


public class ObjectParser {

    private final String DELIMITER = ";";

    public String  authorToCSVString(Author author) {
        return author.getName() + DELIMITER + author.getBirthPlace();
    }
    public String  bookToCSVString(Book book) {
        return book.getName() + DELIMITER + book.getAuthor() + DELIMITER + book.getGenre() + DELIMITER + book.getIsbn();
    }

    public void CSVStringToBook (String csvString,Book book) {
        String[] bookData = csvString.split(DELIMITER);
        book.setName(bookData[0]);
        book.setAuthor(bookData[1]);
        book.setGenre(bookData[2]);
        book.setIsbn(bookData[3]);
    }

    public void CSVStringToAuthor (String csvString,Author author) {
        String[] authorData = csvString.split(DELIMITER);
        author.setName(authorData[0]);
        author.setBirthPlace(authorData[1]);
    }

}
