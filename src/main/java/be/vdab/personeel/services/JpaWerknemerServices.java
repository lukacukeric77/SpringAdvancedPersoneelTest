package be.vdab.personeel.services;

import be.vdab.personeel.domain.Werknemer;
import be.vdab.personeel.exceptions.WerknemerNotFoundException;
import be.vdab.personeel.repositories.JpaDefaultWerknemerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service @Transactional
public class JpaWerknemerServices implements WerknemerServices {

    private final JpaDefaultWerknemerRepository repository;

    public JpaWerknemerServices(JpaDefaultWerknemerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Werknemer> findCeo() {
        return repository.findCeo();
    }

    @Override
    public Optional<Werknemer> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void opslag(long id, BigDecimal percentage) {
        Optional<Werknemer> optionalWerknemer = repository.findById(id);
        if (optionalWerknemer.isPresent()){
            optionalWerknemer.get().opslag(percentage);
        } else {
            throw new WerknemerNotFoundException();
        }
    }

    @Override
    public void wijzigRijksregisternummer(long id, Long nummer) {
        Optional<Werknemer> optionalWerknemer = repository.findById(id);
        if (optionalWerknemer.isPresent()){
            optionalWerknemer.get().setRijksregisternr(nummer);
        } else {
            throw new WerknemerNotFoundException();
        }
    }

    @Override
    public List<Werknemer> findByJobtitelId(long jobtitelId) {
        return repository.findByJobtitelId(jobtitelId);
    }
}
