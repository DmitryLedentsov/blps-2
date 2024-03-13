package blps.labs.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {
    private long id;
    private String authorName;
    private String bookName;
    private Date manufactureYear;
    private String reviewText;
    private String pros;
    private String cons;
    private String advice;
    private Date ownershipDate;
    private boolean approved = false;
}
