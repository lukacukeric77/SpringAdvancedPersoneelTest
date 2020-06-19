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
    public List<Werknemer> findByChefId(long chefId) {                    //looks for ondergeschichten
        return manager.createNamedQuery("Werknemer.findByChefid", Werknemer.class)
                .setParameter("idOfChef", chefId)
                .setHint("javax.persistence.loadgraph", manager.createEntityGraph("Werknemer.withChefAndJobtitles"))
                .getResultList();
    }

    @Override
    public Optional<Werknemer> findCeo() {                              //1st page opener
        return Optional.of(manager.createNamedQuery("Werknemer.findCeo", Werknemer.class)
                .setHint("javax.persistence.loadgraph", manager.createEntityGraph("Werknemer.withChefAndJobtitles"))
                .getSingleResult());
    }

    @Override
    public Optional<Werknemer> findById(long id) {
        return Optional.ofNullable(manager.find(Werknemer.class, id));
    }

    @Override
    public List<Werknemer> findByJobtitelId(long jobtitelId) {      //looks for werknemers by jobtitel id; on the page jobtitel, when you pick the jobtitel
        return manager.createQuery("select w from Werknemer w where w.jobtitel.id = :jobtitel", Werknemer.class)
                .setParameter("jobtitel", jobtitelId)
                .setHint("javax.persistence.loadgraph", manager.createEntityGraph("Werknemer.withChefAndJobtitles"))
                .getResultList();
    }
}
