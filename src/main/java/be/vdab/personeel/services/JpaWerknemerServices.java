package be.vdab.personeel.services;

import be.vdab.personeel.domain.Werknemer;
import be.vdab.personeel.repositories.JpaDefaultWerknemerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaWerknemerServices implements WerknemerServices {

    private final JpaDefaultWerknemerRepository repository;

    public JpaWerknemerServices(JpaDefaultWerknemerRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Werknemer> findByChefId(long chefId) {
        return repository.findByChefId(chefId);
    }

    @Override
    public Optional<Werknemer> findCeo() {
        return repository.findCeo();
    }

    @Override
    public Optional<Werknemer> findById(long id) {
        return repository.findById(id);
    }
}
