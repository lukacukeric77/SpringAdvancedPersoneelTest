package be.vdab.personeel.repositories;

import be.vdab.personeel.domain.Werknemer;

import java.util.List;
import java.util.Optional;

public interface WerknemerRepository {
    List<Werknemer> findByChefId(long chefId);
    Optional<Werknemer> findCeo();
    Optional<Werknemer> findById(long id);

}
