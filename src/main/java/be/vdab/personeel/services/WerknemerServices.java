package be.vdab.personeel.services;

import be.vdab.personeel.domain.Werknemer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WerknemerServices {
    Optional<Werknemer> findCeo();
    Optional<Werknemer> findById(long id);
    void opslag(long id, BigDecimal percentage);
    void wijzigRijksregisternummer(long id, Long nummer);
    List<Werknemer> findByJobtitelId(long jobtitelId);
}
