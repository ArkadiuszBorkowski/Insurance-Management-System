// ROZWIJANA LISTA by https://materializecss.com/collapsible.html
document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('.collapsible');
    var instances = M.Collapsible.init(elems);
});

// SEKCJA NAWIGACJI by https://materializecss.com/navbar.html
document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('.sidenav');
    var instances = M.Sidenav.init(elems);
    var dropdowns = document.querySelectorAll('.dropdown-trigger');
    M.Dropdown.init(dropdowns, {coverTrigger: false});
});


// SEKCJA SZYBKI DOSTĘP (prawy dolny róg) by https://materializecss.com/floating-action-button.html

document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('.fixed-action-btn');
    var instances = M.FloatingActionButton.init(elems, {
        direction: 'left',
        hoverEnabled: false
    });
});


// PRZEKAZANIE NUMERU POLISY DO FORMULARZA SZKODY - > NOWA SZKODA Z PRZYCISKU Z FORM. POLISY -->
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

        } else {
            console.log('Policy Number Search Input Not Found'); // Debugowanie
        }
    }
});


//SEKCJA ZAKŁADKI - WYKORZYSTANE W SEKCJI POLISY () by https://materializecss.com/tabs.html -->
document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('.tabs');
    var instances = M.Tabs.init(elems, {
        swipeable: true
    });
});


//SEKCJA WALIDACJI DAT I WYBORU DATY by https://materializecss.com/ -->
document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('select');
    M.FormSelect.init(elems);

    var dateElems = document.querySelectorAll('.datepicker');
    M.Datepicker.init(dateElems, {
        format: 'dd.mm.yyyy'
    });
});


//SEKCJA WALIDACJI DŁUGOŚCI POLA by https://materializecss.com/ -->

document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('input[data-length], textarea[data-length]');
    M.CharacterCounter.init(elems);
});

//KLIKNIĘCIE NA WIERSZ W LISTACH OTWIERA ZDEFINIIOWANY NA LIŚCIE URL
function rowClicked(element) {
    var url = element.getAttribute("data-url");
    location.href = url;
}

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
});

//WYSZUKIWAWNIE KLIENTA NA PODSTAWIE PESEL. WCZYTANIE JEGO DANYCH DO FORMULARZA POLISY - KONIEC

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
});
//ZACZYTANIE POLISY DO SEKCJI SZKÓD - OBRÓBKA FORMULARZA  - KONIEC

//ZACZYTYWANIE PRODUKTÓW DO SEKCJI FORMULARZA POLISOWEGO
document.addEventListener('DOMContentLoaded', function () {
    const productSelect = document.getElementById('insuranceProduct');
    const riskCounter = document.getElementById('riskCounter');
    const riskList = document.getElementById('riskList');

    console.log('riskList element:', riskList); // Log elementu riskList

    productSelect.addEventListener('change', function () {
        const productId = this.value;
        console.log('Selected product ID:', productId); // Log wybrany produkt ID
        fetch(`/products/${productId}/risks`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Fetched risks:', data); // Log dopasowane ryzyka
                // Update risk counter
                riskCounter.value = data.length;

                // Clear existing risks
                if (riskList) {
                    riskList.innerHTML = '';

                    // Add new risks
                    data.forEach(risk => {
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
            })
            .catch(error => console.error('Error fetching risks:', error));
    });
});

//ZACZYTYWANIE PRODUKTÓW DO SEKCJI FORMULARZA POLISOWEGO  - KONIEC