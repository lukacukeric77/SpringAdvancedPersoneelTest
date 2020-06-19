package be.vdab.personeel.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "werknemers")
@NamedEntityGraph(name = "Werknemer.withChef", attributeNodes = @NamedAttributeNode("chef"))
public class Werknemer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String familienaam;
    private String voornaam;
    private String email;
    private Long rijksregisternr;

    //jobtitel relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jobtitelid")
    private Jobtitel jobtitel;

    //self relation fields
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chefid", referencedColumnName = "id")
    private Werknemer chef;

    @OneToMany(mappedBy = "chef", fetch = FetchType.LAZY)
    @OrderBy("voornaam, familienaam")
    private final Set<Werknemer> ondergeschikten = new LinkedHashSet<>();

    @NumberFormat(pattern = "#,##0.00")
    private BigDecimal salaris;
    private String paswoord;
    @DateTimeFormat(style = "S-", pattern = "d-M-yy")
    private LocalDate geboorte;
    @Version
    private Long versie;

    protected Werknemer() {
    }

    public Werknemer(String familienaam, String voornaam,
                     String email, Long rijksregisternr, Jobtitel jobtitel, BigDecimal salaris, String paswoord, Werknemer chef,
                     LocalDate geboorte) {
        this.familienaam = familienaam;
        this.voornaam = voornaam;
        this.email = email;
        this.rijksregisternr = rijksregisternr;
        setJobtitel(jobtitel);
        setChef(chef);
        this.salaris = salaris;
        this.paswoord = paswoord;
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

    public Long getRijksregisternr() {
        return rijksregisternr;
    }

    public void setRijksregisternr(Long rijksregisternr) {
        if (rijksregisternr != null){
        this.rijksregisternr = rijksregisternr;}
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

    public String getPaswoord() {
        return paswoord;
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
        if (chef != null && !chef.getOndergeschikten().contains(this)) {
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

        return rijksregisternr.equals(werknemer.rijksregisternr);
    }

    @Override
    public int hashCode() {
        return rijksregisternr.hashCode();
    }

    public void opslag(BigDecimal ammount) {
        if (ammount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal factor = BigDecimal.ONE.add(ammount.divide(BigDecimal.valueOf(100)));
        salaris = salaris.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }
}
