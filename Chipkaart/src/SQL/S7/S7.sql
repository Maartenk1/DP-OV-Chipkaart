-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S7: Indexen
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------
-- LET OP, zoals in de opdracht op Canvas ook gezegd kun je informatie over
-- het query plan vinden op: https://www.postgresql.org/docs/current/using-explain.html


-- S7.1.
--
-- Je maakt alle opdrachten in de 'sales' database die je hebt aangemaakt en gevuld met
-- de aangeleverde data (zie de opdracht op Canvas).
--
-- Voer het voorbeeld uit wat in de les behandeld is:
-- 1. Voer het volgende EXPLAIN statement uit:
--    EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
--    Bekijk of je het resultaat begrijpt. Kopieer het explain plan onderaan de opdracht
-- 2. Voeg een index op stock_item_id toe:
--    CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
-- 3. Analyseer opnieuw met EXPLAIN hoe de query nu uitgevoerd wordt
--    Kopieer het explain plan onderaan de opdracht
-- 4. Verklaar de verschillen. Schrijf deze hieronder op.
EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
"Gather  (cost=1000.00..6151.87 rows=1006 width=96)"
"  Workers Planned: 2"
"  ->  Parallel Seq Scan on order_lines  (cost=0.00..5051.27 rows=419 width=96)"
"        Filter: (stock_item_id = 9)"

CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
"Bitmap Heap Scan on order_lines  (cost=12.09..2300.26 rows=1006 width=96)"
"  Recheck Cond: (stock_item_id = 9)"
"  ->  Bitmap Index Scan on ord_lines_si_id_idx  (cost=0.00..11.84 rows=1006 width=0)"
"        Index Cond: (stock_item_id = 9)"

-- Na het creëren van de INDEX is de cost een stuk lager. eerst was de kost (cost=0.00..5051.27 rows=419 width=96)
-- en nu nog maar (cost=12.09..2300.26 rows=1006 width=96). dit scheelt de helft.

-- S7.2.
--
-- 1. Maak de volgende twee query’s:
-- 	  A. Toon uit de order tabel de order met order_id = 73590
-- 	  B. Toon uit de order tabel de order met customer_id = 1028
-- 2. Analyseer met EXPLAIN hoe de query’s uitgevoerd worden en kopieer het explain plan onderaan de opdracht
-- 3. Verklaar de verschillen en schrijf deze op
-- 4. Voeg een index toe, waarmee query B versneld kan worden
-- 5. Analyseer met EXPLAIN en kopieer het explain plan onder de opdracht
-- 6. Verklaar de verschillen en schrijf hieronder op
EXPLAIN SELECT * from orders WHERE order_id = 73590
    "Index Scan using pk_sales_orders on orders  (cost=0.29..8.31 rows=1 width=155)"
"  Index Cond: (order_id = 73590)"

    EXPLAIN SELECT * from orders WHERE customer_id = 1028
    "Seq Scan on orders  (cost=0.00..1819.94 rows=106 width=155)"
"  Filter: (customer_id = 1028)"

CREATE INDEX B ON orders (customer_id);

EXPLAIN SELECT * from orders WHERE customer_id = 1028
    "Bitmap Heap Scan on orders  (cost=5.11..308.94 rows=106 width=155)"
"  Recheck Cond: (customer_id = 1028)"
"  ->  Bitmap Index Scan on b  (cost=0.00..5.09 rows=106 width=0)"
"        Index Cond: (customer_id = 1028)"

--Bij query A wordt er maar bij 1 rij gezocht. hierdoor is de cost een stuk lager.
--Bij query B wordt er in meerdere rijen gezocht. Hierdoor is de cost hoger dan bij A.
--Na het gebruiken van de index op query B is de cost een stuk lager. Dit komt omdat hij bij index alleen maar op column van customer_id zoekt.

