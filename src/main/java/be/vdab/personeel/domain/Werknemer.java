package be.vdab.personeel.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "werknemers")
public class Werknemer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String familienaam;
    private String voornaam;
    private String email;

    //self relation fields
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chefid", referencedColumnName = "id")
    private Werknemer chef;

    @OneToMany(mappedBy = "chef", fetch = FetchType.LAZY)
    @OrderBy("voornaam, familienaam")
    private Set<Werknemer> ondergeschikten;

    //jobtitel relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jobtitelid")
    private Jobtitel jobtitel;

    private BigDecimal salaris;
    private String password;
    private LocalDate geboorte;
    private Long rijksregisternr;
    @Version
    private Long versie;

    protected Werknemer() {
    }

    public Werknemer(String familienaam, String voornaam,
                     String email, Werknemer chef, Jobtitel jobtitel, BigDecimal salaris,
                     String password, Long rijksregisternr, LocalDate geboorte) {
        this.familienaam = familienaam;
        this.voornaam = voornaam;
        this.email = email;
        setJobtitel(jobtitel);
        setChef(chef);
        this.ondergeschikten = new LinkedHashSet<>();
        this.salaris = salaris;
        this.password = password;
        this.rijksregisternr = rijksregisternr;
        this.geboorte = geboorte;
    }

    public long getId() {
        return id;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getEmail() {
        return email;
    }

    public Jobtitel getJobtitel() {
        return jobtitel;
    }

    public Werknemer getChef() {
        return chef;
    }

    public Set<Werknemer> getOndergeschikten() {
        return ondergeschikten;
    }

    public BigDecimal getSalaris() {
        return salaris;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getGeboorte() {
        return geboorte;
    }

    public Long getVersie() {
        return versie;
    }

    public void setVersie(Long versie) {
        this.versie = versie;
    }

    // association with Jobtitel
    public void setJobtitel(Jobtitel jobtitel) {
        if (!jobtitel.getWerknemers().contains(this)) {
            jobtitel.add(this);
        }
        this.jobtitel = jobtitel;
    }

    // association with self - chef-ondergeschikten


    public void setChef(Werknemer chef) {
        if (!chef.getOndergeschikten().contains(this)) {
            chef.add(this);
        }
        this.chef = chef;
    }

    public boolean add(Werknemer onderschiekte) {
        boolean toegevoegd = ondergeschikten.add(onderschiekte);
        Werknemer oudeChef = onderschiekte.getChef();
        if (oudeChef != null & oudeChef != this) {
            oudeChef.ondergeschikten.remove(onderschiekte);
        }
        if (this != oudeChef) {
            onderschiekte.setChef(this);
        }
        return toegevoegd;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Werknemer)) return false;

        Werknemer werknemer = (Werknemer) o;

        return email.toLowerCase().equals(werknemer.email.toLowerCase());
    }

    @Override
    public int hashCode() {
        return email.toLowerCase().hashCode();
    }
}
