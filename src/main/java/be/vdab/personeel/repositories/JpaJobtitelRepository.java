package be.vdab.personeel.repositories;

import be.vdab.personeel.domain.Jobtitel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaJobtitelRepository implements JobtitelRepository {

private final EntityManager entityManager;

    public JpaJobtitelRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Jobtitel> findById(long id) {
        return Optional.ofNullable(entityManager.find(Jobtitel.class, id));
    }


    @Override
    public List<Jobtitel> findAll() {
        return entityManager.createQuery("select j from Jobtitel j order by j.naam", Jobtitel.class).getResultList();
    }
}
