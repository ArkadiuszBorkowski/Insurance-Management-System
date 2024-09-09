-- Wstawianie danych do tabeli Risk
INSERT INTO Risk (RISK_NAME) VALUES
        ('ZALANIE'),
        ('POŻAR'),
        ('KRADZIEŻ'),
        ('SKUTKI WICHURY'),
        ('ZNISZCZENIA MURÓW'),
        ('ZNISZCZENIA AGD'),
        ('PRZEPIĘCIA');

INSERT INTO Insurance_Product (product_name, description) VALUES
        ('BEZPIECZNY_DOM', 'Ubezpieczenie obejmujące szeroki zakres ryzyk dla domu'),
        ('BEZPIECZNE_MASZYNY', 'Ubezpieczenie dla maszyn obejmujące wybrane ryzyka');

INSERT INTO product_risk (product_id, risk_id) VALUES
-- BEZPIECZNY DOM
(1, 1), -- ZALANIE
(1, 2), -- POŻAR
(1, 3), -- KRADZIEŻ
(1, 4), -- SKUTKI WICHURY
(1, 5), -- ZNISZCZENIA MURÓW
(1, 6), -- ZNISZCZENIA AGD
(1, 7), -- PRZEPIĘCIA

-- BEZPIECZNE MASZYNY
(2, 1), -- ZALANIE
(2, 2), -- POŻAR
(2, 3), -- KRADZIEŻ
(2, 7); -- PRZEPIĘCIA