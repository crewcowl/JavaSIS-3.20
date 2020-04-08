package pro.sisit.unit9.data;

import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Buyer;
import pro.sisit.unit9.entity.BuyingBook;

import java.util.List;

public interface BuyingBookRepository extends CrudRepository<BuyingBook, Long> {

    List<BuyingBook> findByBook(Book book);
    List<BuyingBook> findByBuyer(Buyer buyer);
}
