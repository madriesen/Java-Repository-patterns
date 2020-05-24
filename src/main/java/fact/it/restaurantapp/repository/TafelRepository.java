package fact.it.restaurantapp.repository;

import fact.it.restaurantapp.model.Personeel;
import fact.it.restaurantapp.model.Tafel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TafelRepository extends JpaRepository<Tafel, Long> {
}
