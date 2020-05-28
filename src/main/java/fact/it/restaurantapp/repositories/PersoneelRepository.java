package fact.it.restaurantapp.repositories;

import fact.it.restaurantapp.model.Bestelling;
import fact.it.restaurantapp.model.Personeel;
import fact.it.restaurantapp.model.Tafel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersoneelRepository extends JpaRepository<Personeel, Long> {

    List<Personeel> findByDtype(String dtype);
}
