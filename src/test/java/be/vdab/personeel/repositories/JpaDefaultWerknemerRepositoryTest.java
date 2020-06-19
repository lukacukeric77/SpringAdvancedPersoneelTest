package be.vdab.personeel.repositories;

import be.vdab.personeel.domain.Werknemer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaDefaultWerknemerRepository.class)
@Sql("/insertJobtitel.sql")
@Sql("/insertWerknemer.sql")
class JpaDefaultWerknemerRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaDefaultWerknemerRepository repository;

    public JpaDefaultWerknemerRepositoryTest(JpaDefaultWerknemerRepository repository) {
        this.repository = repository;
    }


    @Test
    void findCeo() {
        Werknemer werknemer = repository.findCeo().get();
        assertThat(werknemer.getChef()).isNull();
    }
}