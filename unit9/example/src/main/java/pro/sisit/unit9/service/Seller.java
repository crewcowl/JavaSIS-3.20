package pro.sisit.unit9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sisit.unit9.data.BuyingBookRepository;
import pro.sisit.unit9.entity.Book;
import pro.sisit.unit9.entity.Buyer;
import pro.sisit.unit9.entity.BuyingBook;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class Seller {

    private final BuyingBookRepository buyingBookRepository;

    public void sellBook(Book book, Buyer buyer, BigDecimal cost){
        BuyingBook buyingBook = new BuyingBook();
        buyingBook.setBook(book);
        buyingBook.setBuyer(buyer);
        buyingBook.setCost(cost);
        buyingBookRepository.save(buyingBook);

    }

    public BigDecimal bookTotalCost (Book book) {
        return buyingBookRepository.findByBook(book).stream()
                .map(BuyingBook::getCost)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public BigDecimal buyerTotalCost (Buyer buyer) {
        return buyingBookRepository.findByBuyer(buyer).stream()
                .map(BuyingBook::getCost)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

}
