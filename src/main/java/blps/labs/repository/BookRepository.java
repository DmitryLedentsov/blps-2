package blps.labs.repository;

import blps.labs.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookNameAndAndManufactureYearAndOwnershipDate(@NotEmpty String bookName, @NotNull Date manufactureYear, @NotNull Date ownershipDate);
}
