package be.vdab.personeel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USERS_BY_USERNAME = "select email as username, paswoord as password, true as enabled from werknemers where email=?";
    private static final String AUTHORITIES_BY_USERNAME = "select werknemers.email as username, jobtitels.naam as authorities" +
            " from werknemers inner join jobtitels on werknemers.jobtitelid=jobtitels.id where werknemers.email=?";

    @Bean
    JdbcDaoImpl jdbcDao(DataSource dataSource){
        JdbcDaoImpl impl = new JdbcDaoImpl();
        impl.setDataSource(dataSource);
        impl.setUsersByUsernameQuery(USERS_BY_USERNAME);
        impl.setAuthoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME);
        return impl;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
        .mvcMatchers("/images/**")
        .mvcMatchers("/css/**")
        .mvcMatchers("/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").and().logout().logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .mvcMatchers("/", "/login", "/jobtitels").permitAll()
                .mvcMatchers("/werknemer/**").authenticated();


    }
}
