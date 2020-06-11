package be.vdab.personeel.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "jobtitels")
public class Jobtitle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String naam;

    @OneToMany(mappedBy = "jobtitle", cascade = CascadeType.REMOVE)
    @OrderBy("voornaam, familienaam")
    private Set<Werknemer> werknemers = new LinkedHashSet<>();

    @Version
    private Long versie;

    protected Jobtitle() {
    }

    public Jobtitle(String naam) {
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
        Jobtitle oudeJobtitle = werknemer.getJobtitle();
        if (oudeJobtitle !=null && oudeJobtitle != this){
            oudeJobtitle.werknemers.remove(werknemer);
        }
        if (this != oudeJobtitle){
            werknemer.setJobtitle(this);
        }
        return toegevoegd;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jobtitle)) return false;

        Jobtitle jobtitle = (Jobtitle) o;

        return naam.toLowerCase().equals(jobtitle.naam.toLowerCase());
    }

    @Override
    public int hashCode() {
        return naam.toLowerCase().hashCode();
    }
}
