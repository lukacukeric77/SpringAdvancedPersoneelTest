package be.vdab.personeel.services;

import be.vdab.personeel.domain.Jobtitel;
import be.vdab.personeel.domain.Werknemer;
import be.vdab.personeel.exceptions.WerknemerNotFoundException;
import be.vdab.personeel.repositories.JpaDefaultWerknemerRepository;
import be.vdab.personeel.repositories.WerknemerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JpaWerknemerServicesTest {

    private JpaWerknemerServices services;
    @Mock
    private JpaDefaultWerknemerRepository repository;
    private Werknemer werknemer;
    private Jobtitel jobtitel;

    @BeforeEach
    void setup(){
        jobtitel = new Jobtitel("jobtitelName");
        werknemer = new Werknemer("surnameNull", "nameNull",
                "name@email.com", 2345L, jobtitel, BigDecimal.valueOf(100), "zorro", null,
                LocalDate.of(2000, 01, 01));
        services = new JpaWerknemerServices(repository);
    }

    @Test
    void opslag() {
        when(repository.findById(1)).thenReturn(Optional.of(werknemer));
        services.opslag(1, BigDecimal.TEN);
        assertThat(werknemer.getSalaris()).isEqualByComparingTo("110");
        verify(repository).findById(1);
    }

}