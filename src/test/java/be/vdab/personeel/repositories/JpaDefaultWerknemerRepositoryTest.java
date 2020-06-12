package be.vdab.personeel.repositories;

import be.vdab.personeel.domain.Werknemer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(JpaDefaultWerknemerRepository.class)
@Sql("/insertJobtitel.sql")
@Sql("/insertWerknemer.sql")
class JpaDefaultWerknemerRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String WERKNEMERS = "werknemers";
    private final JpaDefaultWerknemerRepository repository;

    public JpaDefaultWerknemerRepositoryTest(JpaDefaultWerknemerRepository repository) {
        this.repository = repository;
    }

    private long idOfTestWerknemer(){
        return super.jdbcTemplate.queryForObject("select id from werknemers where familienaam='testInsert'", Long.class);
    }

    @Test
    void findByChefId() {
        List<Werknemer> werknemer = repository.findByChefId(idOfTestWerknemer());
        assertThat(werknemer).hasSize(super.countRowsInTableWhere(WERKNEMERS, "chefid = " + idOfTestWerknemer()));
    }

    @Test
    void findCeo() {
        Werknemer werknemer = repository.findCeo().get();
        assertThat(werknemer.getChef()).isNull();
    }
}