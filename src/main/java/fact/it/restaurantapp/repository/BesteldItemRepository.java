package fact.it.restaurantapp.repository;

import fact.it.restaurantapp.model.BesteldItem;
import fact.it.restaurantapp.model.Bestelling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BesteldItemRepository extends JpaRepository<BesteldItem, Long> {
}
