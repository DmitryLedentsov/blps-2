package blps.labs.service;

import blps.labs.entity.Review;
import blps.labs.exception.DataNotFoundException;
import blps.labs.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
        //log.debug("{} review by {} on saved in DB", review.getCarModel(), review.getAuthorName());
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Review not found"));
    }

    public List<Review> findAllByApproved(boolean approved) {
        return reviewRepository.findAllByApproved(approved);
    }


    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> findAllByAuthorName(String authorName) {
        return reviewRepository.findAllByAuthorName(authorName);
    }
}
