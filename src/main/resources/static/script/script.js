// ROZWIJANA LISTA, SEKCJA NAWIGACJI, PŁYWAJĄCY PRZYCISK, ZAKŁADKI
// https://materializecss.com/collapsible.html
// https://materializecss.com/sidenav.html + dropdown-trigger
// https://materializecss.com/floating-action-button.html

document.addEventListener('DOMContentLoaded', function () {
    // Inicjalizacja rozwijanej tabelki "collapsible"
    var collapsibleElems = document.querySelectorAll('.collapsible');
    var collapsibleInstances = M.Collapsible.init(collapsibleElems);

    // Inicjalizacja sekcji nawigacji
    var sidenavElems = document.querySelectorAll('.sidenav');
    var sidenavInstances = M.Sidenav.init(sidenavElems);

    // Inicjalizacja rozwijanych menu
    var dropdownElems = document.querySelectorAll('.dropdown-trigger');
    M.Dropdown.init(dropdownElems, {coverTrigger: false});

    // Inicjalizacja sekcji szybkiego dostępu - floating action button
    var fabElems = document.querySelectorAll('.fixed-action-btn');
    var fabInstances = M.FloatingActionButton.init(fabElems, {
        direction: 'left',
        hoverEnabled: false
    });

    // Inicjalizacja zakładek na formularzach, np w sekcji polisy (polisa, szkody, dokumenty)
    var tabsElems = document.querySelectorAll('.tabs');
    var tabsInstances = M.Tabs.init(tabsElems, {
        swipeable: true
    });

    // Inicjalizacja walidacji daty
    var selectElems = document.querySelectorAll('select');
    M.FormSelect.init(selectElems);

    var dateElems = document.querySelectorAll('.datepicker');
    M.Datepicker.init(dateElems, {
        format: 'dd.mm.yyyy'
    });

    // Inicjalizacja walidacji długości wprowadzego tekstu w polu
    var lengthElems = document.querySelectorAll('input[data-length], textarea[data-length]');
    M.CharacterCounter.init(lengthElems);
});

//KLIKNIĘCIE NA WIERSZ W LISTACH OTWIERA LINK ZDEFINIIOWANY NA LIŚCIE URL - UŻYTE NA LISTACH POLIS, SZKÓD
//https://stackoverflow.com/questions/17147821/how-to-make-a-whole-row-in-a-table-clickable-as-a-link
//inicjalizacja poprzez th:data-url="@{/object/{id}(id=${object.id})}" th:onclick="'javascript:rowClicked(this);'">
function rowClicked(element) {
    var url = element.getAttribute("data-url");
    location.href = url;
}

// F - O - R - M - U - L - A - R - Z - - P - O - L - I - S - O - W -Y
// ASYNCHRONICZNE ZACZYTYWANIE PRODUKTÓW DO SEKCJI FORMULARZA POLISOWEGO (form id ="policyForm")
// wysyłanie zapytania przez api i pobieranie danych z odpowiedzi przez url: /products/${productId}/risks
// przy zmianie produktów z listy produktów na formularzu będzie tworzony widok powiązanych ryzyk

