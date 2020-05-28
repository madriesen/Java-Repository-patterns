package fact.it.restaurantapp.repositories;

import fact.it.restaurantapp.model.BesteldItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BesteldItemRepository extends JpaRepository<BesteldItem, Long> {
}
