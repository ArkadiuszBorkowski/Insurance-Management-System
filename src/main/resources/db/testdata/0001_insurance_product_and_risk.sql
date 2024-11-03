-- Wstawianie danych do tabeli Risk
INSERT INTO Risk (RISK_NAME, ICON_NAME) VALUES
        ('ZALANIE', 'flood'),
        ('POŻAR', 'fire_truck'),
        ('KRADZIEŻ', 'sign_language' ),
        ('SKUTKI WICHURY', 'storm'),
        ('ZNISZCZENIA MURÓW', 'wb_shade'),
        ('ZNISZCZENIA AGD', 'kitchen'),
        ('WANDALIZM', 'mood_bad'),
        ('USZKODZENIE SZYB', 'window'),
        ('USZKODZENIE MIENIA', 'engineering'),
        ('PRZEPIĘCIA', 'electric_bolt');

INSERT INTO Insurance_Product (PRODUCT_NAME, DESCRIPTION) VALUES
        ('BEZPIECZNY_DOM', 'Ubezpieczenie domu lub mieszkania zapewnia kompleksową ochronę Twojej nieruchomości przed różnymi ryzykami, takimi jak pożar, powódź, włamanie czy uszkodzenia spowodowane przez wichury. Polisa obejmuje zarówno mury i stałe elementy budynku, jak i wyposażenie wnętrza, w tym meble, sprzęt RTV i AGD, a także cenne przedmioty osobiste.'),
        ('BEZPIECZNE_MASZYNY', 'Ubezpieczenie maszyn firmowych zapewnia ochronę przed nagłymi i nieprzewidzianymi zdarzeniami, które mogą prowadzić do uszkodzenia, zniszczenia lub utraty maszyn i urządzeń wykorzystywanych w działalności gospodarczej. Polisa obejmuje szkody powstałe w wyniku błędów projektowych, wadliwego wykonania, błędów montażowych, awarii mechanicznych, a także zdarzeń losowych, takich jak pożar, powódź czy kradzież..'),
        ('BEZPIECZNY_JACHT', 'Ubezpieczenie jachtów i łodzi zapewnia kompleksową ochronę Twojej jednostki pływającej przed różnymi ryzykami, takimi jak kolizje, pożar, kradzież czy uszkodzenia spowodowane przez czynniki atmosferyczne. Polisa obejmuje zarówno ubezpieczenie casco, które pokrywa koszty naprawy lub wymiany łodzi w przypadku jej uszkodzenia lub utraty, jak i ubezpieczenie OC, które chroni przed odpowiedzialnością cywilną za szkody wyrządzone innym osobom. Dodatkowo, ubezpieczenie może obejmować pomoc assistance, zapewniając holowanie, pomoc techniczną oraz wsparcie w razie awarii na wodzie'),
        ('BEZPIECZNA_PRZYSZŁOŚĆ', 'Ubezpieczenie odpowiedzialności cywilnej (OC) w życiu prywatnym zapewnia ochronę przed finansowymi skutkami szkód wyrządzonych osobom trzecim lub ich mieniu. Polisa obejmuje sytuacje, w których Ty lub członkowie Twojej rodziny nieumyślnie spowodujecie szkody, takie jak zalanie mieszkania sąsiada, wybicie szyby czy uszkodzenie mienia podczas codziennych czynności. Ubezpieczenie to pokrywa koszty naprawy szkód, co pozwala uniknąć nieprzewidzianych wydatków i zapewnia spokój ducha.');

INSERT INTO product_risk (PRODUCT_ID, RISK_ID) VALUES
-- BEZPIECZNY DOM
(1, 1), -- ZALANIE
(1, 2), -- POŻAR
(1, 3), -- KRADZIEŻ
(1, 4), -- SKUTKI WICHURY
(1, 5), -- ZNISZCZENIA MURÓW
(1, 6), -- ZNISZCZENIA AGD
(1, 10), -- PRZEPIĘCIA

-- BEZPIECZNE MASZYNY
(2, 1), -- ZALANIE
(2, 2), -- POŻAR
(2, 3), -- KRADZIEŻ
(2, 10), -- PRZEPIĘCIA

-- BEZPIECZNY_JACHT
(3, 1), -- ZALANIE
(3, 2), -- POŻAR
(3, 3), -- KRADZIEŻ
(3, 7), -- WANDALIZM
(3, 10), -- PRZEPIĘCIA

-- BEZPIECZNA PRZYSZŁOŚĆ
(4, 1), -- ZALANIE
(4, 2), -- POŻAR
(4, 9), -- USZKODZENIE MIENIA
(4, 8); -- USZKODZENIE SZYB

