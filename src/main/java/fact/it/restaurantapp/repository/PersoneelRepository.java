package fact.it.restaurantapp.repository;

import fact.it.restaurantapp.model.Personeel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersoneelRepository extends JpaRepository<Personeel, Long> {
}