document.addEventListener('DOMContentLoaded', function () {
    const policyForm = document.getElementById('policyForm');
    //sprawdzam czy formularz istnieje
    if (policyForm) {

        //pobieram elementy z formularza
        const productSelect = document.getElementById('insuranceProduct');
        const riskCounter = document.getElementById('riskCounter');
        const riskList = document.getElementById('riskList');

        console.log('riskList element:', riskList); // Log elementu riskList
        //nasłuchiwanie zmian selekcji z listy produktów
        productSelect.addEventListener('change', function () {
            const productId = this.value;
            console.log('Selected product ID:', productId); // Log wybrany produkt ID
            //pobieranie ryzyk dla wybranego produktu
            fetch(`/products/${productId}/risks`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Fetched risks:', data); // Log dopasowane ryzyka
                    // aktualizacja licznika ryzyk
                    riskCounter.value = data.length;

                    //  czyszczenie ryzyk
                    if (riskList) {
                        riskList.innerHTML = '';

                        // dodanie nowych ryzyk
                        data.forEach(risk => {
                            const riskChip = document.createElement('div');
                            riskChip.classList.add('chip');
                            riskChip.innerHTML = `
                                <i class="material-icons">
                                    <span>${risk.iconName}</span>
                                </i>
                                <span>${risk.riskName}</span>
                            `;
                            console.log('Created risk chip:', riskChip); // Log - utworzona lista ryzyk
                            riskList.appendChild(riskChip);
                        });
                    } else {
                        console.error('riskList element not found');
                    }
                })
                .catch(error => console.error('Error fetching risks:', error));
        });


    }
});   //ZACZYTYWANIE PRODUKTÓW DO SEKCJI FORMULARZA POLISOWEGO  - KONIEC

// F - O - R - M - U - L - A - R - Z - - P - O - L - I - S - O - W -Y
// PRZEKAZANIE NUMERU POLISY Z FORM. POLISY DO FORMULARZA SZKODY - > UTWORZENIE NOWEJ SZKODY Z PRZYCISKU Z FORM. POLISY -->
document.addEventListener('DOMContentLoaded', function () {
    var registerNewClaimButton = document.getElementById('registerNewClaimButton');
    if (registerNewClaimButton) {
        registerNewClaimButton.addEventListener('click', function () {
            // Pobieram numer polisy z pola input
            var policyNumber = document.getElementById('policyNumber').value;
            console.log('Policy Number:', policyNumber); // Debugowanie

            // Przekieruj na stronę /claims/new z numerem polisy jako parametrem URL
            window.location.href = '/claims/new?policyNumber=' + policyNumber;
        });
    } else {
        console.log('Register New Claim Button Not Found'); // Debugowanie
    }

    // Sprawdzam czy jesteśmy na stronie /claims/new i czy mamy numer polisy w URL
    var urlParams = new URLSearchParams(window.location.search);
    var policyNumber = urlParams.get('policyNumber');
    console.log('URL Policy Number:', policyNumber); // Debugowanie

    if (policyNumber) {
        // Upewniam się, że pole input jest dostępne
        var policyNumberSearchInput = document.getElementById('policyNumberSearch');
        if (policyNumberSearchInput) {
            console.log('Policy Number Search Input Found'); // Debugowanie

            // Wypełniam pole nr polisy
            policyNumberSearchInput.value = policyNumber;
            console.log('Policy Number Set:', policyNumberSearchInput.value); // Debugowanie

            // Automatycznie klikam przycisk po odczekaniu pół sekundy
            var policyNumberSearchButton = document.getElementById('policyNumberSearchButton');
            if (policyNumberSearchButton) {
                setTimeout(function() {
                    policyNumberSearchButton.click();
                }, 500); // 500 milisekund = 0.5 sekundy
            }
        }
    }
});

