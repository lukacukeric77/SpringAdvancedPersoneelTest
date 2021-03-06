package be.vdab.personeel.repositories;

import be.vdab.personeel.domain.Werknemer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WerknemerRepository {
    Optional<Werknemer> findCeo();
    Optional<Werknemer> findById(long id);
    List<Werknemer> findByJobtitelId(long jobtitelId);

}
