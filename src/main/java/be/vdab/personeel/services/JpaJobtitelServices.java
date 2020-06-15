package be.vdab.personeel.services;

import be.vdab.personeel.domain.Jobtitel;
import be.vdab.personeel.repositories.JpaJobtitelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaJobtitelServices implements JobtitelServices {

    private final JpaJobtitelRepository repository;

    public JpaJobtitelServices(JpaJobtitelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Jobtitel> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Jobtitel> findAll() {
        return repository.findAll();
    }
}
