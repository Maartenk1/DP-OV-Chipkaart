-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht SQL.S1: Data Definition Language
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
--
--
-- Opdracht: schrijf SQL-queries om onderstaande resultaten op te vragen,
-- aan te maken, verwijderen of aan te passen in de database van de
-- bedrijfscasus.
--
-- Codeer je uitwerking onder de regel 'DROP VIEW ...' (bij een SELECT)
-- of boven de regel 'ON CONFLICT DO NOTHING;' (bij een INSERT)
-- Je kunt deze eigen query selecteren en los uitvoeren, en wijzigen tot
-- je tevreden bent.
--
-- Vervolgens kun je je uitwerkingen testen door de testregels
-- (met [TEST] erachter) te activeren (haal hiervoor de commentaartekens
-- weg) en vervolgens het hele bestand uit te voeren. Hiervoor moet je de
-- testsuite in de database hebben geladen (bedrijf_postgresql_test.sql).
-- NB: niet alle opdrachten hebben testregels.
--
-- Lever je werk pas in op Canvas als alle tests slagen. Draai daarna
-- alle wijzigingen in de database terug met de queries helemaal onderaan.
-- ------------------------------------------------------------------------


-- SQL.S1.1. Geslacht
--
-- Voeg een kolom `geslacht` toe aan de medewerkerstabel.
-- Voeg ook een beperkingsregel `m_geslacht_chk` toe aan deze kolom,
-- die ervoor zorgt dat alleen 'M' of 'V' als geldige waarde wordt
-- geaccepteerd. Test deze regel en neem de gegooide foutmelding op als
-- commentaar in de uitwerking.

--EERST:
ALTER TABLE medewerkers
    ADD geslacht varchar(255);

--DAN:
ALTER TABLE medewerkers ADD CONSTRAINT
    m_geslacht_chk CHECK (geslacht = 'M' OR geslacht = 'V');

--TESTEN:
UPDATE medewerkers
SET geslacht = 'M'
WHERE mnr = 7369;

--TEST 2:
UPDATE medewerkers
SET geslacht = 'K'
WHERE mnr = 7499;

ERROR: new row for relation "medewerkers" violates check constraint "m_geslacht_chk" DETAIL: Failing row contains (7499, ALDERS, JAM, VERKOPER, 7698, 1981-02-20, 1600.00, 200.00, 30, K). SQL state: 23514



            -- SQL.S1.2. Nieuwe afdeling
--
        -- Het bedrijf krijgt een nieuwe onderzoeksafdeling 'ONDERZOEK' in Zwolle.
            -- Om de onderzoeksafdeling op te zetten en daarna te leiden wordt de
-- nieuwe medewerker A DONK aangenomen. Hij krijgt medewerkersnummer 8000
            -- en valt direct onder de directeur.
            -- Voeg de nieuwe afdeling en de nieuwe medewerker toe aan de database.

INSERT INTO medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal)
VALUES (8000, 'DONK', 'A', 'ONDERZOEKER', 7839, '2000-02-12', 5000);

INSERT INTO afdelingen (anr, naam, locatie, hoofd)
VALUES (50, 'ONDERZOEK', 'ZWOLLE', 7839)


-- SQL.S1.3. Verbetering op afdelingentabel
--
       -- We gaan een aantal verbeteringen doorvoeren aan de tabel `afdelingen`:
       --   a) Maak een sequence die afdelingsnummers genereert. Denk aan de beperking
--      dat afdelingsnummers veelvouden van 10 zijn.
--   b) Voeg een aantal afdelingen toe aan de tabel, maak daarbij gebruik van
--      de nieuwe sequence.
--   c) Op enig moment gaat het mis. De betreffende kolommen zijn te klein voor
--      nummers van 3 cijfers. Los dit probleem op.


--    A)
CREATE SEQUENCE afdelingsnummers
    START WITH 60
    INCREMENT BY 10 ;

alter table afdelingen
    alter column anr set default nextval('afdelingsnummers');

--B)
insert into afdelingen (naam, locatie, hoofd)
VALUES('ONDERZOEK', 'GOUDA', 9000);

insert into afdelingen (naam, locatie, hoofd)
VALUES('TEST1', 'GOUDA', 7788);

