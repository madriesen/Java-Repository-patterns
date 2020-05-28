package fact.it.restaurantapp.repositories;

import fact.it.restaurantapp.model.Tafel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TafelRepository extends JpaRepository<Tafel, Long> {
}
