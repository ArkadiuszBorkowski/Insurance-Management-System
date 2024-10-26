INSERT INTO claims (id, claim_number, description, claim_date, claim_registration_date, claim_status, decision, payment_amount, payment_date, claim_verification_status, policy_id) VALUES
        (1, '20230115001', 'Claim for car accident', '2023-01-15','2023-01-15', 'NOWE_ROSZCZENIE', 'AKCEPTACJA',NULL,NULL, NULL ,1),
        (2, '20230220001', 'Claim for house damage', '2023-02-20','2023-02-20', 'ZAMKNIĘTE', 'AKCEPTACJA',NULL,NULL,NULL, 2),
        (3, '20230115002', 'Claim for theft', '2023-01-15', '2023-01-15', 'WERYFIKACJA', 'ODMOWA',NULL,NULL,NULL, 1),
        (4, '20230405001', 'Claim for water damage', '2023-04-05','2023-04-05', 'OCZEKIWANIE_NA_DOKUMENTY', 'ODMOWA',NULL,NULL,NULL, 3),
        (5, '20230512001', 'Claim for fire damage', '2023-05-12','2023-05-12', 'W_TRAKCIE_REALIZACJI', 'AKCEPTACJA',NULL,NULL,NULL, 4),
        (6, '20230618001', 'Claim for medical expenses', '2023-06-18','2023-06-18', 'ZATWIERDZONE', 'AKCEPTACJA',NULL,NULL,NULL, 5),
        (7, '20230722001', 'Claim for travel cancellation', '2023-07-22','2023-07-22', 'ODRZUCONE', 'ODMOWA',NULL,NULL,NULL, 6),
        (8, '20230830001', 'Claim for lost luggage', '2023-08-30','2023-08-30', 'WYPŁACONE', 'AKCEPTACJA',NULL,NULL,NULL, 7),
        (9, '20230914001', 'Claim for property damage', '2023-09-14','2023-09-14', 'NOWE_ROSZCZENIE', 'AKCEPTACJA',NULL,NULL,NULL, 4),
        (10, '20231001001', 'Claim for liability', '2023-10-01','2023-10-01', 'WERYFIKACJA', 'ODMOWA',NULL,NULL,NULL, 9),
        (11, '20231106001', 'Claim for natural disaster', '2023-11-05','2023-11-05', 'OCZEKIWANIE_NA_DOKUMENTY', 'AKCEPTACJA',NULL,NULL,NULL, 10),
        (12, '20231222001','Claim for vandalism', '2023-12-20', '2023-12-20', 'W_TRAKCIE_REALIZACJI', 'AKCEPTACJA',NULL,NULL,NULL, 11);

ALTER TABLE claims ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM claims);