// F - O - R - M - U - L - A - R - Z - - P - O - L - I - S - O - W -Y
//WYSZUKIWAWNIE KLIENTA NA PODSTAWIE PESEL. WCZYTANIE JEGO DANYCH DO FORMULARZA POLISY
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('peselSearchForm');
    form.addEventListener('submit', async function (event) {
        event.preventDefault();
        const pesel = document.getElementById('peselSearch').value;

        try {
            const response = await fetch(`/clients/search?pesel=${pesel}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
                showMessage('Klient nie został odnaleziony. Dane klienta nie zostały wczytane.', 'red');
            }
            const data = await response.json();
            document.getElementById('firstName').value = data.firstName;
            document.getElementById('lastName').value = data.lastName;
            document.getElementById('pesel').value = data.pesel;
            document.getElementById('dateOfBirth').value = data.dateOfBirth;
            document.getElementById('emailAddresss').value = data.email;
            document.getElementById('mobileNumber').value = data.mobileNumber;
            document.getElementById('city').value = data.address.city;
            document.getElementById('zipcode').value = data.address.zipcode;
            document.getElementById('street').value = data.address.street;
            document.getElementById('streetNo').value = data.address.streetNo;
            document.getElementById('apartmentNo').value = data.address.apartmentNo;

            // Aktualizacja etykiet
            M.updateTextFields();
            showMessage(`Klient ${data.firstName} ${data.lastName} został odnaleziony. Dane klienta zostały wczytane.`, 'green');
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
            showMessage('Klient nie został odnaleziony. Dane klienta nie zostały zastąpione.', 'red');
            //alert('Klient nie został odnaleziony. Dane klienta nie zostały zastąpione.');
        }
    });

    function showMessage(message) {
        const messageContainer = document.getElementById('messageContainer');
        const messageText = document.getElementById('messageText');
        messageText.textContent = message;
        messageContainer.style.display = 'block';
    }

    function showMessage(message, color) {
        const messageContainer = document.getElementById('messageContainer');
        const messageText = document.getElementById('messageText');
        messageText.textContent = message;
        messageContainer.className = `card-panel ${color} lighten-4`;
        messageText.className = `${color}-text text-darken-4`;
        messageContainer.style.display = 'block';
    }
}); //WYSZUKIWAWNIE KLIENTA NA PODSTAWIE PESEL. WCZYTANIE JEGO DANYCH DO FORMULARZA POLISY - KONIEC

// F - O - R - M - U - L - A - R - Z - - S - Z - K - O - D - O - W -Y
//ZACZYTANIE POLISY DO SEKCJI SZKÓD - OBRÓBKA FORMULARZA
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('policyNumberSearchForm');
    form.addEventListener('submit', async function (event) {
        event.preventDefault();
        const policyNumber = document.getElementById('policyNumberSearch').value;

        try {
            const response = await fetch(`/checkPolicyNumber?policyNumber=${policyNumber}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
                showMessage('Polisa nie została odnaleziona.', 'red');
            }
            const data = await response.json();
            document.getElementById('policyId').value = data.id;
            document.getElementById('policyNumber').value = data.policyNumber;
            document.getElementById('startDate').value = data.startDate;
            document.getElementById('endDate').value = data.endDate;
            document.getElementById('premium').value = data.premium;
            document.getElementById('coverageAmount').value = data.coverageAmount;
            document.getElementById('reserveAmount').value = data.reserveAmount;
            document.getElementById('insuranceProduct').value = data.insuranceProduct.productName;
            document.getElementById('policyStatus').textContent = data.policyStatus ? `DANE POLISY: ${data.policyStatus}` : 'DANE POLISY: ';

            //get client
            document.getElementById('firstName').value = data.client.firstName;
            document.getElementById('lastName').value = data.client.lastName;
            document.getElementById('pesel').value = data.client.pesel;
            document.getElementById('dateOfBirth').value = data.client.dateOfBirth;
            document.getElementById('emailAddresss').value = data.client.email;
            document.getElementById('mobileNumber').value = data.client.mobileNumber;
            document.getElementById('city').value = data.client.address.city;
            document.getElementById('zipcode').value = data.client.address.zipcode;
            document.getElementById('street').value = data.client.address.street;
            document.getElementById('streetNo').value = data.client.address.streetNo;
            document.getElementById('apartmentNo').value = data.client.address.apartmentNo;


            //get risk
            const riskList = document.getElementById('riskList');

            // Clear existing risks
            if (riskList) {
                riskList.innerHTML = '';

                // Add new risks
                data.insuranceProduct.risks.forEach(risk => {
                    const riskChip = document.createElement('div');
                    riskChip.classList.add('chip');
                    riskChip.innerHTML = `
                        <i class="material-icons">
                            <span>${risk.iconName}</span>
                        </i>
                        <span>${risk.riskName}</span>
                    `;
                    console.log('Created risk chip:', riskChip); // Log utworzona lista ryzyk
                    riskList.appendChild(riskChip);
                });
            } else {
                console.error('riskList element not found');
            }


            // Aktualizacja etykiet
            M.updateTextFields();
            showMessage(`Polisa o numerze:  ${data.policyNumber} zawarta przez  ${data.client.firstName} ${data.client.lastName} została odnaleziona. Dane z polisy zostaną zaczytane.`, 'green');

            // Pokaż sekcję claim_data
            document.getElementById('claim_data').style.display = 'block';
            document.getElementById('search_data').style.display = 'none';
            // Zmień kroki w formularzu
            document.getElementById('steps_2').classList.remove('inactive-step');
            document.getElementById('steps_2').classList.add('active-step');


        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
            showMessage('Polisa nie została odnaleziona. Utwórz lub zaimportuj polisę by utworzyć szkodę.', 'red');
        }
    });

    function showMessage(message, color) {
        M.toast({html: message, classes: `${color} rounded`});
    }
});//ZACZYTANIE POLISY DO SEKCJI SZKÓD - OBRÓBKA FORMULARZA  - KONIEC

