package fact.it.restaurantapp.repositories;

import fact.it.restaurantapp.model.Bestelling;
import fact.it.restaurantapp.model.Tafel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BestellingRepository extends JpaRepository<Bestelling, Long> {

    // werkt niet om een of andere vage reden
//    @Query("select b from Bestelling b  where function('date', b.datum) = function('date', :gegevenDatum) ")
//    List<Bestelling> findByDatum(GregorianCalendar gegevenDatum);

    List<Bestelling> findByTafel(Tafel tafel);
}
