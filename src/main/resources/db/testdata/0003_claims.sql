INSERT INTO claims (id, claim_number, description, claim_date, claim_registration_date, claim_status, decision, payment_amount, payment_date, claim_verification_status, policy_id) VALUES
    (1, '20231220001', 'W wyniku burzy doszło do uszkodzenia dachu domu: uszkodzenie instalacji elektrycznej i mebli.', '2023-12-15', '2023-12-20', 'WYPŁACONE', 'AKCEPTACJA', 50000.00, '2023-12-29', 'Roszczenie zostało wypłacone.', 1),
    (2, '20231220002', 'Uszkodzenie mechaniczne maszyny CNC', '2023-11-20', '2023-12-20', 'WYPŁACONE', 'AKCEPTACJA', 200000.00, '2024-01-03', 'Roszczenie zostało wypłacone.', 2),
    (3, '20240115001', 'Zalanie jachtu podczas sztormu', '2024-01-10', '2024-01-15', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 3),
    (4, '20240225001', 'Uszkodzenie instalacji elektrycznej w domu', '2024-02-20', '2024-02-25', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 4),
    (5, '20240310001', 'Zalanie piwnicy w wyniku powodzi', '2024-03-05', '2024-03-10', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 5),
    (6, '20240405001', 'Pożar w warsztacie', '2024-04-01', '2024-04-05', 'WYPŁACONE', 'AKCEPTACJA', 100000.00, '2024-04-15', 'Roszczenie zostało wypłacone.', 6),
    (7, '20240520001', 'Kradzież sprzętu elektronicznego', '2024-05-15', '2024-05-20', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 7),
    (8, '20240610001', 'Zalanie mieszkania', '2024-06-05', '2024-06-10', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 8),
    (9, '20240725001', 'Uszkodzenie silnika jachtu', '2024-07-20', '2024-07-25', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 9),
    (10, '20240815001', 'Zalanie piwnicy', '2024-08-10', '2024-08-15', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 10),
    (11, '20240905001', 'Pożar w domu', '2024-09-01', '2024-09-05', 'WYPŁACONE', 'AKCEPTACJA', 150000.00, '2024-09-15', 'Roszczenie zostało wypłacone.', 11),
    (12, '20241010001', 'Uszkodzenie maszyny produkcyjnej', '2024-10-05', '2024-10-10', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 12),
    (13, '20241105001', 'Zalanie mieszkania', '2024-10-15', '2024-11-05', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 13),
    (14, '20241105002', 'Uszkodzenie jachtu podczas sztormu', '2024-08-15', '2024-11-05', 'OCZEKIWANIE_NA_DOKUMENTY', 'ANALIZA', NULL, NULL, 'Oczekiwanie na dokumenty.', 14);
ALTER TABLE claims ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM claims);