
USE CANIBAU;

-- NOTE:
-- UNICO APPUNTAMENTO COMPLETATO (DI PROVA)  CON PAGAMENTO E REVIEW E' QUELLO FRA MARCO.CARTA E GIOVANNI MUCIACCIA (VEDI ASSIGNMENT)

-- LAST UPDATE:

-- ELIMINATO ATTRIBUTO AREA DA DOGSITTERS;
-- AGGIUNTO TUPLA A TRANSACTIONS;
-- AGGIUNTO DOGSITTER "MARIOBIANCHI" CHE SIA ANCHE CLIENTE, CON RELATIVE TUPLE NELLE ALTRE TABLES



-- CREDIT CARDS

INSERT INTO CREDIT_CARDS VALUES ('5311234567899811', 'RICCARDO', 'GIURA', '2020-03-31',111, 1000);
INSERT INTO CREDIT_CARDS VALUES ('5311234567650022', 'GIANNI', 'NORVEGESE', '2022-06-30',589, 20000);
INSERT INTO CREDIT_CARDS VALUES ('5311555566667777', 'FILIPPO', 'ALFIERI', '2023-02-28', 608, 10000);
INSERT INTO CREDIT_CARDS VALUES ('5311234567654321', 'GIOVANNI', 'MUCIACCIA', '2023-06-30', 609, 0);
INSERT INTO CREDIT_CARDS VALUES ('5311222233334444', 'MARCO', 'CARTA', '2023-11-30', 699, 200);
INSERT INTO CREDIT_CARDS VALUES ('5311888899990000', 'ERICA', 'ROSSI', '2023-12-31', 009, 500);
INSERT INTO CREDIT_CARDS VALUES ('5311234567659999', 'MARIO', 'BIANCHI', '2023-10-31', 444, 2000);



-- ADDRESS


INSERT INTO ADDRESS VALUES ('RICCARDOGIURA@GMAIL.COM', 'ITALY', 'FIRENZE', 'VIA ROMA', '5', '50100');
INSERT INTO ADDRESS VALUES ('GIOVANNINO0@GMAIL.COM', 'ITALY', 'ROMA', 'VIA TEVERE', '10B', '00100');
INSERT INTO ADDRESS VALUES ('GIANNINORVEGESE@GMAIL.COM', 'ITALY', 'PAVIA', 'VIALE MATTEOTTI', '33C', '27100');
INSERT INTO ADDRESS VALUES ('PETERPARKER@GMAIL.COM', 'ITALY', 'CORTEOLONA', 'VIA CAVALLOTTI', '12', '27014');
INSERT INTO ADDRESS VALUES ('MARIOBIANCHI@LIBERO.COM', 'ITALY', 'BELGIOIOSO', 'VIA MANZONI', '1', '27011');
INSERT INTO ADDRESS VALUES ('ERICA.ROSSI@GMAIL.COM', 'ITALY', 'MILANO', 'VIA CANALETTO', '101', '20019');
INSERT INTO ADDRESS VALUES ('TRUMPDNLD@GMAIL.COM', 'UNITED KINGDOM', 'LONDON', 'VIA ROMA', '5', 'E1');
INSERT INTO ADDRESS VALUES ('FILIPPO_ALFIERI@GMAIL.COM', 'UNITED STATES', 'CHICAGO', 'NORTH AVENUE', '155', '60007');
INSERT INTO ADDRESS VALUES ('SALVOMONTA@GMAIL.COM', 'UNITED STATES', 'CHICAGO', 'ILLINOIS ST', '503', '60007');
INSERT INTO ADDRESS VALUES ('BERLUSCA.SILVIO@GMAIL.COM', 'ITALY', 'FIRENZE', 'VIA DANTE', '14', '50100');
INSERT INTO ADDRESS VALUES ('MARCO.CARTA@GMAIL.COM', 'ITALY', 'VOGHERA', 'PIAZZA MEARDI', '12', '15999');



-- CUSTOMERS

