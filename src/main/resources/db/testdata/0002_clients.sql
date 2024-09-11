-- Wstawianie danych do tabeli Client
INSERT INTO Client (FIRST_NAME, LAST_NAME, PESEL, DATE_OF_BIRTH, EMAIL, MOBILE_NUMBER, PASSWORD, STREET, STREET_NO, APARTMENT_NO, CITY, ZIPCODE) VALUES
('Jan', 'Kowalski', '44051401359', '1980-05-14', 'jan.kowalski@example.com', '123456789', 'password123', 'Main Street', '1', '10', 'Warszawa', '00-001'),
('Anna', 'Nowak', '02270803628', '1990-07-08', 'anna.nowak@example.com', '987654321', 'password456', 'Second Street', '2', NULL, 'Kraków', '30-002'),
('Piotr', 'Wiśniewski', '55010212345', '1975-01-02', 'piotr.wisniewski@example.com', '112233445', 'password789', 'Third Street', '3', '5', 'Gdańsk', '80-003');

-- Wstawianie danych do tabeli Policy
INSERT INTO Policy (ID, POLICY_NUMBER, START_DATE, END_DATE, INSURANCE_PRODUCT_ID, COVERAGE_AMOUNT, RESERVE_AMOUNT, PREMIUM, CLIENT_ID) VALUES
(1, 'POL123456', '2023-01-01', '2024-01-01', 1, 100000.00, 5000.00, 1200.00, 1),
(2, 'POL123457', '2023-02-01', '2024-02-01', 2, 200000.00, 10000.00, 2400.00, 2),
(3, 'POL123458', '2023-03-01', '2024-03-01', 3, 300000.00, 15000.00, 3600.00, 3);
