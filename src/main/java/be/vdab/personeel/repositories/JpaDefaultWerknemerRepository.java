package be.vdab.personeel.repositories;

import be.vdab.personeel.domain.Werknemer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaDefaultWerknemerRepository implements WerknemerRepository{

    private final EntityManager manager;

    public JpaDefaultWerknemerRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<Werknemer> findByChefId(long chefId) {
        return manager.createNamedQuery("Werknemer.findByChefid", Werknemer.class)
                .setParameter("idOfChef", chefId)
                .getResultList();
    }

    @Override
    public Optional<Werknemer> findCeo() {
        return Optional.of(manager.createNamedQuery("Werknemer.findCeo", Werknemer.class).getSingleResult());
    }

    @Override
    public Optional<Werknemer> findById(long id) {
        return Optional.ofNullable(manager.find(Werknemer.class, id));
    }
}
