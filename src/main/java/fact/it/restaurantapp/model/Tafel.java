package fact.it.restaurantapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tafel {

    public Long getTafelId() {
        return tafelId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tafelId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;
}
