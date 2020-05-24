package fact.it.restaurantapp.repository;

import fact.it.restaurantapp.model.Bestelling;
import fact.it.restaurantapp.model.Personeel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BestellingRepository extends JpaRepository<Bestelling, Long> {
}