INSERT INTO CUSTOMERS VALUES('RICCARDOGIURA@GMAIL.COM', 'RICCARDO', 'GIURA', 'PROVAPROVA123', 3334445556, '1995-04-03', '5311234567899811');
INSERT INTO CUSTOMERS VALUES('GIOVANNINO0@GMAIL.COM', 'GIOVANNI', 'MUCIACCIA', 'HEARTATTACK', 3494923123, '1960-02-01', '5311234567654321');
INSERT INTO CUSTOMERS VALUES('GIANNINORVEGESE@GMAIL.COM', 'GIANNI', 'NORVEGESE', 'CHIMIAIUTA', 3321213435, '1921-06-08', '5311234567650022');
INSERT INTO CUSTOMERS VALUES('MARIOBIANCHI@LIBERO.COM', 'MARIO', 'BIANCHI', 'SUPERMARIO', 3317858334, '1912-11-30', '5311234567659999');

-- Tuple il cui inserimento andrebbe completato anche nelle altre tabelle per non avere errori dovuti a chiavi esterne

-- INSERT INTO CUSTOMERS VALUES('PETERPARKER@GMAIL.COM', 'PETER', 'PARKER', 'RAGNO1122', 3354324689, '1989-12-25', 5311234567653377);
-- INSERT INTO CUSTOMERS VALUES('BENEDEDETTO00@GMAIL.COM', 'BENEDETTO', 'DA NORCIA', 'ORAETLABORA', 3998877651, '1910-07-19', 5311234567651221);
-- INSERT INTO CUSTOMERS VALUES('TRUMPDNLD@GMAIL.COM', 'DONALD', 'TRUMP', 'MAKEAMERIKAGRTAGAIN', 3314364659, '1961-02-21', 5311234567654936);
-- INSERT INTO CUSTOMERS VALUES('TOMMASOCROCIERA@GMAIL.COM', 'TOMMASO', 'CROCIERA', 'MISSIONIMPOSSIBLE', 3400667216, '1959-01-12', 5311234567657100);
-- INSERT INTO CUSTOMERS VALUES('SALVOMONTA@GMAIL.COM', 'SALVATORE', 'MONTALBANO', 'VIGATA123', 3356750828, '1964-03-01', 5311234567656081);
-- INSERT INTO CUSTOMERS VALUES('BERLUSCA.SILVIO@GMAIL.COM', 'SILVIO', 'BERLUSCONI', 'CAVALIEREEE', 3235791110, '1937-05-09', 5311234567650133);


-- DOGSITTERS


INSERT INTO DOGSITTERS VALUES ('MARCO.CARTA@GMAIL.COM', 'MARCO','CARTA','PROGETTO123',3245761239, '1985-12-24', '5311222233334444', TRUE, 5, 'AMO I CANI XD');
INSERT INTO DOGSITTERS VALUES ('FILIPPO_ALFIERI@GMAIL.COM', 'FILIPPO', 'ALFIERI', 'PROVA001', 3324790136, '1993-09-20','5311555566667777', TRUE, 3, 'CON QUANTI BIT?' );
INSERT INTO DOGSITTERS VALUES ('ERICA.ROSSI@GMAIL.COM', 'ERICA', 'ROSSI', 'SDFGHJKL12', 3412345670, '2000-01-01', '5311888899990000', FALSE , 1, 'HOLA' );
INSERT INTO DOGSITTERS VALUES('MARIOBIANCHI@LIBERO.COM', 'MARIO', 'BIANCHI', 'SUPERMARIO', 3317858334, '1912-11-30', '5311234567659999', TRUE, 2, 'MI CHIAMO BIANCHI PERCHè MARIO ROSSI ERA GIà OCCUPATO');


-- TRANSACTIONS

INSERT INTO TRANSACTIONS VALUES ('GIOVANNINO0@GMAIL.COM', 'MARCO.CARTA@GMAIL.COM', '2018-03-08 20:00:00', 5.00);

-- DOGS

INSERT INTO DOGS VALUES (1, 'FIDO', 'BULLDOG', 15.5,7, 'GIANNINORVEGESE@GMAIL.COM');
INSERT INTO DOGS VALUES (2, 'PALLINA', 'PUG', 6, 10, 'GIOVANNINO0@GMAIL.COM');
INSERT INTO DOGS VALUES (3, 'HERMES', 'BORDER COLLIE', 20, 3, 'RICCARDOGIURA@GMAIL.COM');
INSERT INTO DOGS VALUES (4, 'THOR', 'GERMAN SHORTHAIRED POINTER', 30, 2, 'RICCARDOGIURA@GMAIL.COM');
INSERT INTO DOGS VALUES (5, 'ALFONSO', 'BIG HALF-BREED', 40, 11, 'RICCARDOGIURA@GMAIL.COM');