insert into afdelingen (naam, locatie, hoofd)
VALUES('TEST2', 'GOUDA', 7788);

insert into afdelingen (naam, locatie, hoofd)
VALUES('TEST3', 'GOUDA', 7788);

--C)
ALTER TABLE afdelingen ALTER COLUMN anr type numeric(3,0);
ALTER TABLE medewerkers ALTER COLUMN afd type numeric(3,0);


-- SQL.S1.4. Adressen
--
        -- Maak een tabel `adressen`, waarin de adressen van de medewerkers worden
-- opgeslagen (inclusief adreshistorie). De tabel bestaat uit onderstaande
-- kolommen. Voeg minimaal één rij met adresgegevens van A DONK toe.
            --
            --    postcode      PK, bestaande uit 6 karakters (4 cijfers en 2 letters)
--    huisnummer    PK
--    ingangsdatum  PK
--    einddatum     moet na de ingangsdatum liggen
--    telefoon      10 cijfers, uniek
--    med_mnr       FK, verplicht

Create table adressen (
                          postcode VARCHAR(6) CONSTRAINT
                            constraint_postcode CHECK (postcode LIKE '[0-9][0-9][0-9][0-9][a-z,A-Z][a-z,A-Z]'),
                          huisnummer varchar(10),
                          ingangsdatum DATE,
                          einddatum DATE
                              CHECK (ingangsdatum < einddatum),
                          telefoon int UNIQUE,
                          med_mnr int NOT NULL,
                          CONSTRAINT TenDigits CHECK (telefoon BETWEEN 1000000000 and 9999999999),
                          PRIMARY KEY (postcode, huisnummer, ingangsdatum),
                          FOREIGN KEY (med_mnr) REFERENCES medewerkers(mnr));

    INSERT INTO adressen (postcode, huisnummer,ingangsdatum, einddatum,telefoon,med_mnr) VALUES ('1234AZ' ,'15A' ,'1997-08-31', '2020-08-31', 0900262626, 1);

-- SQL.S1.5. Commissie
--
        -- De commissie van een medewerker (kolom `comm`) moet een bedrag bevatten als de medewerker een functie als
-- 'VERKOPER' heeft, anders moet de commissie NULL zijn. Schrijf hiervoor een beperkingsregel. Gebruik onderstaande
-- 'illegale' INSERTs om je beperkingsregel te controleren.

ALTER TABLE medewerkers
    ADD CONSTRAINT constraint_verkoper CHECK (comm IS NOT NULL AND functie = 'VERKOPER' OR comm IS NULL AND functie != 'VERKOPER');

INSERT INTO medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal, comm)
VALUES (8001, 'MULLER', 'TJ', 'TRAINER', 7566, '1982-08-18', 2000, 500);

INSERT INTO medewerkers (mnr, naam, voorl, functie, chef, gbdatum, maandsal, comm)
VALUES (8002, 'JANSEN', 'M', 'VERKOPER', 7698, '1981-07-17', 1000, NULL);



-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_exists('SQL.S1.1', 1) AS resultaat
UNION
SELECT * FROM test_exists('SQL.S1.2', 1) AS resultaat
UNION
SELECT 'SQL.S1.3 wordt niet getest: geen test mogelijk.' AS resultaat
UNION
SELECT * FROM test_exists('SQL.S1.4', 6) AS resultaat
UNION
SELECT 'SQL.S1.5 wordt niet getest: handmatige test beschikbaar.' AS resultaat
ORDER BY resultaat;


-- Draai alle wijzigingen terug om conflicten in komende opdrachten te voorkomen.
DROP TABLE IF EXISTS adressen;
UPDATE medewerkers SET afd = NULL WHERE mnr < 7369 OR mnr > 7934;
UPDATE afdelingen SET hoofd = NULL WHERE anr > 40;
DELETE FROM afdelingen WHERE anr > 40;
DELETE FROM medewerkers WHERE mnr < 7369 OR mnr > 7934;
ALTER TABLE medewerkers DROP CONSTRAINT IF EXISTS m_geslacht_chk;
ALTER TABLE medewerkers DROP COLUMN IF EXISTS geslacht;