-- S7.3.A
-- Het blijkt dat customers regelmatig klagen over trage bezorging van hun bestelling.
-- Het idee is dat verkopers misschien te lang wachten met het invoeren van de bestelling in het systeem.
-- Daar willen we meer inzicht in krijgen.
-- We willen alle orders (order_id, order_date, salesperson_person_id (als verkoper),
--    het verschil tussen expected_delivery_date en order_date (als levertijd),
--    en de bestelde hoeveelheid van een product zien (quantity uit order_lines).
-- Dit willen we alleen zien voor een bestelde hoeveelheid van een product > 250
--   (we zijn nl. als eerste geïnteresseerd in grote aantallen want daar lijkt het vaker mis te gaan)
-- En verder willen we ons focussen op verkopers wiens bestellingen er gemiddeld langer over doen.
-- De meeste bestellingen kunnen binnen een dag bezorgd worden, sommige binnen 2-3 dagen.
-- Het hele bestelproces is er op gericht dat de gemiddelde bestelling binnen 1.45 dagen kan worden bezorgd.
-- We willen in onze query dan ook alleen de verkopers zien wiens gemiddelde levertijd
--  (expected_delivery_date - order_date) over al zijn/haar bestellingen groter is dan 1.45 dagen.
-- Maak om dit te bereiken een subquery in je WHERE clause.
-- Sorteer het resultaat van de hele geheel op levertijd (desc) en verkoper.
-- 1. Maak hieronder deze query (als je het goed doet zouden er 377 rijen uit moeten komen, en het kan best even duren...)
SELECT orders.order_id, orders.order_date, orders.salesperson_person_id AS verkoper, (expected_delivery_date - order_date) AS levertijd, order_lines.quantity FROM orders
JOIN order_lines ON orders.order_id = order_lines.order_id
WHERE quantity IN (SELECT quantity FROM order_lines where quantity > 250)
  AND orders.salesperson_person_id IN (SELECT ord.salesperson_person_id FROM (SELECT AVG(expected_delivery_date - order_date) AS levertijd, salesperson_person_id FROM orders GROUP BY salesperson_person_id) AS ord WHERE levertijd > 1.45)
ORDER BY  levertijd DESC, verkoper

-- S7.3.B
--
-- 1. Vraag het EXPLAIN plan op van je query (kopieer hier, onder de opdracht)
-- 2. Kijk of je met 1 of meer indexen de query zou kunnen versnellen
-- 3. Maak de index(en) aan en run nogmaals het EXPLAIN plan (kopieer weer onder de opdracht)
-- 4. Wat voor verschillen zie je? Verklaar hieronder.
    EXPLAIN SELECT orders.order_id, orders.order_date, orders.salesperson_person_id AS verkoper, (expected_delivery_date - order_date) AS levertijd, order_lines.quantity FROM orders
                                                                                                                                                                                   JOIN order_lines ON orders.order_id = order_lines.order_id
            WHERE quantity IN (SELECT quantity FROM order_lines where quantity > 250)
              AND orders.salesperson_person_id IN (SELECT ord.salesperson_person_id FROM (SELECT AVG(expected_delivery_date - order_date) AS levertijd, salesperson_person_id FROM orders GROUP BY salesperson_person_id) AS ord WHERE levertijd > 1.45)
            ORDER BY  levertijd DESC, verkoper

    "Sort  (cost=24954.10..25127.66 rows=69424 width=20)"
"  Sort Key: ((orders.expected_delivery_date - orders.order_date)) DESC, orders.salesperson_person_id"
"  ->  Hash Join  (cost=10521.04..19371.32 rows=69424 width=20)"
"        Hash Cond: (order_lines.quantity = order_lines_1.quantity)"
"        ->  Hash Join  (cost=4375.12..12097.26 rows=69424 width=20)"
"              Hash Cond: (order_lines.order_id = orders.order_id)"
"              ->  Seq Scan on order_lines  (cost=0.00..6160.12 rows=231412 width=8)"
"              ->  Hash  (cost=4099.15..4099.15 rows=22078 width=16)"
"                    ->  Hash Join  (cost=2188.13..4099.15 rows=22078 width=16)"
"                          Hash Cond: (orders.salesperson_person_id = ord.salesperson_person_id)"
"                          ->  Seq Scan on orders  (cost=0.00..1635.95 rows=73595 width=16)"
"                          ->  Hash  (cost=2188.09..2188.09 rows=3 width=4)"
"                                ->  Subquery Scan on ord  (cost=2187.91..2188.09 rows=3 width=4)"
"                                      ->  HashAggregate  (cost=2187.91..2188.06 rows=3 width=36)"
"                                            Group Key: orders_1.salesperson_person_id"
"                                            Filter: (avg((orders_1.expected_delivery_date - orders_1.order_date)) > 1.45)"
"                                            ->  Seq Scan on orders orders_1  (cost=0.00..1635.95 rows=73595 width=12)"
"        ->  Hash  (cost=6145.16..6145.16 rows=61 width=4)"
"              ->  HashAggregate  (cost=6144.55..6145.16 rows=61 width=4)"
"                    Group Key: order_lines_1.quantity"
"                    ->  Gather  (cost=1000.00..6142.27 rows=910 width=4)"
"                          Workers Planned: 2"
"                          ->  Parallel Seq Scan on order_lines order_lines_1  (cost=0.00..5051.27 rows=379 width=4)"
"                                Filter: (quantity > 250)"

-- TIJD ZONDER INDEX 408 msec
CREATE INDEX index_quantity ON order_lines (quantity);
-- TIJD 361 msec
"Sort  (cost=18832.05..19005.61 rows=69424 width=20)"
"  Sort Key: ((orders.expected_delivery_date - orders.order_date)) DESC, orders.salesperson_person_id"
"  ->  Hash Join  (cost=4398.99..13249.26 rows=69424 width=20)"
"        Hash Cond: (order_lines.quantity = order_lines_1.quantity)"
"        ->  Hash Join  (cost=4375.12..12097.26 rows=69424 width=20)"
"              Hash Cond: (order_lines.order_id = orders.order_id)"
"              ->  Seq Scan on order_lines  (cost=0.00..6160.12 rows=231412 width=8)"
"              ->  Hash  (cost=4099.15..4099.15 rows=22078 width=16)"
"                    ->  Hash Join  (cost=2188.13..4099.15 rows=22078 width=16)"
"                          Hash Cond: (orders.salesperson_person_id = ord.salesperson_person_id)"
"                          ->  Seq Scan on orders  (cost=0.00..1635.95 rows=73595 width=16)"
"                          ->  Hash  (cost=2188.09..2188.09 rows=3 width=4)"
"                                ->  Subquery Scan on ord  (cost=2187.91..2188.09 rows=3 width=4)"
"                                      ->  HashAggregate  (cost=2187.91..2188.06 rows=3 width=36)"
"                                            Group Key: orders_1.salesperson_person_id"
"                                            Filter: (avg((orders_1.expected_delivery_date - orders_1.order_date)) > 1.45)"
"                                            ->  Seq Scan on orders orders_1  (cost=0.00..1635.95 rows=73595 width=12)"
"        ->  Hash  (cost=23.10..23.10 rows=61 width=4)"
"              ->  HashAggregate  (cost=22.49..23.10 rows=61 width=4)"
"                    Group Key: order_lines_1.quantity"
"                    ->  Index Only Scan using index_quantity on order_lines order_lines_1  (cost=0.29..20.22 rows=910 width=4)"
"                          Index Cond: (quantity > 250)"

-- De query geeft wel een verschillend resultaat, maar ze kosten even veel moeite. het aantal rijen die hij door moet lopen is hetzelfde

-- S7.3.C
--
-- Zou je de query ook heel anders kunnen schrijven om hem te versnellen?
-- Ja, door geen extra selectstatement meer te gebruiken. hierdoor werkt de index wel.