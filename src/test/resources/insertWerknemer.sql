insert into werknemers(familienaam, voornaam, email, chefid, jobtitelid, salaris, paswoord, geboorte, rijksregisternr)
values ('testInsert', 'testInsert', 'testInsert@email.com', 1, (select id from jobtitels where naam='testInsert'), 1234,
        'zorro', '2001-01-02', 1000);