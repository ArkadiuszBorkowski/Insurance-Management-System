-- Wstawianie danych do tabeli Client - dane generowane

INSERT INTO Client (FIRST_NAME, LAST_NAME, PESEL, DATE_OF_BIRTH, EMAIL, MOBILE_NUMBER, STREET, STREET_NO, APARTMENT_NO, CITY, ZIPCODE) VALUES
('Roksana', 'Goral', '82120867193', '1982-12-08', 'roksana.goral@example.com', '656504266', 'Dębowa', '071', NULL, 'Płock', '62-197'),
('Marcelina', 'Bany', '92100211748', '1992-10-02', 'marcelina.bany@example.com', '220295779', 'Rybacka', '80', NULL, 'Czerwionka-Leszczyny', '28-431'),
('Blanka', 'Imiela', '45050222752', '1945-05-02', 'blanka.imiela@example.com', '965680768', 'Wiślana', '47/00', 81, 'Zambrów', '42-228'),
('Gaja', 'Maculewicz', '40021077856', '1940-02-10', 'gaja.maculewicz@example.com', '378624855', 'Północna', '25', NULL, 'Rzeszów', '75-883'),
('Nicole', 'Salwin', '81081670206', '1981-08-16', 'nicole.salwin@example.com', '091485625', 'Słoneczna', '12', NULL, 'Gdańsk', '80-001'),
('Leonard', 'Raba', '35051591238', '1935-05-15', 'leonard.raba@example.com', '643809043', 'Chmielna', '17/16', NULL, 'Sanok', '12-390'),
('Leonard', 'Rećko', '61031460997', '1961-03-14', 'leonard.recko@example.com', '536902228', 'Andersa', '284', 26, 'Brzeg', '49-741'),
('Albert', 'Ciepłuch', '86041224142', '1986-04-12', 'albert.ciepluch@example.com', '737822278', 'Jana Sobieskiego', '66/33', 50, 'Płońsk', '32-110'),
('Melania', 'Tekiela', '34083132326', '1934-08-31', 'melania.tekiela@example.com', '502594221', 'Wojciecha', '155', NULL, 'Nysa', '27-095'),
('Bianka', 'Reiter', '60060496455', '1960-06-04', 'bianka.reiter@example.com', '601123456', 'Kwiatowa', '12', NULL, 'Poznań', '60-001'),
('Cyprian', 'Gill', '40080474624', '1940-08-04', 'cyprian.gill@example.com', '886013144', 'Żytnia', '91/55', 95, 'Tomaszów Mazowiecki', '98-618'),
('Tobiasz', 'Cecot', '34081064680', '1934-08-10', 'tobiasz.cecot@example.com', '519414562', 'Widokowa', '40/18', NULL, 'Kędzierzyn-Koźle', '67-667'),
('Alan', 'Cudzich', '85072777063', '1985-07-27', 'alan.cudzich@example.com', '737989790', 'Kraszewskiego', '935', NULL, 'Łomża', '86-255'),
('Agnieszka', 'Forysiak', '76030471843', '1976-03-04', 'agnieszka.forysiak@example.com', '787172397', 'Piastowska', '714', NULL, 'Bochnia', '99-891'),
('Dawid', 'Bartoś', '79100965746', '1979-10-09', 'dawid.bartos@example.com', '220320249', 'Mokra', '44/33', 32, 'Skarżysko-Kamienna', '66-039'),
('Piotr', 'Goliasz', '44051401359', '1944-05-14', 'piotr.goliasz@example.com', '691545318', 'Szymanowskiego', '07', 69, 'Rumia', '54-540'),
('Malwina', 'Trojanek', '02281128420', '2002-08-11', 'malwina.trojanek@example.com', '512812066', 'Opolska', '98', 94, 'Luboń', '99-958'),
('Gabriel', 'Wypiór', '75030306916', '1975-03-03', 'gabriel.wypior@example.com', '576923993', 'Kolonia', '397', 97, 'Szczytno', '18-593'),
('Adrian', 'Raźny', '79111190553', '1979-11-11', 'adrian.razny@example.com', '789123456', 'Leśna', '12', NULL, 'Gdańsk', '80-001'),
('Katarzyna', 'Zielińska', '85010112345', '1985-01-01', 'katarzyna.zielinska@example.com', '600700800', 'Kwiatowa', '5', 10, 'Poznań', '60-001');


INSERT INTO Policy (ID, POLICY_NUMBER, START_DATE, END_DATE, INSURANCE_PRODUCT_ID, COVERAGE_AMOUNT, RESERVE_AMOUNT, PREMIUM, CLIENT_ID, POLICY_STATUS) VALUES
        (1, 'BP20231106001', '2023-11-06', '2024-11-06', 1, 100000.00, 50000.00, 1200.00, 1, 1),
        (2, 'BP20231106002', '2023-11-06', '2024-11-06', 2, 200000.00, 0.00, 1500.00, 2, 2),
        (3, 'BP20240110001', '2024-01-10', '2025-01-10', 3, 300000.00, 300000.00, 1800.00, 3, 0),
        (4, 'BP20240220001', '2024-02-20', '2025-02-20', 4, 400000.00, 400000.00, 2000.00, 4, 0),
        (5, 'BP20240305001', '2024-03-05', '2025-03-05', 1, 150000.00, 150000.00, 1300.00, 5, 0),
        (6, 'BP20240412001', '2024-04-12', '2025-04-12', 2, 250000.00, 250000.00, 1600.00, 6, 0),
        (7, 'BP20240518001', '2024-05-18', '2025-05-18', 3, 350000.00, 350000.00, 1900.00, 7, 0),
        (8, 'BP20240622001', '2024-06-22', '2025-06-22', 4, 450000.00, 450000.00, 2100.00, 8, 0),
        (9, 'BP20240730001', '2024-07-30', '2025-07-30', 1, 120000.00, 120000.00, 1250.00, 9, 0),
        (10, 'BP20240815001', '2024-08-15', '2025-08-15', 2, 220000.00, 220000.00, 1550.00, 10, 0),
        (11, 'BP20240910001', '2024-09-10', '2025-09-10', 3, 320000.00, 170000.00, 1850.00, 11, 0),
        (12, 'BP20241005001', '2024-10-05', '2025-10-05', 4, 420000.00, 420000.00, 2050.00, 12, 0),
        (13, 'BP20241020001', '2024-10-20', '2025-11-20', 1, 130000.00, 130000.00, 1350.00, 13, 0),
        (14, 'BP20241020002', '2024-10-20', '2025-11-20', 2, 230000.00, 230000.00, 1650.00, 14, 0),
        (15, 'BP20241020003', '2024-10-20', '2025-11-20', 3, 330000.00, 330000.00, 1950.00, 15, 0);


ALTER TABLE policy ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM policy)