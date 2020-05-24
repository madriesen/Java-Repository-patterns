package fact.it.restaurantapp.repository;

import fact.it.restaurantapp.model.Gerecht;
import fact.it.restaurantapp.model.Personeel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GerechtRepository extends JpaRepository<Gerecht, Long> {
}
