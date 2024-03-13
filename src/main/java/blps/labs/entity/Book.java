package blps.labs.entity;

import blps.labs.dto.ReviewDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity(name = "book")
@NoArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    private String bookName;


    @NotNull
    @DateTimeFormat
    private Date manufactureYear;

    @NotNull
    @DateTimeFormat
    private Date ownershipDate;


    public Book(ReviewDTO reviewDTO) {
        this.bookName = reviewDTO.getBookName();
    
        this.manufactureYear = reviewDTO.getManufactureYear();
        this.ownershipDate = reviewDTO.getOwnershipDate();
    }
}
