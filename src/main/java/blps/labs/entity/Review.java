package blps.labs.entity;

import blps.labs.dto.ReviewDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @NotEmpty
    private String reviewText;

    @NotEmpty
    private String pros;

    @NotEmpty
    private String cons;

    @NotEmpty
    private String advice;

    @NotNull
    private boolean approved = false;

    public Review(ReviewDTO reviewDTO) {
        this.authorName = reviewDTO.getAuthorName();
        this.reviewText = reviewDTO.getReviewText();
        this.pros = reviewDTO.getPros();
        this.cons = reviewDTO.getCons();
        this.advice = reviewDTO.getAdvice();
        this.approved = reviewDTO.isApproved();
    }

}
