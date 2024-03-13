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
@Entity(name = "review")
@NoArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    private String authorName;

    @NotEmpty
    private String bookName;


    @NotNull
    @DateTimeFormat
    private Date manufactureYear;

    @NotEmpty
    private String reviewText;

    @NotEmpty
    private String pros;

    @NotEmpty
    private String cons;

    @NotEmpty
    private String advice;

    @NotNull
    @DateTimeFormat
    private Date ownershipDate;


    @NotNull
    private boolean approved = false;

    public Review(ReviewDTO reviewDTO) {
        this.authorName = reviewDTO.getAuthorName();
        this.bookName = reviewDTO.getBookName();
        this.manufactureYear = reviewDTO.getManufactureYear();
        this.reviewText = reviewDTO.getReviewText();
        this.pros = reviewDTO.getPros();
        this.cons = reviewDTO.getCons();
        this.advice = reviewDTO.getAdvice();
        this.ownershipDate = reviewDTO.getOwnershipDate();

        this.approved = reviewDTO.isApproved();
    }

}
