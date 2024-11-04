#  System Zawierania Umów Ubezpieczeniowych
## O projekcie:
|![logo](https://raw.githubusercontent.com/ArkadiuszBorkowski/Insurance-Management-System/refs/heads/master/src/main/resources/static/images/logo_small.png)  |BEZPIECZNA PRZYSZŁOŚĆ | 2024 | Projekt stanowi przykład wykorzystania i integracji różnych technologii, pokazując umiejętność tworzenia kompleksowej aplikacji bazodanowej |
|--|--|--|--|

Aplikacja jest kompleksowym **systemem do zarządzania polisami i szkodami ubezpieczeniowymi**. 
Dzięki modularnej architekturze i zastosowaniu wzorców projektowych, aplikacja jest skalowalna, bezpieczna i łatwa w utrzymaniu.  
Dostęp do aplikacji jest zabezpieczony przy użyciu **Spring Security** i wymaga zalogowania. Użytkownicy systemu posiadają **określone role i uprawnienia** - dostęp może być uzależniony od roli.  
Zadaniem użytkownika systemu jest wprowadzanie danych klienta i zawieranie polis ubezpieczeniowych w zależności od wybranego przez klienta produktu ubezpieczeniowego.  
W przypadku gdy klient zgłosi szkodę, użytkownik wprowadza informacje o zdarzeniu do systemu a następnie operator szkód podejmuje decyzję odnośnie odrzuceniu lub akceptacji szkody i wypłacie odszkodowania.  
Dzięki wykorzystaniu obsługi zdarzeń procesy jakie musi wykonać operator szkód zostały zautomaytyzowane tak by ograniczyć jego pracę  
Produkty ubezpieczeniowe są definiowane tylko przez administratora systemu. (rola ADMIN).


## 💻 Wykorzystane technologie:

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E) ![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white) ![Spring](https://img.shields.io/badge/spring_security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![H2](https://img.shields.io/badge/H2_DATABASE-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)  ![lLIQUIBASE](https://img.shields.io/badge/liquibase-%23E34F26.svg?style=for-the-badge&logo=liquibase&logoColor=white) ![JPA](https://img.shields.io/badge/hibernate-%23323330.svg?style=for-the-badge&logo=hibernate&logoColor=red) ![modelmapper](https://img.shields.io/badge/modelmapper-%23700.svg?style=for-the-badge&logo=modelmapper&logoColor=red) ![SLF4J](https://img.shields.io/badge/SLF4J-%23E34F26.svg?style=for-the-badge&logo=logger&logoColor=yellow) ![iText](https://img.shields.io/badge/iText-pdf-9A1.svg?style=for-the-badge&logo=iText&logoColor=yellow)

## Podgląd aplikacji (dane testowe generowane losowo)
![homescreen](https://raw.githubusercontent.com/ArkadiuszBorkowski/Insurance-Management-System/refs/heads/master/src/main/resources/static/images/HS.PNG)
![listapolis](https://raw.githubusercontent.com/ArkadiuszBorkowski/Insurance-Management-System/refs/heads/master/src/main/resources/static/images/LP.PNG)
![polisy](https://raw.githubusercontent.com/ArkadiuszBorkowski/Insurance-Management-System/refs/heads/master/src/main/resources/static/images/P.PNG)
![szkody](https://raw.githubusercontent.com/ArkadiuszBorkowski/Insurance-Management-System/refs/heads/master/src/main/resources/static/images/S.PNG)


## Założenia projektowe

Autoryzacja systemowa:

```mermaid
flowchart LR
    A[Użytkownik niezalogowany] --> B[Strona logowania]
    B --> C{Autoryzacja}
    C -->|SUKCES| D[Dostęp do systemu]
    C -->|BRAK AUTORYZACJI| E[Powrót na stronę logowania]
    E --> B
```

Zapis polisy:

```mermaid
graph TD
    A[Operator szkód] --> B[Formularz polisy]
    B --> C{Szukanie klienta po PESEL}
    C -- KLIENT ISTNIEJE --> E[Pobranie danych klienta]
    E --> M[Dane klienta]
    C -- KLIENT NIE ISTNIEJE --> L[Manualne wprowadzanie danych]
    B --> L
    L --> M[Dane klienta]
    M --> N[Wprowadzenie danych polisy]
    N -- Zapis polisy w systemie--> id1[(BAZA DANYCH - POLISA)]
```

Polisa utworzona - widok strony: 

```mermaid
flowchart TD
    A((POLISA)) --> B[FORMULARZ POLISY]
    A --> C[SZKODY]
    C --> D[Lista szkód]
    C --> E[Nowa szkoda]
    A --> F[DOKUMENTY]
    F --> G[Przegląd dokumentów]
    F --> H[Generowanie do PDF]
```

Zgłaszanie nowego roszczenia:

```mermaid
flowchart LR
    A[Sprawdzenie czy klient posiada polise] --> B{Klient posiada polise?}
    B -->|Tak| C[Mozliwość założenia szkody]
    B -->|Nie| D[Nie mozna założyć szkody]
```

Roszczenie w systemie:

```mermaid
flowchart TD
    A((DANE SZKODY)) -- ZAPIS --> B[NOWE ROSZCZENIE]
    B --> C{Weryfikacja danych polisy}
    C -->|POLISA_AKTYWNA| D[PROCESY OBSŁUGI ROSZCZENIA]
    C -->|POLISA_NIEAKTYWNA| E[ODRZUCENIE SZKODY]
    C -->|POLISA_AKTYWNA, REZERWA_POLISY = 0| E[ODRZUCENIE SZKODY]
    D <--> F[[WERYIFKACJA SZKODY]] 
    D <--> G[[OCZEKIWANIE NA DOKUMENTY]] 
    D <--> H[[ZATWIERDZENIE ROSZCZENIA]] 
    H --> I{DECYZJA}
    I -->|AKCEPTACJA| J[ZLECENIE PŁATNOŚCI]
    I -->|ODRZUCENIE| E[ODRZUCENIE SZKODY]
    J -- WYPŁATA ROSZCZENIA --> K[POMNIEJSZENIE REZERWY UBEPIECZENIA POLISY]
    K -->|REZERWA_POLISY = 0| L[ZAMKNIĘCIE POLISY]
    K -->|REZERWA_POLISY > 0| M[POLISA AKTYWNA]
```

## Funkcjonalności


 - **Podsumowanie polis i szkód w systemie**, w tym szkody zamknięte, wypłacone oraz dzisiejsze zgłoszenia.  
W aplikacji wykorzystane są specjalne wizualne znaczniki, np. szkód zawartych w danym dniu. SZKODY  3️⃣
- **Asynchroniczne pobieranie danych  klienta**  na podstawie PESEL do formularza, bez konieczności przeładowania.  
- **Asynchroniczne pobieranie ryzyk**  na podstawie wybranego produktu ubezpieczeniowego na formularzu.  
API do zwrócenia danych (JSON) oraz JavaScript do dynamicznego wczytywania danych do formularza.

- **Walidacja wielowarstwowa  danych**  na poziomie modelu  **(backend)**, jak również po stronie klienta  **(frontend)**.

- **Obiekty transferu danych  DTO**  używane do przesyłania danych między warstwami aplikacji.

- Wykorzystanie  harmonogramów **Scheduler**  do weryfikacji polis co 24h oraz  **aktualizacji statusów**  (np. gdy polisa wygaśnie).

- Manualne  **uzupełnianie modelu klienta i zawieranie polis ubezpieczeniowych oraz szkód**.  
Głównym założeniem systemu jest  **minimalizacja ręcznych procesów**  i wprowadzania danych.  
**Część danych jest generowana automatycznie, aby ograniczyć ryzyko błędów ludzkich**, na przykład:  
•  **Generatory numerów polis i szkód**  tworzą unikalne numery na podstawie określonego wzorca, wykorzystując  **Atomic Integer**  do zapewnienia ich unikalności.  
•  **Status polisy**  jest automatycznie ustawiany  **podczas tworzenia i aktualizacji**, a jego stan jest modyfikowany przez  **EventListener  (obsługę zdarzeń)**  oraz  **Scheduler**.  
•  **Opis statusowy szkody**  jest automatycznie ustawiany  **podczas tworzenia i aktualizacji szkody**, a jego stan jest modyfikowany przez  **EventListener  (obsługę zdarzeń)**.

- **Z poziomu**  zapisanej  **polisy**  można przejść  **do szkód ubezpieczonego**  (wybór z listy szkód) lub utworzyć nową:  
Gdy tworzymy szkodę z poziomu formularza polisy, dane klienta i polisy są przenoszone automatycznie do formularza szkód (API + JS).

- **Dynamiczne generowanie dokumentów**  na podstawie danych z polisy oraz  **zapisywanie ich do formatu PDF**.  
- Proste tworzenie szablonów dokumentów, które są automatycznie zaczytywane do listy dokumentów.

- Utworzone  **polisy i szkody są agregowane do listy z paginacją**, z możliwością  **sortowania oraz filtrowania**  wyników.

- Utworzenie szkody możliwe tylko poprzez walidację, czy polisa ubezpieczeniowa istnieje w systemie.  
Na podstawie wprowadzonego numeru polisy i odpowiedzi z API, jeśli polisa istnieje, mapowane są dane polisy na pola w formularzu szkody.

- Formularz szkodowy posiada czytelny baner etapu szkody, ułatwiający rozeznanie na szkodzie oraz opisowy status szkody (+ walidacja).  
[WERYFIKACJA POLISY ➡️ REJESTRACJA ROSZCZENIA ➡️ PODJĘCIE DECYZJI ➡️ WYPŁATA ODSZKODOWANIA]

- Weryfikacja szkód, zbieranie informacji o szkodzie i podejmowanie decyzji o wypłacie roszczeń.  
Wypłata roszczenia wpływa na stan rezerwy polisy i jej status.


