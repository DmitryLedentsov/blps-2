package blps.labs.controller;

import blps.labs.dto.ReviewDTO;
import blps.labs.entity.Review;
import blps.labs.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/")
    public ResponseEntity<String> addReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = new Review(reviewDTO);
        reviewService.saveReview(review);
        return new ResponseEntity<>("Отзыв сохранен", HttpStatus.OK);
    }

    @PatchMapping("/approval/{id}")
    public ResponseEntity<String> changeApproval(@PathVariable(name = "id") Long id,
                                                 @RequestBody Map<String, Boolean> payload) {
        Boolean approved = payload.get("approved");
        if (approved == null)
            return new ResponseEntity<>("Не указано значение approved", HttpStatus.BAD_REQUEST);
        Review review = reviewService.findReviewById(id);
        if (review == null)
            return new ResponseEntity<>("Отзыв с указанным id не существует", HttpStatus.BAD_REQUEST);
        if (review.isApproved() == approved)
            return new ResponseEntity<>("Отзыв с указанным id уже имеет значение " + approved, HttpStatus.BAD_REQUEST);
        review.setApproved(approved);
        reviewService.saveReview(review);
        return new ResponseEntity<>("Подтверждение отзыва изменено", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(name = "id") Long id) {
        reviewService.deleteReviewById(id);
        return new ResponseEntity<>("Отзыв удален", HttpStatus.OK);
    }

    @GetMapping("/approved/{approved}")
    public ResponseEntity<List<Review>> getReviewByApproval(@PathVariable Boolean approved) {
        List<Review> reviews = reviewService.findAllByApproved(approved);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/authorName/{authorName}")
    public ResponseEntity<List<Review>> getReviewByAuthorName(@PathVariable String authorName) {
        List<Review> reviews = reviewService.findAllByAuthorName(authorName);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