-- ASSIGNMENT

INSERT INTO ASSIGNMENT VALUES ('A000001', 'RICCARDOGIURA@GMAIL.COM', 'FILIPPO_ALFIERI@GMAIL.COM', TRUE, '2018-07-12 13:20:00', '2018-07-15 15:20:00');
INSERT INTO ASSIGNMENT VALUES ('A000002', 'GIOVANNINO0@GMAIL.COM', ' MARCO.CARTA@GMAIL.COM', TRUE, '2018-03-09 09:00:00', '2018-03-10 19:00:00');

-- DOG ASSIGNMENT

INSERT INTO DOG_ASSIGNMENT VALUES ('A000001', 3);
INSERT INTO DOG_ASSIGNMENT VALUES ('A000002', 2);

-- REVIEW
INSERT INTO REVIEW VALUES ('GIOVANNINO0@GMAIL.COM', 'MARCO.CARTA@GMAIL.COM', '2018-03-11 20:30:26', 5, 'OTTIMO DOGSITTER, LO CONSIGLIO', 'COME DA TITOLO, BELLA RAGA, SCRITTE A CASO PER RIEMPIRE IL TESTO E SE LE STAI LEGGENDO PER INTERO SEI UN PAZZO/A', 'GRAZIE MILLE ZIO SALVO');


-- DOGSITTER AREA
INSERT INTO DOGSITTER_AREA VALUES ('MARCO.CARTA@GMAIL.COM', 'PAVIA');
INSERT INTO DOGSITTER_AREA VALUES ('FILIPPO_ALFIERI@GMAIL.COM', 'MILANO');
INSERT INTO DOGSITTER_AREA VALUES ('MARIOBIANCHI@LIBERO.COM', 'TORINO');
INSERT INTO DOGSITTER_AREA VALUES ('MARIOBIANCHI@LIBERO.COM', 'TORTONA');
INSERT INTO DOGSITTER_AREA VALUES ('MARIOBIANCHI@LIBERO.COM', 'VERCELLI');





-- DOGS_ACCEPTED
INSERT INTO DOGS_ACCEPTED VALUES ('MARCO.CARTA@GMAIL.COM', TRUE, TRUE, TRUE, TRUE);
INSERT INTO DOGS_ACCEPTED VALUES ('ERICA.ROSSI@GMAIL.COM', TRUE, FALSE, FALSE, FALSE);
INSERT INTO DOGS_ACCEPTED VALUES ('FILIPPO_ALFIERI@GMAIL.COM', TRUE, TRUE, FALSE, FALSE);
INSERT INTO DOGS_ACCEPTED VALUES ('MARIOBIANCHI@LIBERO.COM', TRUE, TRUE, TRUE, FALSE);



-- AVAILABILITY
INSERT INTO AVAILABILITY VALUES ('ERICA.ROSSI@GMAIL.COM', '02:00', '23:59', '00:00', '23:59', '14:00', '15:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO AVAILABILITY VALUES ('MARCO.CARTA@GMAIL.COM', '00:00', '23:59', '00:00', '23:59', '14:00', '15:00', '00:00', '23:59', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO AVAILABILITY VALUES ('FILIPPO_ALFIERI@GMAIL.COM', NULL, NULL, '00:00', '23:59', '14:00', '15:00', '00:00', '23:59', NULL, NULL, NULL, NULL, '11:00', '18:00');
INSERT INTO AVAILABILITY VALUES ('MARIOBIANCHI@LIBERO.COM', NULL, NULL, '00:00', '23:59', '14:00', '15:00', '00:00', '23:59', '00:00', '12:00', NULL, NULL, '11:00', '18:00');


-- MEETING POINT
INSERT INTO MEETING_POINT VALUES ('A000001', 'ITALY', 'PAVIA', 'VIALE MATTEOTTI', '33C', '27100');
INSERT INTO MEETING_POINT VALUES ('A000002', 'ITALY', 'PAVIA', 'VIALE GIANNI', '12C', '27100');