package fact.it.restaurantapp.model;

import fact.it.restaurantapp.observer.Observer;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
//@MappedSuperclass
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Personeel implements Observer {

    protected String naam;
    @Column(insertable = false, updatable = false)
    private String dtype;

    public long getPersoneelId() {
        return personeelId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long personeelId;

    protected Personeel() {
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public abstract void update();

    public String getDtype() {
        return dtype;
    }
}
