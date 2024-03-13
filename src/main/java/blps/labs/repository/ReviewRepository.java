package blps.labs.repository;

import blps.labs.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByApproved(boolean approved);
    List<Review> findAllByAuthorName(String authorName);
}
