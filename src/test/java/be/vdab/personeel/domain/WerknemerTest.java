package be.vdab.personeel.domain;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WerknemerTest {

    private Werknemer werknemer1;
    private Werknemer werknemer2;
    private Werknemer nogEenWerknemer2;
    public Werknemer werknemer3;
    private Jobtitle jobtitle1;
    private Jobtitle jobtitle2;

    @BeforeEach
    void setUp() {

        werknemer1 = new Werknemer("surnameNull", "nameNull",
                "name@email.com", null, jobtitle1,  BigDecimal.ONE,
                "zorro", 1234L, LocalDate.of(2000,01,01));
        werknemer2 = new Werknemer("surnameSecond", "nameSecond",
                "name2@email.com", werknemer1,  jobtitle2, BigDecimal.ONE,
                "zorro", 1234L, LocalDate.of(2000,01,01));
        nogEenWerknemer2 = new Werknemer("surnameSecond", "nameSecond",
                "name2@email.com",  werknemer1, jobtitle2, BigDecimal.ONE,
                "zorro", 1234L, LocalDate.of(2000,01,01));
        werknemer3 = new Werknemer("surnameThird", "nameThird",
                "name3@email.com", werknemer1,  jobtitle2, BigDecimal.ONE,
                "zorro", 1234L, LocalDate.of(2000,01,01));
        jobtitle1 = new Jobtitle("testJob");
        jobtitle2 = new Jobtitle("testJob2");
    }

//    @Test
//    void oneChefCanHaveMoreSubordinates() {
//        assertThat(werknemer1.getOndergeschikten()).contains(werknemer2, werknemer3);
//    }

    @Test
    void oneWerknemerHasOnlyOneJobtitle() {
        assertThat(werknemer1.getJobtitle()).isEqualTo(jobtitle1);
    }
}