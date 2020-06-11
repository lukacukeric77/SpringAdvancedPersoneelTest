package be.vdab.personeel.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "jobtitels")
public class Jobtitel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String naam;

    @OneToMany(mappedBy = "jobtitel", cascade = CascadeType.REMOVE)
    @OrderBy("voornaam, familienaam")
    private Set<Werknemer> werknemers = new LinkedHashSet<>();

    @Version
    private Long versie;

    protected Jobtitel() {
    }

    public Jobtitel(String naam) {
        this.naam = naam;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Set<Werknemer> getWerknemers() {
        return Collections.unmodifiableSet(werknemers);
    }

    public Long getVersie() {
        return versie;
    }

    public void setVersie(Long versie) {
        this.versie = versie;
    }

    //association with werknemers
    public boolean add(Werknemer werknemer){
        boolean toegevoegd = werknemers.add(werknemer);
        Jobtitel oudeJobtitel = werknemer.getJobtitel();
        if (oudeJobtitel !=null && oudeJobtitel != this){
            oudeJobtitel.werknemers.remove(werknemer);
        }
        if (this != oudeJobtitel){
            werknemer.setJobtitel(this);
        }
        return toegevoegd;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jobtitel)) return false;

        Jobtitel jobtitel = (Jobtitel) o;

        return naam.toLowerCase().equals(jobtitel.naam.toLowerCase());
    }

    @Override
    public int hashCode() {
        return naam.toLowerCase().hashCode();
    }
}
