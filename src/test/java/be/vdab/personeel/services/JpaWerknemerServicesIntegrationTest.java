package be.vdab.personeel.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.assertj.core.api.Assertions.*;

import javax.persistence.ElementCollection;
import javax.persistence.EntityManager;
import java.math.BigDecimal;

@DataJpaTest
@Import(JpaWerknemerServices.class)
@ComponentScan(value = "be.vdab.personeel.repositories", resourcePattern = "JpaDefaultWerknemerRepository.class")
@Sql("/insertJobtitel.sql")
@Sql("/insertWerknemer.sql")
public class JpaWerknemerServicesIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaWerknemerServices services;
    private final EntityManager manager;

    public JpaWerknemerServicesIntegrationTest(JpaWerknemerServices services, EntityManager manager) {
        this.services = services;
        this.manager = manager;
    }

    private long idOfWerknemer(){
        return super.jdbcTemplate.queryForObject("select id from werknemers where voornaam='testInsert'", Long.class);
    }

    @Test
    void opslag() {
        long id = idOfWerknemer();
        services.opslag(id, BigDecimal.TEN);
        manager.flush();
        assertThat(super.jdbcTemplate.queryForObject("select salaris from werknemers where id=?", BigDecimal.class, id))
                .isEqualByComparingTo("1100");
    }
}
