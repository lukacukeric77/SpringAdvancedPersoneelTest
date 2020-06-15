package be.vdab.personeel.repositories;

import be.vdab.personeel.domain.Jobtitel;

import java.util.List;
import java.util.Optional;

public interface JobtitelRepository {

    Optional<Jobtitel> findById(long id);
    List<Jobtitel> findAll();

}