// USTAWIANIE STYLI DLA PRZYCISKU PŁATNOŚCI I POLA DECYZJI
document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('.modal');
    var instances = M.Modal.init(elems);

    var decisionSelect = document.getElementById('decision');
    var paymentButtonContainer = document.getElementById('paymentButtonContainer');

    decisionSelect.addEventListener('change', function () {
        if (this.value === 'AKCEPTACJA') {
            paymentButtonContainer.style.display = 'block';
        } else {
            paymentButtonContainer.style.display = 'none';
        }
    });
});

// WALIDACJA POLA HTML DATA SZKODY NIE MOZE BYC PÓZNIEJ NIZ DATA REJESTRACJI
$(document).ready(function() {
    $('#claimDate').on('change', function() {
        var claimDateValue = $(this).val();
        var claimRegistrationDateValue = $('#claimRegistrationDate').val();

        if (claimDateValue && claimRegistrationDateValue) {
            var claimDateParts = claimDateValue.split('.');
            var claimRegistrationDateParts = claimRegistrationDateValue.split('.');

            var claimDate = new Date(claimDateParts[2], claimDateParts[1] - 1, claimDateParts[0]);
            var claimRegistrationDate = new Date(claimRegistrationDateParts[2], claimRegistrationDateParts[1] - 1, claimRegistrationDateParts[0]);

            if (claimDate >= claimRegistrationDate) {
                $(this).val('');
                M.toast({html: 'Data roszczenia nie może być późniejsza lub taka sama jak data rejestracji roszczenia.', classes: 'red'});
            }
        }
    });
});

//DODANIE POPRAWKI DO WYBORU DAT BY BYŁA PO POLSKU
document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.datepicker');
    var instances = M.Datepicker.init(elems, {
        format: 'dd.mm.yyyy', // Ustaw format daty
        i18n: {
            months: ['Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec', 'Lipiec', 'Sierpień', 'Wrzesień', 'Październik', 'Listopad', 'Grudzień'],
            monthsShort: ['Sty', 'Lut', 'Mar', 'Kwi', 'Maj', 'Cze', 'Lip', 'Sie', 'Wrz', 'Paź', 'Lis', 'Gru'],
            weekdays: ['Niedziela', 'Poniedziałek', 'Wtorek', 'Środa', 'Czwartek', 'Piątek', 'Sobota'],
            weekdaysShort: ['Nie', 'Pon', 'Wto', 'Śro', 'Czw', 'Pią', 'Sob'],
            weekdaysAbbrev: ['N', 'P', 'W', 'Ś', 'C', 'P', 'S']
        }
    });
});