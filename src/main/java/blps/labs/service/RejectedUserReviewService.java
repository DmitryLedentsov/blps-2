package blps.labs.service;

import blps.labs.entity.RejectedUserReview;
import blps.labs.repository.RejectedUserReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RejectedUserReviewService {
    private final RejectedUserReviewRepository rejectedUserReviewRepository;

    @Autowired
    public RejectedUserReviewService(RejectedUserReviewRepository rejectedUserReviewRepository) {
        this.rejectedUserReviewRepository = rejectedUserReviewRepository;
    }

    public void save(RejectedUserReview review) {
        rejectedUserReviewRepository.save(review);
    }

    public List<RejectedUserReview> findAllByUsername(String username) {
        return rejectedUserReviewRepository.findAllByUser_Username(username);
    }
}
