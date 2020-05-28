package fact.it.restaurantapp.repositories;

import fact.it.restaurantapp.model.Gerecht;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GerechtRepository extends JpaRepository<Gerecht, Long> {
}
