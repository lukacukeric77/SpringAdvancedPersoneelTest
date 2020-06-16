package be.vdab.personeel.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

class WerknemerTest {

    private Werknemer werknemer1;
    private Werknemer werknemer2;
    private Werknemer nogEenWerknemer2;
    public Werknemer werknemer3;
    private Jobtitel jobtitel1;
    private Jobtitel jobtitel2;

    @BeforeEach
    void setUp() {
        jobtitel1 = new Jobtitel("testJob");
        jobtitel2 = new Jobtitel("testJob2");
        werknemer1 = new Werknemer("surnameNull", "nameNull",
                "name@email.com", 2345L, jobtitel1, BigDecimal.valueOf(100), "zorro", null,
                LocalDate.of(2000, 01, 01));
        werknemer2 = new Werknemer("surnameSecond", "nameSecond",
                "name2@email.com", 1234L, jobtitel2, BigDecimal.ONE, "zorro", werknemer1,
                LocalDate.of(2000, 01, 01));
        nogEenWerknemer2 = new Werknemer("surnameSecond", "nameSecond",
                "name2@email.com", 1234L, jobtitel2, BigDecimal.ONE, "zorro", werknemer1,
                LocalDate.of(2000, 01, 01));
        werknemer3 = new Werknemer("surnameThird", "nameThird",
                "name3@email.com", 5555L, jobtitel2, BigDecimal.ONE, "zorro", werknemer1,
                LocalDate.of(2000, 01, 01));
    }

    @Test
    void oneChefCanHaveMoreSubordinates() {
        assertThat(werknemer1.getOndergeschikten()).contains(werknemer2, werknemer3);
    }

    @Test
    void oneSubordinateCanHaveOnlyOneBoss() {
        assertThat(werknemer2.getChef()).isEqualTo(werknemer1);
    }

    @Test
    void oneWerknemerHasOnlyOneJobtitle() {
        assertThat(werknemer1.getJobtitel()).isEqualTo(jobtitel1);
    }

    @Test
    void werknemersAreAlikeIfTheirHashcodeIsAlike() {
        assertThat(werknemer2).isEqualTo(nogEenWerknemer2);
    }

    @Test
    void werknemersAreNotAlikeIfTheirHashcodeDoNotMatch() {
        assertThat(werknemer1).isNotEqualTo(werknemer2);
    }

    @Test
    void sameWerknemersHaveSameHashcode() {
        assertThat(werknemer2).hasSameHashCodeAs(nogEenWerknemer2);
    }

    @Test
    void nullInSetterOfJobtitelThrowsNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> werknemer1.setJobtitel(null));
    }

    @Test
    void nullInSetterOfChefDoesNotThrowNullPointerException(){
        werknemer2.setChef(null);
        assertThat(werknemer2.getChef()).isNull();
    }

    //opslag

    @Test
    void opslag() {
        werknemer1.opslag(BigDecimal.TEN);
        assertThat(werknemer1.getSalaris()).isEqualByComparingTo("110");
    }

    @Test
    void opslagWithNullThrows(){
        assertThatNullPointerException().isThrownBy(()-> werknemer1.opslag(null));
    }

    @Test
    void opslagWith0Throws(){
        assertThatIllegalArgumentException().isThrownBy(() -> werknemer1.opslag(BigDecimal.ZERO));
    }

    @Test
    void negativeOpslagThrows(){
        assertThatIllegalArgumentException().isThrownBy(()-> werknemer1.opslag(BigDecimal.valueOf(-1)));
    }

}