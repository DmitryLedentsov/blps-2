package blps.labs.repository;

import blps.labs.entity.RejectedUserReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RejectedUserReviewRepository extends JpaRepository<RejectedUserReview, Long> {
    List<RejectedUserReview> findAllByUser_Username(String username);
}
