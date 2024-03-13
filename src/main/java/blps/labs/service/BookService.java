package blps.labs.service;

import blps.labs.entity.Book;
import blps.labs.exception.DataNotFoundException;
import blps.labs.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findBook(Book book) {
        return bookRepository.findByBookNameAndAndManufactureYearAndOwnershipDate(book.getBookName(), book.getManufactureYear(), book.getOwnershipDate()).orElseThrow(() -> new DataNotFoundException("Car not found"));
    }
}
