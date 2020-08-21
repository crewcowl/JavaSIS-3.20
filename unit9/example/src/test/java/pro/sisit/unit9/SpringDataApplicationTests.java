package pro.sisit.unit9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import pro.sisit.unit9.data.*;
import pro.sisit.unit9.entity.*;
import pro.sisit.unit9.service.Seller;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private AuthorOfBookRepository authorOfBookRepository;

	@Autowired
	private BuyerRepository buyerRepository;

	@Autowired
	private BuyingBookRepository buyingBookRepository;

	@Before
	public void init() {
		Book book = new Book();
		book.setDescription("Увлекательные приключения Тома Сойера");
		book.setTitle("Приключения Тома Сойера");
		book.setYear(1876);
		bookRepository.save(book);

		Book book2 = new Book();
		book2.setTitle("Михаил Строгов");
		book2.setYear(1876);
		bookRepository.save(book2);

		Book book3 = new Book();
		book3.setTitle("Приключения Гекльберри Финна");
		book3.setYear(1884);
		bookRepository.save(book3);

		Author author = new Author();
		author.setFirstname("Марк");
		author.setLastname("Твен");
		authorRepository.save(author);

		AuthorOfBook authorOfBook = new AuthorOfBook();
		authorOfBook.setAuthor(author);
		authorOfBook.setBook(book);
		authorOfBookRepository.save(authorOfBook);

		AuthorOfBook authorOfBook2 = new AuthorOfBook();
		authorOfBook2.setAuthor(author);
		authorOfBook2.setBook(book3);
		authorOfBookRepository.save(authorOfBook2);

		Author author2 = new Author();
		author2.setFirstname("Жюль");
		author2.setLastname("Верн");
		authorRepository.save(author2);

		AuthorOfBook authorOfBook3 = new AuthorOfBook();
		authorOfBook3.setAuthor(author2);
		authorOfBook3.setBook(book2);
		authorOfBookRepository.save(authorOfBook3);

		Book book4 = new Book();
		book4.setTitle("Буратино");
		book4.setYear(1936);
		bookRepository.save(book4);

		Author author3 = new Author();
		author3.setFirstname("Алексей");
		author3.setLastname("Толстой");
		authorRepository.save(author3);

		AuthorOfBook authorOfBook4 = new AuthorOfBook();
		authorOfBook4.setAuthor(author3);
		authorOfBook4.setBook(book4);
		authorOfBookRepository.save(authorOfBook4);

	}

	@After
	public void DeleteData() {
		authorOfBookRepository.deleteAll();
		authorRepository.deleteAll();
		buyingBookRepository.deleteAll();
		buyerRepository.deleteAll();
		bookRepository.deleteAll();
	}

	@Test
	public void testBuyerSave() {
		Buyer buyer = new Buyer();
		buyer.setName("Иван");
		buyer.setAddress("Дудинская 2");
		buyerRepository.save(buyer);

		boolean founded = false;
		for (Buyer buyers : buyerRepository.findAll()) {
			if (buyers.getAddress().equals("Дудинская 2")
					&& buyers.getId() > 0) {
				founded = true;
				break;
			}
		}
		assertTrue(founded);
	}

	@Test
	@Transactional
	public void testSell() {
		Buyer buyer = new Buyer();
		buyer.setName("Иван");
		buyer.setAddress("Дудинская 2");
		buyerRepository.save(buyer);

		Book book = new Book();
		book.setTitle("Джанки");
		book.setYear(1953);
		bookRepository.save(book);

		Seller seller = new Seller(buyingBookRepository);
		seller.sellBook(book,buyer, BigDecimal.valueOf(399.99));

		boolean founded = false;
		for (BuyingBook iteratedBook : buyingBookRepository.findAll()) {
			if (iteratedBook.getBook().getTitle().equals("Джанки")
					&& iteratedBook.getId() > 0) {
				founded = true;
				break;
			}
		}
		assertTrue(founded);
	}

	@Test
	@Transactional
	public void testBookTotalCost() {
		Buyer buyer = new Buyer();
		buyer.setName("Иван");
		buyer.setAddress("Дудинская 2");
		buyerRepository.save(buyer);

		Buyer buyer2 = new Buyer();
		buyer.setName("Чувак без скидона");
		buyerRepository.save(buyer);

		Book book = new Book();
		book.setTitle("Джанки");
		book.setYear(1953);
		bookRepository.save(book);

		Seller seller = new Seller(buyingBookRepository);
		seller.sellBook(book,buyer, BigDecimal.valueOf(250));
		seller.sellBook(book,buyer2, BigDecimal.valueOf(300));

		assertEquals(seller.bookTotalCost(book),BigDecimal.valueOf(550));
	}

	@Test
	@Transactional
	public void testBuyerTotalCost() {
		Buyer buyer = new Buyer();
		buyer.setName("Иван");
		buyer.setAddress("Дудинская 2");
		buyerRepository.save(buyer);

		Book book = new Book();
		book.setTitle("Джанки");
		book.setYear(1953);
		bookRepository.save(book);

		Book book1 = new Book();
		book.setTitle("Удушье");
		book.setYear(2001);
		bookRepository.save(book1);

		Book book2 = new Book();
		book.setTitle("Сто лет одиночества");
		book.setYear(1967);
		bookRepository.save(book2);

		Seller seller = new Seller(buyingBookRepository);
		seller.sellBook(book,buyer, BigDecimal.valueOf(250));
		seller.sellBook(book1,buyer, BigDecimal.valueOf(250));
		seller.sellBook(book2,buyer,BigDecimal.valueOf(500));

		assertEquals(seller.buyerTotalCost(buyer),BigDecimal.valueOf(1000));

	}

	@Test
	public void testSave() {
		boolean founded = false;
		for (Book iteratedBook : bookRepository.findAll()) {
			if (iteratedBook.getTitle().equals("Буратино")
					&& iteratedBook.getId() > 0) {
				founded = true;
				break;
			}
		}
		assertTrue(founded);
	}

	@Test
	public void testFindByYear() {
		assertEquals(2, bookRepository.findByYear(1876).size());
		assertEquals(1, bookRepository.findByYear(1884).size());
		assertEquals(0, bookRepository.findByYear(2000).size());
	}

	@Test
	public void testFindAtPage() {
		PageRequest pageRequest = PageRequest.of(1, 1, Sort.Direction.ASC, "title");
		assertTrue(bookRepository.findAll(pageRequest)
				.get().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	}

	@Test
	public void testFindSame() {
		Book book = new Book();
		book.setYear(1876);

		assertEquals(2, bookRepository.findAll(Example.of(book)).size());
	}

	@Test
	public void testFindInRange() {
		assertEquals(3, bookRepository.findAll(
				BookSpecifications.byYearRange(1800, 1900)).size());
		assertEquals(0, bookRepository.findAll(
				BookSpecifications.byYearRange(2000, 2010)).size());
	}

	@Test
	public void testFindByAuthorLastname() {
		assertTrue(bookRepository.findByAuthor("Верн")
				.stream().allMatch(book -> book.getTitle().equals("Михаил Строгов")));
	}

	@Test
	public void testComplexQueryMethod() {
		assertEquals(4, bookRepository.complexQueryMethod().size());
	}

}
